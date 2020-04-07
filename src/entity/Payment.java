import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

public class Payment {
    private LocalDateTime checkInTime;
    private LocalDateTime checkOutTime;
    private double roomCharge;
    private double tax;
    private double roomServiceCharge;
    private double discount;
    private double totalPay;
    private String paymentMethod;

    public Payment(LocalDateTime checkInTime, LocalDateTime checkOutTime, double roomCharge, double tax,
            double roomServiceCharge, double discount, String paymentMethod) {
        this.checkInTime = checkInTime;
        this.checkOutTime = checkOutTime;
        this.roomCharge = roomCharge;
        this.tax = tax;
        this.roomServiceCharge = roomServiceCharge;
        this.discount = discount;
        this.paymentMethod = paymentMethod;
        // not quite sure??
        this.totalPay = (roomCharge + roomServiceCharge) *(1+ tax) * (1-discount);
    }

    public LocalDateTime getCheckInTime() {
        return checkInTime;
    }

    public LocalDateTime getCheckOutTime() {
        return checkOutTime;
    }

    public double getRoomCharge() {
        return roomCharge;
    }

    public double getTax() {
        return tax;
    }

    public double getRoomServiceCharge() {
        return roomServiceCharge;
    }

    public double getDiscount() {
        return discount;
    }

    public double getTotalPay() {
        return totalPay;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
