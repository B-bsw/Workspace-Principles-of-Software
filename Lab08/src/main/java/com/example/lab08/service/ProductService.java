package com.example.lab08.service;

import com.example.lab08.model.Product;
import com.example.lab08.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    public void insertProduct(Product product){
        productRepository.save(product);
    }
}
