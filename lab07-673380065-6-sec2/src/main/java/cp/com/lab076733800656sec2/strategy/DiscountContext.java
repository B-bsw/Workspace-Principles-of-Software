package cp.com.lab076733800656sec2.strategy;

public class DiscountContext {

    public double matchingDiscount(double price, String discountType) {
        DiscountStrategy discountStrategy;

        if (discountType.equals("STUDENT")) {
            discountStrategy = new StudentDiscountStrategy();
        } else if (discountType.equals("SEASONAL")) {
            discountStrategy = new SeasonalSaleStrategy();
        } else {
            discountStrategy = new NoDiscountStrategy();
        }

        return discountStrategy.calculate(price);
    }
}
