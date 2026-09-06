package com.example.lab08.strategy;

import org.springframework.stereotype.Component;

@Component
public class MemberDiscountStrategy implements DiscountStrategy{
    @Override
    public double calculateDiscount(double price) {
        return price * 0.1;
    }
    @Override
    public String getType() {
        return "MEMBER";
    }
}
