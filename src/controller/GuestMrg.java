import java.util.ArrayList;
import java.util.Scanner;

public class GuestMrg {

	public static void printGuestMenu(ArrayList<Guest> guestList) {
		Scanner sc = new Scanner(System.in);
		int choice;
		do {
		System.out.println("               About Guest			 	   ");
		System.out.println("===========================================");
		System.out.println("1. Create New Guest");
		System.out.println("2. Update Guest Details");
		System.out.println("3. Search Guest Details");
		System.out.println("4. Previous");
		System.out.println("===========================================");
		System.out.println("Enter your choice: ");
	    choice = sc.nextInt();
		GuestManager.selectedChoice(choice, guestList);
		}while(choice !=4);
		sc.close();
	}
	
	public static void selectedChoice(int choice ,ArrayList<Guest> guestList) {
		switch (choice) {
		case 1:
			System.out.println("Creating new guest");
			GuestManager.createGuest(guestList);
			break;
		case 2:
			System.out.println("updating guest deatils");
			GuestManager.updateGuest(guestList);
			break;
		case 3:
			System.out.println("Searching guest details");
			GuestManager.searchGuest(guestList);
			break;
		case 4:
			//update database
			break;
		}
	}
	
	public static int guestExist(String data , String type , ArrayList<Guest> guestList) {
		//return index if guest found on ArrayList<Guest> else -1 
		int exist = -1;
		if (type == "ic") {
			    
		 }else {
			 
		 }
		 return exist;
	}
	
	public static void createGuest(ArrayList<Guest> guestList) {
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Enter guest IC number: ");
		String ic = sc.nextLine();
		 
		if(GuestManager.guestExist(ic , "ic" ,  guestList) == -1) {
			//Create new Guest Object , add to guestList
		}else {
			
		}
		sc.close();
	}
	
	public static void searchGuest(ArrayList<Guest> guestList) {
		Scanner sc = new Scanner(System.in);
		ArrayList<Guest> guestList2 = new ArrayList<Guest>();
		
		System.out.println("Enter guest name to be searched: ");
		String name = sc.nextLine();
		for(Guest guest : guestList) {
			if (guest.getGuestName().compareToIgnoreCase(name) == 1) {
				guestList2.add(guest);
			}
		}
	    if(!guestList2.isEmpty()) {
	    	
	    }else {
	    	
	    }
		sc.close();
	}
	public static void updateGuest(ArrayList<Guest> guestList) {
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Enter guest IC number: ");
		String ic  = sc.nextLine();
		int index = GuestManager.guestExist(ic , "ic" ,  guestList);
		if(index != -1) {
			//Found Guest and update guest in guestList
		}else {
			
		}
		sc.close();
	}
}

