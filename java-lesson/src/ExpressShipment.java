public class ExpressShipment extends Shipment {

    public ExpressShipment(double weight) {
        super(weight);
    }

    @Override
    public double calculatePayment() {
        return weight * 100;
    }

    @Override
    public String getType() {
        return "Express";
    }
}