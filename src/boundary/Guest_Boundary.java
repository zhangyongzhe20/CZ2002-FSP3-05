package boundary;

import entity.Guest;
import controller.GuestMrg;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Guest_Boundary {
	static Scanner sc = new Scanner(System.in);
	static String IC;
	static String name;
	static String address;
	static String country;
	static String identityType;
	static String nationality;
	static String gender;
	static String contact;
	static String creditCard;

	public static void guestMain() {
		System.out.println("Guest System\n" + "0. Return to Main Menu\n" + "1. Create Guest\n" + "2. Update Guest\n"
				+ "3. Find Guest\n");
	}

	private static void createGuest() {
		Character confirm;
		System.out.println("Create Guest\n" + "Type of ID:\n" + "1. Passport\n" + "2. Driving License\n");

		int selection = sc.nextInt();
		sc.nextLine();
		identityType = identityType(selection);
		name = name();
		address = address();
		country = country();
		nationality = nationality();
		gender = gender();
		contact = contact();
		creditCard = creditCard();

		printGuestInfo();

		System.out.println(
				"Press Y to confirm," + "N to discard and " + "(No.) to edit a field and (No.) to edit a field.");
		confirm = sc.nextLine().charAt(0);
		switch (confirm) {
		case 'Y':
			List<String> roomNumList = new ArrayList<String>();
			roomNumList.add("10-10");
			roomNumList.add("10-11");
			roomNumList.add("10-12");
			roomNumList.add("10-13");
			Guest guest = new Guest(name, creditCard, address, country, gender, identityType, IC, nationality, contact,
					roomNumList);
			GuestMrg.createGuest(guest);
			break;
		case 'N':
			break;
		case '1':
			identityType = identityType(selection);
			break;
		case '2':
			IC = IC();
			break;
		case '3':
			name = name();
			break;
		case '4':
			address = address();
			break;
		case '5':
			country = country();
			break;
		case '6':
			nationality = nationality();
			break;
		case '7':
			gender = gender();
			break;
		case '8':
			contact = contact();
			break;
		case '9':
			creditCard = creditCard();
			break;
		default:
		}
	}

	public static String identityType(int selection) {
		if (selection == 1) {
			System.out.println("Passport:");
		} else {
			System.out.println("Driving license:");
		}
		String id = sc.nextLine();
		return id;
	}

	public static String IC() {
		System.out.println("IC:");
		String ic = sc.nextLine();
		return ic;
	}

	public static String name() {
		System.out.println("Name:");
		String name = sc.nextLine();
		return name;
	}

	public static String address() {
		System.out.println("Address:");
		String address = sc.nextLine();
		return address;
	}

	public static String country() {
		System.out.println("Country:");
		String country = sc.nextLine();
		return country;
	}

	public static String nationality() {
		System.out.println("Nationality:");
		String nationality = sc.nextLine();
		return nationality;
	}

	public static String gender() {
		System.out.println("Gender:");
		String gender = sc.nextLine();
		return gender;
	}

	public static String contact() {
		System.out.println("Contact:");
		String contact = sc.nextLine();
		return contact;
	}

	public static String creditCard() {
		System.out.println("Credit Card:");
		String creditCard = sc.nextLine();
		return creditCard;
	}

	public static void printGuestInfo() {
		String guestInfo = "Guest information:\n" + "1." + identityType + "\n" + "2." + IC + "\n" + "3." + name + "\n"
				+ "4." + address + "\n" + "5." + country + "\n" + "6." + nationality + "\n" + "7." + gender + "\n"
				+ "8." + contact + "\n" + "9." + creditCard;
		System.out.println(guestInfo);
	}

	public static void main(String[] args) {
		guestMain();
		int selection = sc.nextInt();
		if (selection == 1) {
			createGuest();
		}

	}

}