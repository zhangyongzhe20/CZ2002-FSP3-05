package entity;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Payment {
	private String reservationCode;
	private String promoCode;
    private double roomCharge;
    private double roomServiceCharge;
    private double tax;
    private double discount;
    private double totalPay;
    private PaymentMethod paymentMethod;
    private String creditCard;

    public Payment(String reservationCode, String promoCode, double roomCharge, double roomServiceCharge, double tax,
    	 double discount, double totalPay, PaymentMethod paymentMethod , String creditCard ) {
    	this.reservationCode = reservationCode;
    	this.promoCode = promoCode;
        this.roomCharge = roomCharge;
        this.tax = tax;
        this.roomServiceCharge = roomServiceCharge;
        this.discount = discount;
        this.paymentMethod = paymentMethod;
        this.totalPay = totalPay;
        this.creditCard = creditCard;
    }

    public enum PaymentMethod {
    	CARD , CASH
    }
    
	public String getReservationCode() {
		return reservationCode;
	}

	public void setReservationCode(String reservationCode) {
		this.reservationCode = reservationCode;
	}

	
	public String getPromoCode() {
		return promoCode;
	}

	public void setPromoCode(String promoCode) {
		this.promoCode = promoCode;
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

	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(String creditCard) {
		this.creditCard = creditCard;
	}

	public void printPaymentInfo() {
		
		System.out.println("Total Service Charge: $" + String.format("%.2f", roomServiceCharge));
		System.out.println("Total Room Charge: $" + String.format("%.2f", roomCharge));
		if(promoCode != null) {
		System.out.println("Discount: " + discount + " % (" + promoCode + ")");
		}else {
			System.out.println("Discount: " + discount + " % (No promotion)");
		}
		System.out.println("Tax: " + tax + "%");
		System.out.println("Total Price: $" + String.format("%.2f", totalPay));
		System.out.println("Payment pay by: " + paymentMethod);
		if(paymentMethod.equals(PaymentMethod.CARD)) {
		System.out.println("Credit card :" + creditCard);
		System.out.println(" -------------------------------------------");
		}
		
	}
  
}
