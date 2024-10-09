package com.manica.productscatalogue.inventory.product;

import com.manica.productscatalogue.exceptions.DuplicateRecordException;
import com.manica.productscatalogue.inventory.SearchDTO;
import com.manica.productscatalogue.inventory.brand.Brand;
import com.manica.productscatalogue.inventory.brand.BrandService;
import com.manica.productscatalogue.inventory.category.Category;
import com.manica.productscatalogue.inventory.category.CategoryService;
import com.manica.productscatalogue.inventory.variant.Variant;
import com.manica.productscatalogue.subscriptions.user.User;
import com.manica.productscatalogue.subscriptions.user.UserService;
import com.manica.productscatalogue.subscriptions.vendor.Vendor;
import com.manica.productscatalogue.subscriptions.vendor.VendorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {


    private final ProductRepository productRepository;
    private final ProductMapper mapper;
    private final UserService userService;
    private final BrandService brandService;
    private final VendorService vendorService;
    private final CategoryService categoryService;
    private final ProductSpecification productSpecification;


    public Product add(ProductRequest request) {

        Optional<Product> optionalProduct = productRepository.findBySku(request.sku());

        if (optionalProduct.isPresent()) {
            throw new DuplicateRecordException("Product already exists with sku: " + request.sku());
        }

        Product product = new Product();
        mapper.update(request, product);

        Brand brand = brandService.findBrandById(request.brandId());
        product.setBrand(brand);
        Category category = categoryService.findCategoryById(request.categoryId());
        product.setCategory(category);

        if(request.vendorId() != null){
            Vendor vendor = vendorService.findVendorById(request.vendorId());
            product.setVendor(vendor);
        }

        if(request.ownerId() !=null){
            User user = userService.findUserByEmail(request.ownerId());
            product.setOwner(user);
        }

        //Send notification to admin to verify
        return productRepository.save(product);
    }


    //Adds product variants colors
    public Product update(Variant variant){

        Product product = variant.getProduct();

        Set<String> savedColors = product.getColors() == null ?
                new HashSet<>() : product.getColors();
        savedColors.add(variant.getColorHex());


        Set<String> savedSizes = product.getSizes() == null ?
                new HashSet<>() : product.getSizes();
        savedColors.add(variant.getSize());


        product.setColors(savedColors);
        product.setSizes(savedSizes);

        return productRepository.save(product);
    }



    public Page<Product> findByAll(SearchDTO searchDTO,
                                   Pageable pageable) {


        Specification<Product> appQuery = productSpecification.getAppQuerySpecification(searchDTO);
        return productRepository.findAll(appQuery, pageable);

    }


    public List<Product> findByAllVendor(long vendorId) {
        return productRepository.findByVendor_VendorId(vendorId);

    }

    public List<Product> findByAllEmail(String email) {
        return productRepository.findAllByOwner_Email(email);

    }



    public Product update(ProductUpdateRequest request, long id){
        Product product = findById(id);
        mapper.update(request, product);
        return productRepository.save(product);
    }


    public Product findById(long id){
        return productRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("Product not found with id: " + id));
    }

    public Product findBySku(String sku) {
        return productRepository.findBySku(sku)
                .orElseThrow(()-> new IllegalArgumentException("Product not found with sku: " + sku));
    }



}
