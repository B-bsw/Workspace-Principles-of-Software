package cp.com.lab076733800656sec2.strategy;

public class SeasonalSaleStrategy implements DiscountStrategy {

    @Override
    public double calculate(double price) {
        return price - price * 0.2;
    }

    @Override
    public String discountName() {
        return "ส่วนลดเทศกาล 20%";
    }
}
