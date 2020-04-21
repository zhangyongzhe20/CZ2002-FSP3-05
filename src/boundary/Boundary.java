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


    public void display_Control(){
        int choice;
        Scanner sc = new Scanner(System.in);
        Order_Boundary orderpage = new Order_Boundary();
        Room_Boundary roompage= new Room_Boundary();
        orderpage.loadData();
        roompage.loadData();
            //    roompage.loadData;
    //    reservationpage.loadData;
    //    guestpage.loadData;
    do{
        displayMain();
        choice = Integer.parseInt(sc.nextLine());
		switch(choice) {
			case 1:
			break;
			case 2:
	    	 break;
    		 case 3:
             break;		
        	 case 4:
             orderpage.displayMain();
			 break;
             case 5:
             break;
             case 6:
			}
			 }while(choice!=7);
		// List<String> data = new ArrayList<String>();
		// HashMap<String , String> enumData = new HashMap<String , String>();
		// enumData.put("1", "WALKIN" );
		// enumData.put("2", "RESERVATION");
		// readInputEnum("1. WALKIN\n 2.RESERVATION\n" , enumData);
		// System.out.println("============================================");
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
    	Scanner sc = new Scanner(System.in);
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
    public LocalDateTime readInputDate(String message ,LocalDateTime compareDate, boolean isAfter) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		LocalDateTime dateTime = null;
		do {
		try {
		String strDateTime = readInputString(message);
		dateTime = LocalDateTime.parse(strDateTime,formatter);
		if(isAfter) {
			if(dateTime.isAfter(compareDate)){
				break;
			}else {
				System.out.println("Please enter the correct date");
			}
		}else {
			if(dateTime.isBefore(compareDate)){
				break;
			}else {
				System.out.println("Please enter the correct date");
			}
		}
		}catch(DateTimeParseException e) {
			System.out.println("Please enter the correct date format");
		}
		}while(true);
		return dateTime;
	}
    public String readInputEnum(String message , HashMap<String, String> enumData) {
    	String input;
    	do {
    	 input = readInputString(message);   
    	 if(enumData.get(input) == null) {
    		 System.out.println("Please enter the correct choice");
    	 }
    	}while(enumData.get(input) == null);
    	return (enumData.get(input));
    }

}