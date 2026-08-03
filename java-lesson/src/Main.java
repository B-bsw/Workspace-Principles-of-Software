import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        List<Shipment> shipments = new ArrayList<>();

        shipments.add(new StandardShipment(5));   // 200 บาท
        shipments.add(new ExpressShipment(2));    // 200 บาท
        shipments.add(new StandardShipment(8));   // 320 บาท
        shipments.add(new ExpressShipment(4));    // 400 บาท

        double total = 0;

        System.out.println("รายการ Shipment");
        System.out.println("--------------------------------");

        for (Shipment s : shipments) {
            double payment = s.calculatePayment();

            System.out.println(
                    "ประเภท : " + s.getType() +
                            "\tน้ำหนัก : " + s.weight +
                            " kg\tค่าขนส่ง : " + payment + " บาท"
            );

            total += payment;
        }

        System.out.println("--------------------------------");
        System.out.println("ยอดรวมการชำระ = " + total + " บาท");
    }
}