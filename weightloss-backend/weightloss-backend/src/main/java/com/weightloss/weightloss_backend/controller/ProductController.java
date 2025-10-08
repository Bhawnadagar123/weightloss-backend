package com.weightloss.weightloss_backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.weightloss.weightloss_backend.entities.Product;
import com.weightloss.weightloss_backend.repository.ProductRepository;
import com.weightloss.weightloss_backend.service.ProductServiceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "http://localhost:4200") // allow Angular during dev
public class ProductController {

	private final ProductServiceImpl productService;

    public ProductController(ProductServiceImpl productService) {
        this.productService = productService;
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

    // POST /api/products
 // admin endpoints - protected in SecurityConfig with /api/admin/** mapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/admin/products")
    public Product createProduct(@RequestBody Product product) {
        return productService.saveProduct(product);
    }

    // PUT /api/products/{id}
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping("/admin/products/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product productDetails) {
        return productService.getProductById(id)
                .map(product -> {
                    product.setName(productDetails.getName());
                    product.setDescription(productDetails.getDescription());
                    product.setSubDescription(productDetails.getSubDescription());
                    product.setPrice(productDetails.getPrice());
                    product.setStock(productDetails.getStock());
                    product.setCategory(productDetails.getCategory());
                    return ResponseEntity.ok(productService.saveProduct(product));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE /api/products/{id}
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/admin/products/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        return productService.getProductById(id)
                .map(product -> {
                    productService.deleteProduct(id);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

}

