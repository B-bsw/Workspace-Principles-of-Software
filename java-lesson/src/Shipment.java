abstract class Shipment {
    double weight;

    public Shipment(double weight) {
        this.weight = weight;
    }

    public abstract double calculatePayment();

    public abstract String getType();
}