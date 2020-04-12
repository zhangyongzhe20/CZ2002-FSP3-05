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

public class GuestMrg {

		static List<Guest> guests = new ArrayList<Guest>();
		final static String fileName = "guest_data.txt";
	
		 
	public static int guestExist(String data , String type , ArrayList<Guest> guestList) {
	
		return 1;
	}
	
	public static void createGuest(Guest guest) {
	   guests.add(guest);
	}
	
	public static void searchGuest(ArrayList<Guest> guestList) {
	
	}
	public static void updateGuest(ArrayList<Guest> guestList) {
		
	}
	
	public static void loadGuestData() throws FileNotFoundException {
	//Guest(String guestName, String creditCard, String address, String country, String gender, String identityType,String IC, String nationality, String contact, List<Integer> roomNumList) {
		File file = new File(fileName);
		try { 
			file.createNewFile();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		Scanner sc = new Scanner (file);
		String data;
		while(sc.hasNextLine()) {
			data = sc.nextLine();
			String[] temp = data.split(",");
			List<Integer> roomNumList = new ArrayList<Integer>();
			if(temp.length > 10) {
			for(int i = 9 ; i < temp.length;i ++) {
			roomNumList.add(Integer.parseInt(temp[i]));
		}
			}else {
			roomNumList = null;
		}
			Guest guest = new Guest(temp[0],temp[1], temp[2], temp[3], temp[4],temp[5],temp[6],temp[7],temp[8], roomNumList);
			guests.add(guest);
		}
		sc.close();
	}
	
	public static void writeGuestData() throws IOException {
		 FileWriter fileWriter = new FileWriter(fileName);
		PrintWriter fileOut = new PrintWriter(fileWriter);
		if(guests.size() > 0) {
		for(Guest guest : guests) {
			fileOut.print(guest.getGuestName()+ ",");
			fileOut.print(guest.getCreditCard()+ ",");
			fileOut.print(guest.getAddress()+ ",");
			fileOut.print(guest.getCountry()+ ",");
			fileOut.print(guest.getGender()+ ",");
			fileOut.print(guest.getIdentityType()+ ",");
			fileOut.print(guest.getIC()+ ",");
			fileOut.print(guest.getNationality()+ ",");
			fileOut.print(guest.getContact()+ ",");
			if(guest.getRoomNumList()!= null && guest.getRoomNumList().size()>0) {
			for(int i : guest.getRoomNumList()) {
				fileOut.print(i+",");
			}
			}
			fileOut.println();
		}
		System.out.println("finish writing");
		fileOut.close();
		}
	}
	

}

