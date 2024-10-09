package com.manica.productscatalogue.inventory.features;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Set;


@RestController
@CrossOrigin(origins = "*",allowedHeaders = "*")
@RequestMapping("/api/v1/variant-feature")
@RequiredArgsConstructor
public class VariantFeatureController {

    private final VariantFeatureService variantFeatureService;


    @PostMapping("/variant/{variantId}")
    public ResponseEntity<List<VariantFeature>> add(
            @PathVariable @Parameter(example = "21") long variantId,
            Principal principal,
            @RequestBody @Valid List<VariantFeatureRequest> request
    ) {


        return new ResponseEntity<>(variantFeatureService.add(request, variantId), HttpStatus.CREATED);
    }


    @GetMapping("/variant/{id}")
    public ResponseEntity<Set<VariantFeature>> get(
            @PathVariable @Parameter(example = "21") long id
    ) {
        return new ResponseEntity<>(variantFeatureService.findVariantVariantFeatures(id), HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVariantFeature(
            @PathVariable @Parameter(example = "21") long id
    ) {
        variantFeatureService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
