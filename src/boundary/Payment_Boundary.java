package boundary;


import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import controller.GuestMrg;
import controller.OrderMrg;
import controller.PaymentMrg;
import controller.PromotionMrg;
import controller.ReservationMrg;
import controller.RoomMrg;
import entity.Guest;
import entity.Order;
import entity.Payment;
import entity.Promotion;
import entity.Reservation;
import entity.Reservation.ReservationStatus;
import entity.Room;

public class Payment_Boundary extends Boundary{
	
	private PaymentMrg paymentMrg = PaymentMrg.getInstance();
	
	public static Payment_Boundary getInstance() {
		return new Payment_Boundary();
	}

	public void paymentMain() {

		String promoCode;
		double discount = 0;
		String roomNum = readInputString("Enter room number");
		boolean success = ReservationMrg.getInstance().setCheckOutReservationByRoomNum(roomNum);
		
		if(success) {
			boolean hasPromotion = false;
			do {
			promoCode = readInputString("Enter Promtion Code (Enter 0 for no promotion): ");
			if(PromotionMrg.checkValidPromotionExist(promoCode)) {
				PromotionMrg.getInstance().setPromotionCode(promoCode);
				discount = PromotionMrg.getInstance().getDiscount();
				hasPromotion = true;
				break;
			}else if(promoCode.equalsIgnoreCase("0")) {
				promoCode = "No promotion";
				break;
			}else {
				System.out.println("The promotion does not exist");
			}
			}while(true);
		
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");		
			System.out.println("Date Check In: "+formatter.format(ReservationMrg.getInstance().getCheckIn()));
			System.out.println("Date Check Out:"+formatter.format(ReservationMrg.getInstance().getCheckOut()));
			
			double totalRoomCharge = 0;
			double roomCharge = RoomMrg.getInstance().getRoomCharge(ReservationMrg.getInstance().getCheckIn(),ReservationMrg.getInstance().getCheckOut());
			RoomMrg.getInstance().printRoomInfo();
			System.out.println("Total Room Charge: $"+ String.format("%.2f", roomCharge));
			
			double totalRoomServiceCharge = 0;
			RoomMrg.getInstance().printOrderByRoomNum();
			totalRoomServiceCharge = RoomMrg.getInstance().calculateRoomServiceCharge();
			System.out.println("Room Service Charge: $"+String.format("%.2f", totalRoomServiceCharge));
			

			System.out.println("Discount: " + discount+" % ("+promoCode+")");		
			System.out.println("Tax: "+TAX + "%");
			
			double totalPay = (roomCharge + totalRoomServiceCharge) * (1-discount) * (1+ TAX);
			System.out.println("Total Price: $"+String.format("%.2f", totalPay) );
		}else {
			System.out.println("Unable to check out");
		}
		
		
	

		

		
		String choice;
		String paymentMethod = "CASH";
		String creditCard = null;
		do {
		System.out.println("Select Payment Mode\n"
				+ "1. Credit/Debit Card\n"
				+ "2. Cash");
		choice = sc.nextLine();
		switch(choice) {
		case "1":
			paymentMethod = "CARD";
			Guest g = GuestMrg.getInstance().getGuestByIC(reservation.getGuestIC());
			if(g.getCreditCard()!= null) {
				char confirm;
				do {
				System.out.println("Use existing card details for this payment(Y/N)");
				confirm = sc.nextLine().toUpperCase().charAt(0);
				if(confirm == 'Y') {
					creditCard = g.getCreditCard();
				}else if(confirm == 'N'){
					System.out.println("Enter new card details for this payment: ");
					creditCard = sc.nextLine();
				}
				}while(confirm != 'Y' || confirm !='N');
			}else {
			System.out.println("Enter card details for this payment: ");
			creditCard = sc.nextLine();
			}
        	break;
		case "2":
			 paymentMethod = "CASH";
			default:break;
		}
		}while(!(choice.equalsIgnoreCase("1")||choice.equalsIgnoreCase("2")));
		
		payment =  new Payment(code,promoCode,totalRoomCharge,totalRoomServiceCharge,TAX,discount,totalPay,PaymentMrg.strToPaymentMethod(paymentMethod),creditCard);
		paymentMrg.createPayment(payment);
	}
	

	 public void loadData() {
	        // TODO Auto-generated method stub
	        try {
	        	paymentMrg.loadPaymentData();
	        } catch (FileNotFoundException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	    }

		@Override
		public void displayMain() {
			// TODO Auto-generated method stub

		}
}
