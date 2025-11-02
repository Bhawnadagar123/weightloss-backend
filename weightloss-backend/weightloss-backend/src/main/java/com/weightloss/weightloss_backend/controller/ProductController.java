package com.weightloss.weightloss_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.weightloss.weightloss_backend.entities.Product;
import com.weightloss.weightloss_backend.repository.ProductRepository;
import com.weightloss.weightloss_backend.service.FileStorageService;
import com.weightloss.weightloss_backend.service.ProductServiceImpl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "http://localhost:4200") // allow Angular during dev
public class ProductController {

	
	private final ProductServiceImpl productService;
	
	private final ObjectMapper objectMapper;

    public ProductController(ProductServiceImpl productService, ObjectMapper objectMapper) {
        this.productService = productService;
        this.objectMapper = objectMapper;
    }

    // GET /api/products
    @GetMapping
    public List<Product> getAllProducts(@RequestParam(required = false) String search) {
        return productService.getAllProducts(search);
    }

    // GET /api/products/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return productService.getProductById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

 // CREATE product (multipart: JSON + optional images)
    @PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<Product> createProduct(
            @RequestPart("product") String productJson,
            @RequestPart(value = "images", required = false) MultipartFile[] images
    ) {
        try {
            // parse product JSON into Product object
            Product product = objectMapper.readValue(productJson, Product.class);

            Product saved = productService.saveProductWithImages(product, images);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

 // UPDATE product (multipart)
    @PutMapping( consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<Product> updateProduct(
            @PathVariable Long id,
            @RequestPart("product") String productJson,
            @RequestPart(value = "images", required = false) MultipartFile[] images,
            @RequestParam(value = "replaceImages", required = false, defaultValue = "false") boolean replaceImages
    ) {
        try {
            Product productDetails = objectMapper.readValue(productJson, Product.class);

            Product updated = productService.updateProductWithImages(id, productDetails, images, replaceImages);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

 // DELETE unchanged
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        return productService.getProductById(id)
                .map(product -> {
                    productService.deleteProduct(id);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

}

