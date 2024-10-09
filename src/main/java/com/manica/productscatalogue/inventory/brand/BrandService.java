package com.manica.productscatalogue.inventory.brand;


import com.manica.productscatalogue.exceptions.DataNotFoundException;
import com.manica.productscatalogue.exceptions.DuplicateRecordException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BrandService {


    private final BrandRepository brandRepository;
    private final BrandMapper mapper;

    public Brand addBrand(BrandRequest request, String user) {

        Optional<Brand> optionalBrand = brandRepository.findByBrandName(request.brandName());

        if (optionalBrand.isPresent()) {
            throw new DuplicateRecordException("Brand " + request.brandName() + " already exists");
        }


        Brand brand = new Brand();
        mapper.update(request, brand);
        brand.setCreatedBy(user);
        return brandRepository.save(brand);
    }


    public Brand findBrandByName(String brandName) {
        return  brandRepository.findByBrandName(brandName)
                .orElseThrow(() -> new DataNotFoundException("Brand " + brandName + " not found"));
    }

    public Brand findBrandById(Long id) {
        return brandRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Brand " + id + " not found"));
    }


    public void delete(long id) {
        Brand brand = findBrandById(id);
        brandRepository.delete(brand);
    }

    public List<Brand> findAllBrands() {
        return brandRepository.findAll();
    }

}
