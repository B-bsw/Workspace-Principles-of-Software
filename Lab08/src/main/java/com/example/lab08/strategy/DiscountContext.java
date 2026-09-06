package com.example.lab08.strategy;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class DiscountContext {
   private Map<String, DiscountStrategy> discountStrategies;

   public DiscountContext(Map<String, DiscountStrategy> discountStrategies) {
       this.discountStrategies = discountStrategies;
   }
}
