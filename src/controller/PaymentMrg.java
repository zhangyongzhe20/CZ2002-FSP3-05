package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import entity.*;
import entity.Payment.PaymentMethod;


public class PaymentMrg {
    /**
     * Consider the guest could visit the hotel more than once
     */
	public static List<Payment> payments = new ArrayList<Payment>();
	final static String fileName = "payment_data.txt";
	private Payment payment;
	
	public static PaymentMrg getInstance() {
		return new PaymentMrg();
	}
	
	

	
	public static PaymentMethod strToPaymentMethod(String strPaymentMethod) {
		PaymentMethod paymentMethod = null;
		if (strPaymentMethod.equalsIgnoreCase("CARD")) {
			paymentMethod = Payment.PaymentMethod.CARD;
		} else if (strPaymentMethod.equalsIgnoreCase("CASH")) {
			paymentMethod = Payment.PaymentMethod.CASH;
		} 
		return paymentMethod;
	}
	
    public void createPayment(Payment payment) {
    	payments.add(payment);
    	try {
			writePaymentData();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    public Payment getPaymentById(String id) {
    	for (Payment p : payments) {
    		if(p.getReservationCode().equalsIgnoreCase(id)) {
    			return p;
    		}
    	}
    	return null;
    }
    
	public void loadPaymentData() throws FileNotFoundException {
		File file = new File(fileName);
		try {
			file.createNewFile();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		Scanner sc = new Scanner(file);
		String data;
		while (sc.hasNextLine()) {
			data = sc.nextLine();
			String[] temp = data.split(",");
			String reservationCode = temp[0];
			String promoCode = temp[1];
			double roomCharge =Double.parseDouble(temp[2]);
			double roomServiceCharge =Double.parseDouble(temp[3]);
			double tax =Double.parseDouble(temp[4]);
			double discount =Double.parseDouble(temp[5]);
			double totalPay =Double.parseDouble(temp[6]);
			String paymentMethod =temp[7];
			String creditCard = temp[8];
			Payment p = new Payment(reservationCode,promoCode,roomCharge,roomServiceCharge,tax,discount,totalPay,strToPaymentMethod(paymentMethod),creditCard);
			payments.add(p);
		}
		sc.close();
	}

	public void writePaymentData() throws IOException {
		FileWriter fileWriter = new FileWriter(fileName);
		PrintWriter fileOut = new PrintWriter(fileWriter);
		if (payments.size() > 0) {
			for (Payment payment : payments) {
				fileOut.print(payment.getReservationCode() + ",");
				fileOut.print(payment.getPromoCode() + ",");
				fileOut.print(payment.getRoomCharge() + ",");
				fileOut.print(payment.getRoomServiceCharge() + ",");
				fileOut.print(payment.getTax() + ",");
				fileOut.print(payment.getDiscount() + ",");
				fileOut.print(payment.getTotalPay() + ",");
				fileOut.print(payment.getPaymentMethod() + ",");
				fileOut.print(payment.getCreditCard()+",");
				fileOut.println();
			}
			System.out.println("finish writing");
			fileOut.close();
		}
	}
}