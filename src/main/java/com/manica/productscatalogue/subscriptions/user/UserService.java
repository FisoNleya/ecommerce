package com.manica.productscatalogue.subscriptions.user;

import com.manica.productscatalogue.auth.dtos.AuthenticationUser;
import com.manica.productscatalogue.auth.dtos.ValidUser;
import com.manica.productscatalogue.exceptions.DataNotFoundException;
import com.manica.productscatalogue.exceptions.InvalidRequestException;
import com.manica.productscatalogue.ordering.order.GuestRequest;
import com.manica.productscatalogue.subscriptions.vendor.Vendor;
import com.manica.productscatalogue.subscriptions.vendor.VendorService;
import com.messaging.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserRightRepository userRightRepository;
    private final FeatureRepository featureRepository;
    private final VendorService vendorService;

    private final UserMapper mapper;

    public User add(UserDto request) {

        User user = userRepository.findById(request.getEmail()).orElseGet(User::new);
        mapper.update(request, user);


        if(user.getUserRights() != null){

            List<UserRight> userRights = user.getUserRights()
                    .stream()
                    .filter(rt -> rt != null && rt.getUserRightId() != null)
                    .map(ur -> {
                        UserRight userRight = userRightRepository.findById(ur.getUserRightId()).orElseGet(() ->ur);
                        Vendor vendor = vendorService.findVendorOrCreate(userRight.getVendor());
                        userRight.setVendor(vendor);
                        return userRight;
                    }).toList();

            userRights = userRightRepository.saveAll(userRights);
            user.setUserRights(userRights);


        }


        if(user.getUserRights() != null){

            List<Feature> features = user.getFeatures().stream()
                    .filter(ft -> ft != null && ft.getFeatureId() != null)
                    .map(ft -> featureRepository.findById(ft.getFeatureId()).orElseGet(() -> ft)).toList();
            features = featureRepository.saveAll(features);
            user.setFeatures(features);

        }




        user.setFirstName(request.getFirstname());
        user.setLastName(request.getLastname());
        return userRepository.save(user);
    }


    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new DataNotFoundException("User not found with email: " + email));
    }

    public User findUserByEmailOrCreate(AuthenticationUser user) {
        return userRepository.findByEmail(user.getEmail())
                .orElseGet(() -> userRepository.save(User.builder()
                        .email(user.getEmail())
                        .firstName(user.getFirstname())
                        .lastName(user.getLastname())
                        .build()));
    }

    public User findUserByEmailOrCreate(GuestRequest user) {

        if(Objects.isNull(user.email())){
            throw new InvalidRequestException("User email is required for the guest : ");
        }

        return userRepository.findByEmail(user.email())
                .orElseGet(() -> userRepository.save(User.builder()
                        .email(user.email())
                        .firstName(user.firstName())
                        .lastName(user.lastName())
                        .build()));
    }

}
