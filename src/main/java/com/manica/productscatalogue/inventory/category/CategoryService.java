package com.manica.productscatalogue.inventory.category;

import com.manica.productscatalogue.exceptions.DataNotFoundException;
import com.manica.productscatalogue.exceptions.DuplicateRecordException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryService {


    private final CategoryRepository categoryRepository;
    
    private final CategoryMapper mapper;


    public Category addCategory(CategoryRequest request) {

        Optional<Category> optionalCategory = categoryRepository.findByName(request.name());

        if (optionalCategory.isPresent()) 
            throw new DuplicateRecordException("Category with name " + request.name() + " already exists");

            
        Category category = new Category();
        mapper.update(request, category);
        return categoryRepository.save(category);
    }

    public Category findCategoryByName(String categoryName) {
        return  categoryRepository.findByName(categoryName)
                .orElseThrow(() -> new DataNotFoundException("Category " + categoryName + " not found"));
    }

    public Category findCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Category " + id + " not found"));
    }


    public void delete(long id) {
        Category category = findCategoryById(id);
        categoryRepository.delete(category);
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

}
