package com.example.lab08.service;

import com.example.lab08.model.Product;
import com.example.lab08.repository.ProductRepository;
import com.example.lab08.strategy.DiscountContext;
import com.example.lab08.strategy.DiscountStrategy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private ProductRepository productRepository;
    private DiscountContext discountContext;

    public ProductService(ProductRepository productRepository, DiscountContext discountContext) {
        this.productRepository = productRepository;
        this.discountContext = discountContext;
    }

    public List<Product> getAllProduct() {
        List<Product> products = productRepository.findAll();
        products.forEach(product -> product.setDiscountedPrice(discountContext.calculateDiscount(product.getDiscountType(), product.getPrice())));
        return products;
    }

    public void insertProduct(Product product) {
        product.getReviews().forEach(review -> review.setProduct(product));
        productRepository.save(product);
    }
}
