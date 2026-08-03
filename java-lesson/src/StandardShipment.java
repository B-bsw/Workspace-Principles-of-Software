public class StandardShipment extends Shipment {

    public StandardShipment(double weight) {
        super(weight);
    }

    @Override
    public double calculatePayment() {
        return weight * 40;
    }

    @Override
    public String getType() {
        return "Standard";
    }
}