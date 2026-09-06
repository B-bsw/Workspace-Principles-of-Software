package com.example.lab08.strategy;

import org.springframework.stereotype.Component;

@Component
public class SeasonalSaleStrategy implements DiscountStrategy {
    @Override
    public double calculateDiscount(double price) {
        return price * 0.2;
    }

    @Override
    public String getType() {
        return "SEASONAL";
    }
}
