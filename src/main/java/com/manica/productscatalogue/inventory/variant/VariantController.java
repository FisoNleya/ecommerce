package com.manica.productscatalogue.inventory.variant;


import com.manica.productscatalogue.auth.dtos.AuthenticationUser;
import com.manica.productscatalogue.inventory.SearchDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;


@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/v1/variant")
@RequiredArgsConstructor
public class VariantController {

    private final VariantService variantService;


    @PostMapping
    public ResponseEntity<Variant> addVariant(
            @RequestBody @Valid VariantRequest request
    ) {
        return new ResponseEntity<>(variantService.add(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Variant> updateVariant(
            @RequestBody @Valid VariantUpdate request,
            @PathVariable @Parameter(example = "21") long id,
            Principal principal
    ) {

        var user = (AuthenticationUser) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        return new ResponseEntity<>(variantService.update(id, request, user), HttpStatus.CREATED);
    }


    @GetMapping("/product/{id}")
    public ResponseEntity<List<Variant>> getAllVariants(
            @PathVariable @Parameter(example = "21") long id
    ) {
        return new ResponseEntity<>(variantService.getProductVariants(id), HttpStatus.OK);
    }


    @Operation(
            description = "Get endpoint for product variants",
            summary = "This allows you to get and filter all variants of different products for listing",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "403"
                    )
            }

    )
    @GetMapping("/all")
    public ResponseEntity<Page<Variant>> findAll(
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(required = false) List<String> brands,
            @RequestParam(required = false) List<String> categories,
            @RequestParam(required = false) @Parameter(example = "50") BigDecimal minPrice,
            @RequestParam(required = false) @Parameter(example = "500") BigDecimal maxPrice,
            @RequestParam(required = false) Byte rating,
            @RequestParam(defaultValue = "DESC") Sort.Direction direction,
            @RequestParam(defaultValue = "") String search


    ) {


        SearchDTO searchDto = SearchDTO.builder()
                .brands(brands)
                .categories(categories)
                .maxPrice(maxPrice)
                .minPrice(minPrice)
                .rating(rating)
                .search(search)
                .build();


        Pageable pageable = PageRequest.of(page, size, direction, sortBy);
        return new ResponseEntity<>(variantService.findAllBy(searchDto, pageable), HttpStatus.OK);
    }


    @Operation(
            description = "Get endpoint for featured variants ",
            summary = "This allows you to get and filter all variants of different products for users with activated for special features",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "403"
                    )
            }

    )
    @GetMapping("/featured/all")
    public ResponseEntity<Page<Variant>> findAllFeaturedVariants(
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(required = false) List<String> brands,
            @RequestParam(required = false) List<String> categories,
            @RequestParam(required = false) @Parameter(example = "50") BigDecimal minPrice,
            @RequestParam(required = false) @Parameter(example = "500") BigDecimal maxPrice,
            @RequestParam(required = false) Byte rating,
            @RequestParam(defaultValue = "DESC") Sort.Direction direction,
            @RequestParam(defaultValue = "") String search,
            Principal principal


    ) {


        SearchDTO searchDto = SearchDTO.builder()
                .brands(brands)
                .categories(categories)
                .maxPrice(maxPrice)
                .minPrice(minPrice)
                .rating(rating)
                .search(search)
                .build();


        Pageable pageable = PageRequest.of(page, size, direction, sortBy);
        var user = (AuthenticationUser) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        return new ResponseEntity<>(variantService.findAllFeaturedVariantsBy(searchDto, user, pageable), HttpStatus.OK);
    }


    @GetMapping("/vendor/{vendorId}")
    public ResponseEntity<Page<Variant>> findAllByVendor(
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(required = false) Boolean active,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "0") int page,
            @PathVariable @Parameter(example = "21") long vendorId,
            @RequestParam(defaultValue = "DESC") Sort.Direction direction,
            Principal principal
    ) {


        var user = (AuthenticationUser) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        Pageable pageable = PageRequest.of(page, size, direction, sortBy);
        return new ResponseEntity<>(variantService.findAllByVendor(vendorId, active, pageable, user), HttpStatus.OK);
    }


    @GetMapping("/related/{id}")
    public ResponseEntity<Page<Variant>> findAllRelatedVariants(
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "0") int page,
            @PathVariable @Parameter(example = "21") long id,
            @RequestParam(defaultValue = "DESC") Sort.Direction direction
    ) {


        Pageable pageable = PageRequest.of(page, size, direction, sortBy);
        return new ResponseEntity<>(variantService.findRelatedVariants(id, pageable), HttpStatus.OK);
    }


    @GetMapping("/other/item/color/")
    public ResponseEntity<Variant> findByColor(
            @RequestParam @Parameter(example = "21") Long currentVariantId,
            @RequestParam @Parameter(example = "Blue") String color,
            @RequestParam(required = false) @Parameter(example = "XXHH") String colorHex
    ) {
        Variant variant = variantService.findByColor(currentVariantId, color, colorHex);
        if (variant == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(variant);
    }


    @GetMapping("/other/item/size/")
    public ResponseEntity<Variant> findSize(
            @RequestParam @Parameter(example = "21") Long currentProductId,
            @RequestParam @Parameter(example = "XL") String size
    ) {
        Variant variant = variantService.findBySize(currentProductId,size);
        if (variant == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(variant);
    }



    @GetMapping("/item/{id}")
    public ResponseEntity<Variant> findById(
            @PathVariable @Parameter(example = "21") long id
    ) {
        return ResponseEntity.ok(variantService.findById(id));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVariant(
            @PathVariable @Parameter(example = "21") long id,
            Principal principal

    ) {

        var user = (AuthenticationUser) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        variantService.deleteById(id, user);
        return ResponseEntity.noContent().build();
    }


    @Operation(
            description = "Test endpont , not for officical user!!",
            summary = "Test endpont , not for officical user!!",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "403"
                    )
            }

    )
    @PutMapping("/test/activate-all")
    public ResponseEntity<?> activateAllProductVariants(
    ) {
        variantService.activateAll();
        return ResponseEntity.ok().build();
    }


    //ADMIN Specific
//    @PreAuthorize("hasAuthority('admin:update')")
    @PutMapping("/product/{id}/verification")
    public ResponseEntity<List<Variant>> verifyVariants(
            @RequestBody @Valid VariantVerification request,
            @PathVariable(value = "id") @Parameter(example = "21") long productId

    ) {

        return new ResponseEntity<>(variantService.verifyVariants(productId, request), HttpStatus.OK);
    }


}
