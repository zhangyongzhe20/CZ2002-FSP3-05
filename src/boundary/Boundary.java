package boundary;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;


public abstract class Boundary {
	Scanner sc = new Scanner(System.in);
	// public static final String ANSI_RESET = "\u001B[0m";
	// public static final String ANSI_RED = "\u001B[31m";
    public abstract void displayMain();
	public abstract void loadData() throws FileNotFoundException;


    public String readInputString(String message) {
		System.out.println(message);
	//	System.out.print(ANSI_RED);
		String input = sc.nextLine().replace(",", " ").replace("-", "");
		//System.out.print(ANSI_RESET);
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
    	String input;
    	for(String key : enumData.keySet()) {
    		message = message + "\n" + key+". "+ enumData.get(key);
    	}
    	do {
    	 input = readInputString(message);   
    	 if(enumData.get(input) == null) {
    		 System.out.println("Please enter the correct choice");
    	 }
    	}while(enumData.get(input) == null);
    	return (enumData.get(input));
    }
    
	public <T extends Enum<T>> HashMap<String, String> getEnumTypeHashMap(Class<T> enumData) {
		HashMap<String, String> returnValue = new HashMap<String, String>();
		int count = 1;
		for (T value : Arrays.asList(enumData.getEnumConstants())) {
			returnValue.put(String.valueOf(count), String.valueOf(value));
			count++;
		}
		return returnValue;
	}

}