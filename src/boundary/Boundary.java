package boundary;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;



public class Boundary {
    Reservation_Boundary reservationpage;
    Guest_Boundary guestpage;
    private Scanner sc = new Scanner(System.in);

    public void display_Control(){
        String choice = "0";
        Scanner sc = new Scanner(System.in);
        Order_Boundary orderpage = new Order_Boundary();
        Room_Boundary roompage= new Room_Boundary();
        Reservation_Boundary reservationpage = new Reservation_Boundary();
        Guest_Boundary guestpage = new Guest_Boundary();
        Payment_Boundary paymentpage = new Payment_Boundary();
        orderpage.loadData();
        roompage.loadData();
        reservationpage.loadData();
        guestpage.loadData();
        paymentpage.loadData();
            //    roompage.loadData;
    //    reservationpage.loadData;
    //    guestpage.loadData;
    do{
        displayMain();
        choice = sc.nextLine();
		switch(choice) {
			case "1":
				guestpage.displayMain();
			break;
			case "2":
				roompage.displayMain();
	    	 break;
    		 case "3":
    			reservationpage.displayMain();
             break;		
        	 case "4":
             orderpage.displayMain();
			 break;
             case "5":
             break;
             case "6":
             break;
			}
			 }while(!choice.equalsIgnoreCase("7"));
    }


    public void displayMain(){
        System.out.println("Hotel Reservation and Payment System (HRPS)");
		System.out.println("===========================================");
		System.out.println("1. About Guest");
		System.out.println("2. About Room");
		System.out.println("3. About Reservation");
        System.out.println("4. About Room Service");
        System.out.println("5. About Checkin");
        System.out.println("6. About Checkout");
		System.out.println("7. Quit");
        System.out.println("============================================");
    }
    
    public void loadData(){};

    public String readInputString(String message) {
    	System.out.println(message);
    	String input = sc.nextLine();
		return input;
    }
    public int readInputInt(String message) {
    	int input = -1;
    	while (input < 0) {
    		try {
    			input = Integer.parseInt(readInputString(message));
    		} catch (NumberFormatException e) {
				System.out.println("Please enter in digits");
			}	
    	}
    	return input;
    }
    public Double readInputDouble(String message) {
    	double input = -1;
    	while (input < 0) {
    		try {
    			input = Double.parseDouble(readInputString(message));
    		} catch (NumberFormatException e) {
				System.out.println("Please enter in digits");
			}	
    	}
    	return input;
    }
    public boolean readInputBoolean(String message) {
    	boolean bool =  false;
    	char confirm;
    	do {
    	 confirm = readInputString(message).toUpperCase().charAt(0);
    	 if(confirm == 'Y') {
    		 bool = true;
    	 }else if(confirm == 'N') {
    		 bool = false;
    	 }
    	}while(!(confirm == 'Y' || confirm =='N'));
    	return bool;
    }
    public LocalDateTime readInputDate(String message) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		LocalDateTime dateTime = null;
		do {
		try {
		String strDateTime = readInputString(message);
		dateTime = LocalDateTime.parse(strDateTime,formatter);
		break;
		}catch(DateTimeParseException e) {
			System.out.println("Please enter the correct date format");
		}
		}while(true);
		return dateTime;
	}
    public String readInputEnum(String message , HashMap<String, String> enumData) {
    	message = message+"\n";
    	String input;
    	for(String key : enumData.keySet()) {
    		message = message + key+". "+ enumData.get(key)+"\n";
    	}
    	do {
    	 input = readInputString(message);   
    	 if(enumData.get(input) == null) {
    		 System.out.println("Please enter the correct choice");
    	 }
    	}while(enumData.get(input) == null);
    	return (enumData.get(input));
    }

}