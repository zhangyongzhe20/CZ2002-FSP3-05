import java.util.ArrayList;
import java.util.Scanner;

public class HotelApp {
	public static void main(String[] args) {
		 //Load data from text file
		 ArrayList<Guest> guestList = new ArrayList<Guest>();

		 Scanner sc = new Scanner(System.in);
		 int choice;
		 do {		
			 printMenu();
			 choice = sc.nextInt();
	    
			 switch(choice) {
			 	case 1:
			 		GuestManager.printGuestMenu(guestList);
			 	case 2:
			 	case 3:
			 	case 4:
			 	case 5:
			 	case 6:
			 		}
			 }while(choice !=6);
	}
	
	
	public static void printMenu() {
		System.out.println("Hotel Reservation and Payment System (HRPS)");
		System.out.println("===========================================");
		System.out.println("1. About Guest");
		System.out.println("2. About Room");
		System.out.println("3. About Reservation");
		System.out.println("4. About Room Service");
		System.out.println("5. About Payment");
		System.out.println("6. Quit");
		System.out.println("============================================");
	}
}
