package com.manica.productscatalogue.subscriptions.vendor;

import com.manica.productscatalogue.subscriptions.user.User;
import com.manica.productscatalogue.subscriptions.user.UserRequest;
import org.mapstruct.*;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface VendorMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(VendorRequest request, @MappingTarget Vendor vendor);

}
