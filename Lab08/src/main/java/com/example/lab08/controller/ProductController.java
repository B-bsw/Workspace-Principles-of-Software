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

    @GetMapping("/edit/{id}")
    public String editProductPage(Model model, @PathVariable Long id) {
        model.addAttribute("product", productService.getProductById(id));
        return "products/edit";
    }

    @PostMapping("/update/{id}")
    public String updateProduct(Model model, @PathVariable Long id, @ModelAttribute Product product) {
        productService.editProductById(id, product);
        return "redirect:/products";
    }

    @GetMapping("/delete/{id}")
    public String deleteProductPage(Model model, @PathVariable Long id) {
        model.addAttribute("product", productService.getProductById(id));
        return "products/delete";
    }

    @PostMapping("/delete/{id}")
    public String deleteProduct(Model model, @PathVariable Long id) {
        productService.deleteProductById(id);
        model.addAttribute("message", "Product has been deleted");
        return "redirect:/products";
    }
}
