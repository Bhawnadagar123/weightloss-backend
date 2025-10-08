package com.weightloss.weightloss_backend.service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weightloss.weightloss_backend.entities.Product;
import com.weightloss.weightloss_backend.repository.ProductRepository;

@Service
public class ProductServiceImpl {

	@Autowired
	private ProductRepository productRepository;


	public List<Product> getAllProducts(String search) {
        if (search != null && !search.isEmpty()) {
            return productRepository.findByNameContainingIgnoreCase(search);
        }
        return productRepository.findAll();
    }
    

    
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
