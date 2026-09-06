package com.example.lab08.strategy;

import org.springframework.stereotype.Component;

@Component
public class NoDiscountStrategy implements DiscountStrategy {
    @Override
    public double calculateDiscount(double price) {
        return price;
    }
    @Override
    public String getType() {
        return "NONE";
    }
}
