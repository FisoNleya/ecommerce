package com.manica.productscatalogue.inventory.features;

import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface VariantFeatureMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(VariantFeatureRequest request, @MappingTarget VariantFeature variantFeature);

}
