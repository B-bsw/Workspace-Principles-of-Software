package com.example.lab08.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String reviewer;
    @Column
    private Integer rating;
    @Column
    private String comment;
    @Column
    private LocalDate reviewDate;

    // FK อยู่ที่ฝั่ง Many เสมอ
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
