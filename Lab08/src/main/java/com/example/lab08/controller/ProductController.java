package com.example.lab08.controller;

import com.example.lab08.model.Product;
import com.example.lab08.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {
    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping()
    public String listProductPage(Model model) {
        model.addAttribute("products", productService.getAllProduct());
        return "products/list";
    }

    @GetMapping("/add")
    public String addProductPage(Model model) {
        model.addAttribute("product", new Product());
        return "products/add";
    }

    @PostMapping("/save")
    public String insertProduct(Model model, @ModelAttribute Product product) {
        productService.insertProduct(product);
        model.addAttribute("message", "Product has been inserted");
        return "redirect:/products";
    }
}
