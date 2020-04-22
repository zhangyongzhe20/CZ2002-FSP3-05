package boundary;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Scanner;

public abstract class Boundary {
    Scanner sc = new Scanner(System.in);
    public abstract void displayMain();
    public abstract void loadData();

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