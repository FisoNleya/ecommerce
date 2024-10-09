package com.manica.productscatalogue.inventory.brand;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;


@RestController
@CrossOrigin(origins = "*",allowedHeaders = "*")
@RequestMapping("/api/v1/brand")
@RequiredArgsConstructor
public class BrandController {

    private final BrandService brandService;


    @PostMapping
    public ResponseEntity<Brand> addBrand(
            @RequestBody @Valid BrandRequest request,
                Principal user
    ) {

        return new ResponseEntity<>(brandService.addBrand(request, user.getName()), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Brand>> getAllBrands(
    ) {
        return new ResponseEntity<>(brandService.findAllBrands(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Brand> findById(
            @PathVariable @Parameter(example = "21") long id
    ) {
        return ResponseEntity.ok(brandService.findBrandById(id));
    }


    @DeleteMapping ("/{id}")
    public ResponseEntity<Void> deleteBrand(
            @PathVariable @Parameter(example = "21") long id
    ) {
        brandService.delete(id);
        return ResponseEntity.noContent().build();
    }

}