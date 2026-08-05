package cp.com.lab076733800656sec2.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;

public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;
    private String genre;
    private String platform;
    private double rating;
    private LocalDate releaseDate;
    private double price;
    private String discountType;
}
