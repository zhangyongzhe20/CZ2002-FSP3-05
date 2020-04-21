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

	private Scanner sc = new Scanner(System.in);
	private PaymentMrg paymentMrg = PaymentMrg.getInstance();
	private Payment payment;
	final static double TAX = 17.0;
	
	public static Payment_Boundary getInstance() {
		return new Payment_Boundary();
	}
	public void paymentMain(String code) {
		String promoCode;
		Promotion p = null;
		do {
		System.out.println("Enter Promtion Code (Enter 0 to exit): ");
		promoCode = sc.nextLine();
		 	if(promoCode.equalsIgnoreCase("0")) {
		 		break;
		 	}else {
		 	 p  = PromotionMrg.getInstance().getPromotionByPromotionCode(promoCode);
		 	 if(!(p.getPromoStartDate().isBefore(LocalDateTime.now())&& p.getPromoEndDate().isAfter(LocalDateTime.now()))) {
		 	System.out.println("The promotion has already expired");
		 		 p = null;
		 	 }
		 		if(p != null) {
		 			break;
		 		}
		 	}
		}while(true);
		
		Reservation reservation = ReservationMrg.getInstance().getReservationByCode(code);
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");		
		System.out.println("Date Check In: "+formatter.format(reservation.getCheckIn()));
		System.out.println("Date Check Out:"+formatter.format(reservation.getCheckOut()));
		
		double totalRoomCharge = 0;
		Room r = RoomMrg.getInstance().getRoomByRoomNum(reservation.getRoomNum());
		double roomCharge = RoomMrg.getInstance().getRoomCharge(r , reservation.getCheckIn(),reservation.getCheckOut());
		System.out.println("Room Number: "+ r.getRoomNumber() + " Room Charge: $"+ String.format("%.2f", roomCharge));
		
		
		double totalRoomServiceCharge = 0;
		List<Order> orderList = OrderMrg.getInstance().searchOrderByRoomNum(reservation.getRoomNum());
		for(Order order : orderList) {
			order.printOrderInfo();
		}
		OrderMrg.getInstance();
		totalRoomServiceCharge = OrderMrg.calculateRoomServiceCharge(reservation.getRoomNum());
		
		System.out.println("Room Service Charge: $"+String.format("%.2f", totalRoomServiceCharge));
		
		double discount = 0;
		String promoCodeStr = "No promotion";
		if(p!=null) {
			discount = p.getDiscount();
			promoCodeStr = p.getPromotionCode();
		}
		System.out.println("Discount: " + discount+" % ("+promoCodeStr+")");
		
		System.out.println("Tax: "+TAX + "%");
		
		double totalPay = (roomCharge + totalRoomServiceCharge) * (1-discount) * (1+ TAX);
		System.out.println("Total Price: $"+String.format("%.2f", totalPay) );
		
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
	
	public void checkOutMenu() {
	   String roomNum = readInputString("Enter room number");
	   Reservation r = ReservationMrg.getInstance().getReservationByRoomNum(roomNum);
	   if(r!=null && r.getReservationStatus().equals(ReservationStatus.CHECKIN)) {
		   paymentMain(r.getReservationCode());
	   }
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
}
