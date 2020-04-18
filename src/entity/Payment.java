package entity;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

public class Payment {
	private String reservationCode;
	private String promoCode;
    private double roomCharge;
    private double tax;
    private double roomServiceCharge;
    private double discount;
    private double totalPay;
    private String paymentMethod;

    public Payment(String reservationCode, double roomCharge, double tax, double roomServiceCharge,
    	 double discount, double totalPay ,String paymentMethod ) {
    	this.reservationCode = reservationCode;
        this.roomCharge = roomCharge;
        this.tax = tax;
        this.roomServiceCharge = roomServiceCharge;
        this.discount = discount;
        this.paymentMethod = paymentMethod;
        this.totalPay = (roomCharge + roomServiceCharge) *(1+ tax) * (1-discount);
    }

	public String getReservationCode() {
		return reservationCode;
	}

	public void setReservationCode(String reservationCode) {
		this.reservationCode = reservationCode;
	}

	public double getRoomCharge() {
		return roomCharge;
	}

	public void setRoomCharge(double roomCharge) {
		this.roomCharge = roomCharge;
	}

	public double getTax() {
		return tax;
	}

	public void setTax(double tax) {
		this.tax = tax;
	}

	public double getRoomServiceCharge() {
		return roomServiceCharge;
	}

	public void setRoomServiceCharge(double roomServiceCharge) {
		this.roomServiceCharge = roomServiceCharge;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public double getTotalPay() {
		return totalPay;
	}

	public void setTotalPay(double totalPay) {
		this.totalPay = totalPay;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

  
}
