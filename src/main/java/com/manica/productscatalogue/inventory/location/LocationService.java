package com.manica.productscatalogue.inventory.location;


import com.manica.productscatalogue.auth.dtos.AuthenticationUser;
import com.manica.productscatalogue.subscriptions.user.User;
import com.manica.productscatalogue.subscriptions.user.UserService;
import com.manica.productscatalogue.subscriptions.vendor.Vendor;
import com.manica.productscatalogue.subscriptions.vendor.VendorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class LocationService {


    private final VendorService vendorService;
    private final LocationMapper mapper;
    private final UserService userService;
    private final LocationRepository locationRepository;

    public Location add(LocationRequest request, Long vendorId, AuthenticationUser authUser){

        Location location =  new Location();

        if(Objects.nonNull(vendorId)){
            Vendor vendor  = vendorService.findVendorById(vendorId);
            location.setVendor(vendor);
        }else {
            User user = userService.findUserByEmail(authUser.getEmail());
            location.setUser(user);
        }

        location.setType(request.addressType().name());
        mapper.update(request, location);
        return locationRepository.save(location);
    }

    public List<Location> getMyLocations(AuthenticationUser authUser){

        User user = userService.findUserByEmail(authUser.getEmail());

        if(user.getUserRights() != null ){

            List<Long> vendorIds = user.getUserRights()
                    .stream()
                    .filter(userRight -> userRight.getVendor() != null)
                    .map(right-> right.getVendor().getVendorId())
                    .toList();

            if(!vendorIds.isEmpty()){
                return locationRepository.findByVendor_VendorIdIn(vendorIds);
            }

        }

        return locationRepository.findByUser_Email(authUser.getEmail());

    }


    public Location findById(Long locationId){
        return locationRepository.findById(locationId)
                .orElseThrow(()-> new RuntimeException("Location not found with id: " + locationId));
    }


    public void delete(Long locationId){
        Location location = findById(locationId);
        locationRepository.delete(location);
    }

}
