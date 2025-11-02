package com.weightloss.weightloss_backend.service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.weightloss.weightloss_backend.entities.Product;
import com.weightloss.weightloss_backend.repository.ProductRepository;

@Service
public class ProductServiceImpl {

	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private FileStorageService fileStorageService;

	
	public List<Product> getAllProducts(String search) {
        if (search != null && !search.isEmpty()) {
            return productRepository.findByNameContainingIgnoreCase(search);
        }
        return productRepository.findAll();
    }
    

    
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public Product saveProductWithImages(Product product, MultipartFile[] images) {
        try {
            if (images != null) {
                for (MultipartFile f : images) {
                    String url = fileStorageService.saveProductImage(f);
                    if (url != null) {
                        product.getImages().add(url);
                    }
                }
            }
        } catch (IOException e) {
            // log error (handle as appropriate)
            e.printStackTrace();
        }
        return productRepository.save(product);
    }
    
    
 // update method: optionally replace images or add new ones
    public Product updateProductWithImages(Long id, Product input, MultipartFile[] images, boolean replaceImages) {
        Product existing = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        existing.setName(input.getName());
        existing.setDescription(input.getDescription());
        existing.setSubDescription(input.getSubDescription());
        existing.setPrice(input.getPrice());
        existing.setMrp(input.getMrp());
        existing.setStock(input.getStock());
        existing.setCategory(input.getCategory());

        try {
            if (images != null && images.length > 0) {
                if (replaceImages) {
                    existing.getImages().clear();
                }
                for (MultipartFile f : images) {
                    String url = fileStorageService.saveProductImage(f);
                    if (url != null) existing.getImages().add(url);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return productRepository.save(existing);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
