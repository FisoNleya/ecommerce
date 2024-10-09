package com.manica.productscatalogue.delivery;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.manica.productscatalogue.delivery.courier.Client;
import com.manica.productscatalogue.delivery.courier.request.*;
import com.manica.productscatalogue.delivery.courier.response.RatesResponse;
import com.manica.productscatalogue.delivery.courier.response.ServiceLevel;
import com.manica.productscatalogue.delivery.courier.response.ShipResponse;
import com.manica.productscatalogue.exceptions.DataNotFoundException;
import com.manica.productscatalogue.inventory.location.Location;
import com.manica.productscatalogue.inventory.variant.Variant;
import com.manica.productscatalogue.ordering.contact.Contact;
import com.manica.productscatalogue.delivery.courier.CourierClient;
import com.manica.productscatalogue.ordering.order.Order;
import com.manica.productscatalogue.ordering.order.OrderItem;
import com.manica.productscatalogue.ordering.order.Shipping;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeliveryOptionService {
    private final ModelMapper modelMapper;

    private final DeliveryOptionRepository deliveryOptionRepository;
    private final DeliveryMapper mapper;

    private final CourierClient client;
//    private final Client client;


    @Value("${delivery.option.courier.api_key}")
    private String token;

    public DeliveryOption add(DeliveryOptionReq request) {
        DeliveryOption deliveryOption = deliveryOptionRepository.findByOptionTradeName(request.optionTradeName()).orElseGet(DeliveryOption::new);
        mapper.update(request, deliveryOption);
        return deliveryOptionRepository.save(deliveryOption);
    }


    public List<DeliveryOption> findAll() {
        return deliveryOptionRepository.findAll();
    }


    public List<DeliveryOptionResponse> findAllOptions(Order order) {

        Contact contact = order.getDeliveryContact();
        if (contact == null)
            throw new DataNotFoundException("Delivery contact not found for this order : " + order.getOrderId());


        List<DeliveryOption> deliveryOptions = deliveryOptionRepository.findAll();
        List<DeliveryOptionResponse> optionResponses = new ArrayList<>();

        for (DeliveryOption option : deliveryOptions) {

            if (Objects.equals(option.getOptionTradeName(), DefaultOptions.COURIER_GUY.getTradeName())) {

                DeliveryOptionResponse response = new DeliveryOptionResponse();
                modelMapper.map(option, response);

                DeliveryAddress deliveryAddress = mapper.to(contact);
                deliveryAddress.setType(deliveryAddress.getType().toLowerCase());
                deliveryAddress.setLat(-25.80665579999999);
                deliveryAddress.setLng(28.334732);
                deliveryAddress.setLocalArea(contact.getSuburb());
                deliveryAddress.setCode(contact.getZipCode());

                Map<Location, List<OrderItem>> itemsMapOnLocation = order
                        .getOrderItems()
                        .stream()
                        .collect(Collectors.groupingBy(orderItem -> orderItem
                                .getCartItem()
                                .getVariant()
                                .getLocation()));

                //Getting rates for products variants based on location
                List<RatesResponse> ratesResponseList = itemsMapOnLocation
                        .entrySet()
                        .stream()
                        .map(map -> getCourierRatesPerLocation(map, deliveryAddress)).toList();


                List<RateResponse> rateResponses = new ArrayList<>();

                //Add rates that belong to same group
                ratesResponseList.stream().forEach(rs -> {

                    //Collect rates from courier that have same service name and add them
                    List<RateResponse> splitRates = rs.getRates()
                            .stream()
                            .map(rt -> {

                                ServiceLevel serviceLevel = rt.getServiceLevel();
                                return RateResponse.builder()
                                        .rate(rt.getRate())
                                        .name(rt.getServiceLevel().getName())
                                        .rateId(serviceLevel.getId().toString())
                                        .rateCode(serviceLevel.getCode())
                                        .deliveryDateTo(serviceLevel.getDeliveryDateTo())
                                        .deliveryDateFrom(serviceLevel.getDeliveryDateFrom())
                                        .collectionDate(serviceLevel.getCollectionDate())
                                        .build();

                            }).toList()
                            .stream()
                            .collect(Collectors.groupingBy(RateResponse::getName))
                            .entrySet()
                            .stream()
                            .map(DeliveryOptionService::sumAndGetUniqueServiceRateTypes).toList();

                    rateResponses.addAll(splitRates);

                });
                response.setRates(rateResponses);
                optionResponses.add(response);
            }


        }


        return optionResponses;
    }

    private static RateResponse sumAndGetUniqueServiceRateTypes(Map.Entry<String, List<RateResponse>> rtMap) {
        AtomicReference<Double> totalRate = new AtomicReference<>(0.0);
        List<RateResponse> subRateList = rtMap.getValue();

        subRateList.forEach(val -> totalRate.set(totalRate.get() + val.getRate()));
        return RateResponse.builder()
                .rate(totalRate.get())
                .name(rtMap.getKey())
                .rateId(subRateList.get(0).getRateId())
                .rateCode(subRateList.get(0).getRateCode())
                .deliveryDateTo(subRateList.get(0).getDeliveryDateTo())
                .deliveryDateFrom(subRateList.get(0).getDeliveryDateFrom())
                .collectionDate(subRateList.get(0).getCollectionDate())
                .build();
    }

    private RatesResponse getCourierRatesPerLocation(Map.Entry<Location, List<OrderItem>> map, DeliveryAddress deliveryAddress) {
        Location location = map.getKey();
        List<OrderItem> cartItems = map.getValue();
        CollectionAddress collectionAddress = mapper.to(location);

        collectionAddress.setLat(-25.7863272);
        collectionAddress.setLng(28.277583);
        collectionAddress.setLocalArea(location.getSuburb());
        collectionAddress.setCode(location.getZipCode());
        collectionAddress.setType(collectionAddress.getType().toLowerCase());

        List<Parcel> parcels = cartItems.stream().map(item -> {
            Variant variant = item.getCartItem().getVariant();
            return Parcel.builder()
                    .submittedLengthCm(variant.getLength())
                    .submittedWidthCm(variant.getWidth())
                    .submittedHeightCm(variant.getHeight())
                    .submittedWeightKg(variant.getWeight() / 1000)
                    .parcelDescription(variant.getVariantName())
                    .build();
        }).toList();

        var res = client
                .getRates(CourierClient.BEARER + token, RatesRequest.builder()
                        .collectionAddress(collectionAddress)
                        .deliveryAddress(deliveryAddress)
                        .parcels(parcels)
//                                            .declaredValue(1500)
//                                            .collectionMinDate("2021-05-21")
//                                            .deliveryMinDate("2021-05-21")
                        .build());

        if (Objects.isNull(res) || Objects.isNull(res.getRates()))
            throw new DataNotFoundException("Failed to find courier guy rate for the given source and destination");

        return res;
    }


    @Async
    @EventListener
    public void onOrderCreateShipment(Order order) {
        Shipping shipping = order.getShipping();
        DeliveryOption deliveryOption = order.getDelivery();

        Contact contact = order.getDeliveryContact();
        if (contact == null)
            throw new DataNotFoundException("Delivery contact not found for this order : " + order.getOrderId());

        if (Objects.equals(deliveryOption.getOptionTradeName(), DefaultOptions.COURIER_GUY.getTradeName())) {


            DeliveryAddress deliveryAddress = mapper.to(contact);
            deliveryAddress.setType(deliveryAddress.getType().toLowerCase());
            deliveryAddress.setLat(-25.80665579999999);
            deliveryAddress.setLng(28.334732);
            deliveryAddress.setLocalArea(contact.getSuburb());
            deliveryAddress.setCode(contact.getZipCode());


            CollectionContact collectionContact = CollectionContact.builder()
                    .name(contact.getFirstName() + " "+ contact.getLastName())
                    .mobileNumber(contact.getPhoneNumber1())
                    .email(contact.getEmail())
                    .build();

            DeliveryContact deliveryContact = DeliveryContact.builder()
                    .name(contact.getFirstName() + " "+ contact.getLastName())
                    .mobileNumber(contact.getPhoneNumber1())
                    .email(contact.getEmail())
                    .build();


            Map<Location, List<OrderItem>> itemsMapOnLocation = order
                    .getOrderItems()
                    .stream()
                    .collect(Collectors.groupingBy(orderItem -> orderItem
                            .getCartItem()
                            .getVariant()
                            .getLocation()));

            //Getting rates for products variants based on location
            itemsMapOnLocation
                    .forEach((location, cartItems) -> {


                        CollectionAddress collectionAddress = mapper.to(location);

                        collectionAddress.setLat(-25.7863272);
                        collectionAddress.setLng(28.277583);
                        collectionAddress.setLocalArea(location.getSuburb());
                        collectionAddress.setCode(location.getZipCode());
                        collectionAddress.setType(collectionAddress.getType().toLowerCase());

                        List<Parcel> parcels = cartItems.stream().map(item -> {
                            Variant variant = item.getCartItem().getVariant();
                            return Parcel.builder()
                                    .submittedLengthCm(variant.getLength())
                                    .submittedWidthCm(variant.getWidth())
                                    .submittedHeightCm(variant.getHeight())
                                    .submittedWeightKg(variant.getWeight() / 1000)
                                    .parcelDescription(variant.getVariantName())
                                    .build();
                        }).toList();

                        var shippingReq = ShipmentRequest.builder()
                                .collectionAddress(collectionAddress)
                                .collectionContact(collectionContact)
                                .deliveryContact(deliveryContact)
                                .deliveryAddress(deliveryAddress)
                                .parcels(parcels)
                                .serviceLevelCode(shipping.getServiceCode())
                                .muteNotifications(false)
                                .customerReference(order.getOrderId())
                                .specialInstructionsCollection(shipping.getSpecialCollectionInstructions())
                                .specialInstructionsDelivery(shipping.getSpecialDeliveryInstructions())
                                .build();

                        var res = client
                                .addShipment(CourierClient.BEARER + token, shippingReq);

                        if (Objects.isNull(res))
                            throw new DataNotFoundException("Failed to create shipment for this order : " + order.getOrderId());
                        log.info(""+res);


                    });


        }
    }

    public DeliveryOption findDeliveryOptionByEmail(String tradeName) {
        return deliveryOptionRepository.findByOptionTradeName(tradeName)
                .orElseThrow(() -> new DataNotFoundException("DeliveryOption not found with trade Name: " + tradeName));
    }

    public DeliveryOption findDeliveryOptionById(Long id) {
        return deliveryOptionRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("DeliveryOption not found with id: " + id));
    }


    @PostConstruct
    private void initDefaultDeliveryOptions() {

        Optional<DeliveryOption> optionalDo = deliveryOptionRepository.findByOptionTradeName(DefaultOptions.COURIER_GUY.getTradeName());

        if (optionalDo.isEmpty()) {
            deliveryOptionRepository.save(DeliveryOption.builder()
                    .optionTradeName(DefaultOptions.COURIER_GUY.getTradeName())
                    .optionLogoUrl("https://www.pudo.co.za/imgs/the-courier-guy.png")
                    .phoneNumber("")
                    .addressLine1("")
                    .addressLine2("")
                    .city("JHB")
                    .province("GAUTANG")
                    .country("ZA")
                    .createdBy("support@manicasolutions.com")
                    .build());

            log.info("Saved courier guy ::");
        }


    }

}
