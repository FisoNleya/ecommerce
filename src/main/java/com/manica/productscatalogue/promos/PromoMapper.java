package com.manica.productscatalogue.promos;

import com.manica.productscatalogue.inventory.rating.Rating;
import com.manica.productscatalogue.inventory.rating.RatingRequest;
import org.mapstruct.*;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PromoMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(PromoRequest request, @MappingTarget Promo promo);

}
