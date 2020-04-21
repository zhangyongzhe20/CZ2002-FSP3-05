package boundary;


import java.io.FileNotFoundException;
import java.util.Scanner;

import controller.GuestMrg;
import controller.ReservationMrg;
import controller.RoomMrg;

public class HotelApp {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String choice;
		try {
			GuestMrg.getInstance().loadGuestData();
			RoomMrg.getInstance().loadRoomData();
			ReservationMrg.getInstance().loadReservationData();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		do {
			Boundary main = Boundary.getInstance();
			main.displayMain();
			choice = sc.nextLine();
			switch (choice) {
			case "1":	
				main = new Guest_Boundary();
				main.displayMain();
				break;
			case "2":
				main = new Room_Boundary();
				main.displayMain();
				break;
			case "3":
				main = new Reservation_Boundary();
				main.displayMain();
				break;
			case "4":			
				main = new Order_Boundary();
				main.displayMain();
				break;
			case "5":
				break;
			}
		} while (!(choice.equalsIgnoreCase("6")));

