package com.manica.productscatalogue.subscriptions.user;

import com.manica.productscatalogue.inventory.rating.Rating;
import com.manica.productscatalogue.inventory.rating.RatingRequest;
import com.messaging.UserDto;
import org.mapstruct.*;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(UserRequest request, @MappingTarget User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(UserDto request, @MappingTarget User user);

}
