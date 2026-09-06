package com.example.lab08.strategy;

import com.example.lab08.model.Product;

public interface DiscountStrategy {
    double calculateDiscount(double price);
    String getType();
}
