package cp.com.lab076733800656sec2.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "game")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    private String genre;

    private String platform;

    private double rating;

    private LocalDate releaseDate;

    private double price;

    @Nullable
    private String discountType;

    public String getDiscountName() {
        return discountType;
    }

    public double getFinalPrice() {
        if (discountType == null) {
            return price;
        }

        return price;
    }
}
