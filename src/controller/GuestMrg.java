package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import entity.Guest;
import entity.Guest.IdentityType;
import entity.Reservation;
import entity.Room;
import entity.Reservation.CheckInType;

public class GuestMrg {

	static List<Guest> guests = new ArrayList<Guest>();
	final static String fileName = "guest_data.txt";

	public static GuestMrg getInstance() {
		GuestMrg guestMrg = new GuestMrg();
		return guestMrg;
	}
	
	public static IdentityType strToIdentityType(String strIdentityType) {
		IdentityType identityType = null;
		if (strIdentityType.equalsIgnoreCase("PASSPORT")) {
			identityType = IdentityType.PASSPORT;
		} else if (strIdentityType.equalsIgnoreCase("DRIVING LICENSE")) {
			identityType = IdentityType.DRIVING_LICENSE;
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
		List<Guest> guestList = new ArrayList<Guest>();
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

	public void updateGuest(Guest guest) {
		for(Guest g : guests) {
			if(guest.getIC().equalsIgnoreCase(g.getIC())) {
				g.setAddress(guest.getAddress());
				g.setContact(guest.getContact());
				g.setCountry(guest.getCountry());
				g.setCreditCard(guest.getCreditCard());
				g.setGender(guest.getGender());
				g.setGuestName(guest.getGuestName());
				g.setIdentityType(guest.getIdentityType());
				g.setNationality(guest.getNationality());
			}
		}
	}

	public void loadGuestData() throws FileNotFoundException {
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
			Guest guest = new Guest(temp[0], temp[1], temp[2], temp[3], temp[4], GuestMrg.strToIdentityType(temp[5]), temp[6], temp[7], temp[8]);
			guests.add(guest);
		}
		sc.close();
	}

	public void writeGuestData() throws IOException {
		FileWriter fileWriter = new FileWriter(fileName);
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

}