package com.manica.productscatalogue.delivery;

import com.manica.productscatalogue.delivery.courier.request.CollectionAddress;
import com.manica.productscatalogue.delivery.courier.request.DeliveryAddress;
import com.manica.productscatalogue.inventory.location.Location;
import com.manica.productscatalogue.ordering.contact.Contact;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DeliveryMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(DeliveryOptionReq request, @MappingTarget DeliveryOption option);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(DeliveryOption request, @MappingTarget DeliveryOptionResponse option);



    @ValueMapping(source = "suburb", target = "localArea")
    @ValueMapping(source = "zipCode", target = "code")
    DeliveryAddress to(Contact contact);


    @ValueMapping(source = "suburb", target = "localArea")
    @ValueMapping(source = "zipCode", target = "code")
    CollectionAddress to(Location location);
}
