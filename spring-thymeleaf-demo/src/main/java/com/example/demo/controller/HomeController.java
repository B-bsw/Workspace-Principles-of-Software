package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("message", "Sorawit thassadorn");
        model.addAttribute(
            "studentId",
            "สวัสดี สรวิชญ์ ทัศดร (รหัส 673380065-6)"
        );
        return "home"; // ไม่ใช่ path ไฟล์ แค่ "ชื่อ view" เชิงตรรกะเท่านั้น
    }

    @GetMapping("/about")
    public String about(Model model) {
        return "about";
    }
}
