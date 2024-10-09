package com.manica.productscatalogue.inventory;


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
public class ImageService {
    
    private final ImageRepository imageRepository;
    private final VariantService variantService;
    private final ImageMapper mapper;

    public List<Image> add(List<ImageRequest> request, long variantId) {

        Variant variant = variantService.findById(variantId);

        List<Image> images = request.stream().map(imageRequest -> {

            Image image = new Image();
            mapper.update(imageRequest, image);
            image.setVariant(variant);
            return image;
        }).toList();


        return imageRepository.saveAll(images);
    }

    public Set<Image> findVariantImages(long variantId) {
        return imageRepository.findByVariant_VariantId(variantId);
    }

    public Image findImageById(Long id) {
        return imageRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Image " + id + " not found"));
    }

    public void delete(long id) {
        Image image = findImageById(id);
        imageRepository.delete(image);
    }



}
