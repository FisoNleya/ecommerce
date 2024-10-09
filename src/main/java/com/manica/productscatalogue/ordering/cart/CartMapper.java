package com.manica.productscatalogue.ordering.cart;


import com.manica.productscatalogue.inventory.Image;
import com.manica.productscatalogue.inventory.ImageRequest;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CartMapper {


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(ImageRequest request, @MappingTarget Image image);

}
