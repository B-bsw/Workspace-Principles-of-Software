package com.cp.lab10;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cp.lab10.repository.ProductRepository;

/**
 * AppConfig — Spring Bean Configuration
 *
 * ✅ ไฟล์นี้เตรียมไว้ให้ครบแล้ว ไม่ต้องแก้ไข
 *
 * ProductRepository ไม่มี @Repository annotation
 * เพราะไม่ต่อ Database จริง — ต้องประกาศ @Bean เอง
 */
@Configuration
public class AppConfig {

    @Bean
    public ProductRepository productRepository() {
        return new ProductRepository();
    }
}
