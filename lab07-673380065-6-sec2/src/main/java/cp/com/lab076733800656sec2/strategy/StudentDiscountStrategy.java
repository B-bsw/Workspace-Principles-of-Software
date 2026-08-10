package cp.com.lab076733800656sec2.strategy;

public class StudentDiscountStrategy implements DiscountStrategy {

    @Override
    public double calculate(double price) {
        return price - price * 0.1;
    }

    @Override
    public String discountName() {
        return "ส่วนลดนักศึกษา 10%";
    }
}
