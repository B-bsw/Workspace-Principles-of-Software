package cp.com.lab076733800656sec2.model;

import cp.com.lab076733800656sec2.strategy.DiscountContext;
import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "games")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    private String genre;

    private String platform;

    private double rating;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate releaseDate;

    private double price;

    @Nullable
    private String discountType;

    public String getDiscountName() {
        if (discountType.equals("NONE")) {
            return "ราคาปกติ";
        } else if (discountType.equals("STUDENT")) {
            return "ส่วนลดนักศึกษา 10%";
        } else if (discountType.equals("SEASONAL")) {
            return "ส่วนลดเทศกาล 20%";
        }
        return discountType;
    }

    public double getFinalPrice() {
        DiscountContext discountContext = new DiscountContext();

        return discountContext.matchingDiscount(price, discountType);
    }
}
