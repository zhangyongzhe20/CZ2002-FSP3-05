package boundary;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Scanner;

public class Boundary {
    public void displayMain(){
        System.out.println("Hotel Reservation and Payment System (HRPS)");
		System.out.println("===========================================");
		System.out.println("1. About Guest");
		System.out.println("2. About Room");
		System.out.println("3. About Reservation");
		System.out.println("4. About Room Service");
		System.out.println("5. About Payment");
		System.out.println("6. Quit");
        System.out.println("============================================");
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
			}
			 }while(choice!=6);
	
    }
    
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