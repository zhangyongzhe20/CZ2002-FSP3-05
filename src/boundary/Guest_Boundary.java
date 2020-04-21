package boundary;

import entity.Guest;
import entity.Room;
import controller.GuestMrg;
import controller.RoomMrg;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Guest_Boundary {
	private Scanner sc = new Scanner(System.in);
	private GuestMrg guestMrg = GuestMrg.getInstance();
	private Guest guest;
	public static void guestMain() {
		System.out.println("Guest System\n" + "0. Return to Main Menu\n" + "1. Create Guest\n" + "2. Update Guest\n"
				+ "3. Find Guest\n");
	}

	private void createGuestMenu() {
		char confirm;
		guest = new Guest();
		enterIC();
		if(guestMrg.getGuestByIC(guest.getIC()) == null) {
			enterIdentityType();
			enterName();
			enterGender();
			enterContact();
			enterAddress();
			enterCountry();
			enterNationality();
			enterCreditCard();
			
			guest.printGuestInfo();
			
			System.out.println(
					"Press Y to confirm," + "N to discard and " + "(No.) to edit a field and (No.) to edit a field.");
			confirm = sc.nextLine().charAt(0);
			switch (confirm) {
			case 'Y':
				break;
			case 'N':
				break;
			case '1':
				enterIdentityType();
				break;
			case '2':
				enterName();
				break;
			case '3':
				enterGender();
				break;
			case '4':
				enterContact();
				break;
			case '5':
				enterAddress();
				break;
			case '6':
				enterNationality();
				break;
			case '7':
			 enterGender();
				break;
			case '8':
				enterContact();
				break;
			case '9':
				enterCreditCard();
				break;
			default:
				break;
			}
		}else {
			System.out.println("Guest already exist");
		}
	}

	private void enterIdentityType() {
		String choice = null;
	do {
		System.out.println("Create Guest\n" + "Type of ID:\n" + "1. Passport\n" + "2. Driving License\n");
		if(choice.equalsIgnoreCase("1")) {
			choice = sc.nextLine();
			guest.setIdentityType(GuestMrg.strToIdentityType("PASSPORT"));
		}else if(choice.equalsIgnoreCase("2")) {
			guest.setIdentityType(GuestMrg.strToIdentityType("DRIVING LICENSE"));
		}
	}while(!(choice.equalsIgnoreCase("1")||choice.equalsIgnoreCase("2")));

		
	}

	private void enterIC() {
		System.out.println("IC:");
		String ic = sc.nextLine();
		guest.setIC(ic);
	
	}

	private void  enterName() {
		System.out.println("Name:");
		String name = sc.nextLine();
		guest.setGuestName(name);
	
	}

	private void  enterAddress() {
		System.out.println("Address:");
		String address = sc.nextLine();
		guest.setAddress(address);
		
	}

	private void  enterCountry() {
		System.out.println("Country:");
		String country = sc.nextLine();
		guest.setCountry(country);
	
	}

	private void  enterNationality() {
		System.out.println("Nationality:");
		String nationality = sc.nextLine();
		guest.setNationality(nationality);
		
	}

	private void  enterGender() {
		System.out.println("Gender:");
		String gender = sc.nextLine();
		guest.setGender(gender);
	
	}

	private void  enterContact() {
		System.out.println("Contact:");
		String contact = sc.nextLine();
		guest.setContact(contact);
	
	}

	private void  enterCreditCard() {
		System.out.println("Credit Card:");
		String creditCard = sc.nextLine();
		guest.setCreditCard(creditCard);

	}

}