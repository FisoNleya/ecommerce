package com.manica.productscatalogue.inventory.features;

import com.manica.productscatalogue.exceptions.DataNotFoundException;
import com.manica.productscatalogue.inventory.variant.Variant;
import com.manica.productscatalogue.inventory.variant.VariantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class VariantFeatureService {

    private final VariantFeatureRepository variantFeatureRepository;
    private final VariantService variantService;
    private final VariantFeatureMapper mapper;


    public List<VariantFeature> add(List<VariantFeatureRequest> request, long variantId) {

        Variant variant = variantService.findById(variantId);

        List<VariantFeature> variantFeatures = request.stream().map(variantFeatureRequest -> {

            VariantFeature variantFeature = new VariantFeature();
            mapper.update(variantFeatureRequest, variantFeature);
            variantFeature.setVariant(variant);
            return variantFeature;
        }).toList();


        return variantFeatureRepository.saveAll(variantFeatures);
    }

    public Set<VariantFeature> findVariantVariantFeatures(long variantId) {
        return variantFeatureRepository.findByVariant_VariantId(variantId);
    }

    public VariantFeature findVariantFeatureById(Long id) {
        return variantFeatureRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("VariantFeature " + id + " not found"));
    }

    public void delete(long id) {
        VariantFeature variantFeature = findVariantFeatureById(id);
        variantFeatureRepository.delete(variantFeature);
    }


}
