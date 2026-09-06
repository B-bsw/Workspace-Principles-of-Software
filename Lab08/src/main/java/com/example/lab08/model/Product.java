package com.example.lab08.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private String category;
    @Column
    private String brand;
    @Column
    private Integer stock;
    @Column
    private Double price;
    @Column
    private String discountType;

    @OneToOne(cascade = ALL)
    @JoinColumn(name = "detail_id", referencedColumnName = "id")
    private ProductDetail detail;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Review> reviews =  new ArrayList<>();
}
