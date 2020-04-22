package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import entity.Guest;
import entity.Guest.IdentityType;
import entity.Reservation;
import entity.Room;

public class GuestMrg {

	private static List<Guest> guests = new ArrayList<Guest>();
	final static String FILENAME = "guest_data.txt";
	private Guest guest;

	public static GuestMrg getInstance() {
		GuestMrg guestMrg = new GuestMrg();
		return guestMrg;
	}

	public void setGuestIC(String ic) {
		guest.setIC(ic);
	}

	public void updateGuest() {
		try {
			writeGuestData();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public String getCreditCard() {
		return guest.getCreditCard();
	}
	public static IdentityType strToIdentityType(String strIdentityType) {
		IdentityType identityType = null;
		if (strIdentityType.equalsIgnoreCase("PASSPORT")) {
			identityType = IdentityType.Passport;
		} else if (strIdentityType.equalsIgnoreCase("DRIVING LICENSE")) {
			identityType = IdentityType.DrivingLicense;
		}
		return identityType;
	}

	public void createGuest(Guest guest) {
		guests.add(guest);

		try {
			writeGuestData();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public Guest getGuestByIC(String ic) {
		Guest g = null;
		for (Guest guest : guests) {
			if (guest.getIC().equalsIgnoreCase(ic)) {
				g = guest;
			}
		}
		return g;
	}

	public List<Guest> getGuestByName(String name) {
		List<Guest> guestList = new ArrayList<>();
		for (Guest guest : guests) {
			System.out.println(guest.getGuestName());
			if (guest.getGuestName().equalsIgnoreCase(name)) {
				guestList.add(guest);
			}
		}
		return guestList;
	}

	public Guest getGuestByRoomNum(String roomNum) {
		Guest g = null;
		Reservation r = ReservationMrg.getInstance().getReservationByRoomNum(roomNum);
		if (r != null) {
			g = getGuestByIC(r.getGuestIC());
		}
		return g;
	}

	public static boolean checkGuestExist(String guestName) {
		for (Guest guest : guests) {
			if (guest.getGuestName().equalsIgnoreCase(guestName)) {
				return true;
			}
		}
		return false;
	}

	public void loadGuestData() throws FileNotFoundException {
		File file = new File(FILENAME);

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
			Guest guest = new Guest(temp[0], temp[1], temp[2], temp[3], temp[4], GuestMrg.strToIdentityType(temp[5]), temp[6], temp[7], temp[8]);
			guests.add(guest);
		}
		sc.close();
		return guests;
	}

	public void writeGuestData() throws IOException {
		FileWriter fileWriter = new FileWriter(FILENAME);
		PrintWriter fileOut = new PrintWriter(fileWriter);
		if (guests.size() > 0) {
			for (Guest guest : guests) {
				fileOut.print(guest.getGuestName() + ",");
				fileOut.print(guest.getCreditCard() + ",");
				fileOut.print(guest.getAddress() + ",");
				fileOut.print(guest.getCountry() + ",");
				fileOut.print(guest.getGender() + ",");
				fileOut.print(guest.getIdentityType() + ",");
				fileOut.print(guest.getIC() + ",");
				fileOut.print(guest.getNationality() + ",");
				fileOut.print(guest.getContact() + ",");
				fileOut.println();
			}
			System.out.println("finish writing");
			fileOut.close();
		}
	}

	public void printGuestInfoByName(String name) {
		List<Guest> guestList = getGuestByName(name);
		if (guestList.size() > 0) {
			for (Guest g : guestList) {
				g.printGuestInfo();
			}
		} else {
			System.out.println("No guest found by the name " + name);
		}
	}
}