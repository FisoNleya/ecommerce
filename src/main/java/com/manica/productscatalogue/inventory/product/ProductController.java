package com.manica.productscatalogue.inventory.product;


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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*",allowedHeaders = "*")
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Product> add(
            @RequestBody @Valid ProductRequest request
    ) {
        return new ResponseEntity<>(productService.add(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> add(
            @PathVariable @Parameter(example = "21") long id,
            @RequestBody @Valid ProductUpdateRequest request
    ) {
        return new ResponseEntity<>(productService.update(request,id), HttpStatus.OK);
    }

    @Operation(
            description = "Get endpoint for product blue prints",
            summary = "This allows you to get blue prints of products",
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
    @GetMapping("all")
    public ResponseEntity<Page<Product>> fetchAll(
            @RequestParam(defaultValue = "created_at") String sortBy,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "0") int page,

            @RequestParam (defaultValue = "DESC") Sort.Direction direction,
            @RequestParam(defaultValue = "", required = false) String search


    ) {


        SearchDTO searchDto = SearchDTO.builder()
                .search(search)
                .build();


        Pageable pageable = PageRequest.of(page, size, direction, sortBy);
        return new ResponseEntity<>(productService .findByAll(searchDto, pageable), HttpStatus.OK);
    }

    @GetMapping("/vendor/{id}")
    public ResponseEntity<List<Product>> fetchAllVendor(
            @PathVariable @Parameter(example = "21") long id
    ) {
        return new ResponseEntity<>(productService .findByAllVendor(id), HttpStatus.OK);
    }

    @GetMapping("/owner/{email}")
    public ResponseEntity<List<Product>> fetchAllOwner(
            @PathVariable @Parameter(example = "21") String email
    ) {
        return new ResponseEntity<>(productService .findByAllEmail(email), HttpStatus.OK);
    }


}
