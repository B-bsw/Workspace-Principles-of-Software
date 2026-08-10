package cp.com.lab076733800656sec2.strategy;

public class DiscountContext {

    private DiscountStrategy getStrategy(String discountType) {
        switch (discountType) {
            case "STUDENT":
                return new StudentDiscountStrategy();
            case "SEASONAL":
                return new SeasonalSaleStrategy();
            default:
                return new NoDiscountStrategy();
        }
    }

    public String getDiscountName(String discountType) {
        return getStrategy(discountType).discountName();
    }

    public double getDiscountPrice(double price, String discountType) {
        return getStrategy(discountType).calculate(price);
    }
}
