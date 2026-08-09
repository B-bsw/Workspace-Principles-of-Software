package cp.com.lab076733800656sec2.strategy;

public class NoDiscountStrategy implements DiscountStrategy{
    @Override
    public double calculate(double price){
        return price;
    }
}
