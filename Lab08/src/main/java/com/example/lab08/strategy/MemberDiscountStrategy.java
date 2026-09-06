package com.example.lab08.strategy;

import org.springframework.stereotype.Component;

@Component("MEMBER")
public class MemberDiscountStrategy implements DiscountStrategy{
    @Override
    public double calculateDiscount(double price) {
        return price * 0.90;
    }
    @Override
    public String getType() {
        return "MEMBER";
    }
}
