package com.manica.productscatalogue.inventory.category;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*",allowedHeaders = "*")
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {

    
    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<Category> addCategory(
            @RequestBody @Valid CategoryRequest request
    ) {
        return new ResponseEntity<>(categoryService.addCategory(request), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Category>> getAllCategories(
    ) {
        return new ResponseEntity<>(categoryService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> findById(
            @PathVariable @Parameter(example = "21") long id
    ) {
        return ResponseEntity.ok(categoryService.findCategoryById(id));
    }


    @DeleteMapping ("/{id}")
    public ResponseEntity<Void> deleteCategory(
            @PathVariable @Parameter(example = "21") long id
    ) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
}
