package com.manica.productscatalogue.inventory.variant;

import com.manica.productscatalogue.inventory.Image;
import com.manica.productscatalogue.inventory.ImageRequest;
import com.manica.productscatalogue.inventory.ImageService;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Set;


@RestController
@CrossOrigin(origins = "*",allowedHeaders = "*")
@RequestMapping("/api/v1/image")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;


    @PostMapping("/variant/{variantId}")
    public ResponseEntity<List<Image>> add(
            @PathVariable @Parameter(example = "21") long variantId,
            Principal principal,
            @RequestBody @Valid List<ImageRequest> request
    ) {


        return new ResponseEntity<>(imageService.add(request, variantId), HttpStatus.CREATED);
    }


    @GetMapping("/variant/{id}")
    public ResponseEntity<Set<Image>> get(
            @PathVariable @Parameter(example = "21") long id
    ) {
        return new ResponseEntity<>(imageService.findVariantImages(id), HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImage(
            @PathVariable @Parameter(example = "21") long id
    ) {
        imageService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
