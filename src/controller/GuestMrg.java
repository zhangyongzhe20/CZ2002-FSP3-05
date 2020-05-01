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

public class GuestMrg {

	private static List<Guest> guests;
	private static Guest guest;
	private final static String FILENAME = "guest_data.txt";

	    /**
     * Applied Singelton Desgin Pattern in Mrg classes
     */
    private static GuestMrg SINGLE_INSTANCE;
    public static GuestMrg getInstance() {
        if (SINGLE_INSTANCE == null) {
            SINGLE_INSTANCE = new GuestMrg();
        }
        return SINGLE_INSTANCE;
	}
	
	public GuestMrg() {
        guests = new ArrayList<Guest>();
        guest = new Guest();
	}
	
	public void createNewGuest() {
		guest = new Guest();
	}
	public static boolean checkGuestExist(String ic) {
		boolean returnValue = false;
		for (Guest guest : guests) {
			if (guest.getIC().equalsIgnoreCase(ic)) {
				returnValue = true;
			}
		}
		return returnValue;
	}

	public void updateGuest() {
		int index = 0;
		for (Guest guest : guests) {
			if (guest.getGuestName().equalsIgnoreCase(guest.getGuestName())) {
				guests.set(index, guest);
			}	
      	index++;
		}
		try {
			writeGuestData();
			System.out.println("Guest information is updated successfully!");
			System.out.println("-------------------------------------------");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public String getCreditCardByGuestIC(String ic) {
		Guest g = getGuestByIC(ic);
		if(g!=null && g.getCreditCard() != null) {
			return g.getCreditCard();
		}
		return null;
	}
	public Guest getGuestByIC(String ic) {
		for(Guest guest : guests) {
			if(guest.getIC().equalsIgnoreCase(ic)) {
				return guest;
			}
		}
		return null;
	}
	public IdentityType strToIdentityType(String strIdentityType) {
		IdentityType identityType = null;
		if (strIdentityType.equalsIgnoreCase("PASSPORT")) {
			identityType = IdentityType.PASSPORT;
		} else if (strIdentityType.equalsIgnoreCase("DRIVING_LICENSE")) {
			identityType = IdentityType.DRIVING_LICENSE;
		}
		return identityType;
	}

	public void createGuest() {
		guests.add(guest);

		try {
			writeGuestData();
			System.out.println("Guest information is recorded successfully!");
			System.out.println("-------------------------------------------");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static boolean checkGuestByIC(String ic) {
		for (Guest guest_ : guests) {
			if (guest_.getIC().equalsIgnoreCase(ic)) {
				guest = guest_;
				return true;
			}
		}
		return false;
	}

	public static boolean checkGuestByName(String name) {
		for (Guest guest_ : guests) {
			if (guest_.getGuestName().equalsIgnoreCase(name)) {
				return true;
			}
		}
		return false;
	}



	
	public List<Guest> getGuestByName(String name) {
		List<Guest> guestList = new ArrayList<>();
		for (Guest guest : guests) {
			if (guest.getGuestName().equalsIgnoreCase(name)) {
				guestList.add(guest);
			}
		}
		return guestList;
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
			if(!data.isEmpty()){
			String[] temp = data.split(",");
			Guest guest = new Guest();
			guest.setGuestName(temp[0]);
			guest.setCreditCard(temp[1]);
			guest.setAddress(temp[2]);
			guest.setCountry(temp[3]);
			guest.setGender(temp[4]);
			guest.setIdentityType(strToIdentityType(temp[5]));
			guest.setIC(temp[6]);
			guest.setNationality(temp[7]);
			guest.setContact(temp[8]);
			guests.add(guest);
		}
	}
		sc.close();
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
			fileOut.close();
		}
	}

	public void printGuestInfo() {
	  guest.printGuestInfo();
	}
	public List<String> getGuestsICByGuestName(String name){
		List<String> icList = new ArrayList<String>();
		List<Guest> guestList = getGuestByName(name);
		for(Guest guest : guestList) {
			if(guest.getGuestName().equalsIgnoreCase(name)) {
				icList.add(guest.getIC());
			}
		}
		return icList;
	}
	public void printGuestInfoByGuestName(String guestName) {
		List<Guest> guestList = getGuestByName(guestName);
		if(guestList.size() > 0) {
		for(Guest guest : guestList) {
			if(guest.getGuestName().equalsIgnoreCase(guestName))
			guest.printGuestInfo();
		}
		}else {
			 System.out.println("Guest does not exist");
		}
	}
	public void setIdentityType(IdentityType strToIdentityType) {
		guest.setIdentityType(strToIdentityType);
	}

	public void setGuestName(String name) {
		if (checkGuestByName(name))
			guest = getGuestsByName(name);
		else
			guest.setGuestName(name);
	}

	
	private Guest getGuestsByName(String name) {
		Guest g = null;
		for (Guest guest : guests) {
			if (guest.getGuestName().equalsIgnoreCase(name)) {
				g=guest;
			}
		}
		return g;
	}

	public void setGuestIC(String ic) {
		if(checkGuestExist(ic)) {
			guest = getGuestByIC(ic);
		}else {
		guest.setIC(ic);
		}
	}
	public void setGender(String nextLine) {
		guest.setGender(nextLine);
	}

	public void setContact(String nextLine) {
		guest.setContact(nextLine);
	}

	public void setCountry(String nextLine) {
		guest.setCountry(nextLine);
	}

	public void setNationality(String nextLine) {
		guest.setNationality(nextLine);
	}

	public void setAddress(String nextLine) {
		guest.setAddress(nextLine);
	}

	public void setCreditCard(String nextLine) {
		guest.setCreditCard(nextLine);
		}
}