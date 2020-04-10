package boundary;

import java.util.Scanner;

public class guest_Boundary {
    static Scanner sc = new Scanner(System.in);
    static String id;
    static String name;
    static String address;
    static String country;
    static String nationality;
    static Character gender;
    static String contact;
    static String creditCard;


    public static void guestMain(){
        System.out.println(
       "Guest System\n" +
        "0. Return to Main Menu\n" +
        "1. Create Guest\n" + 
        "2. Update Guest\n" +
        "3. Find Guest\n");
    }

    public static void createGuest(){
        Character confirm;
        System.out.println(
        "Create Guest\n" +
        "Type of ID:\n" +
        "1. Passport\n" +
        "2. Driving License\n");

        int selection = sc.nextInt();
        sc.nextLine();
        id = id(selection);
        name = name();
        address = address();
        country=country();
        nationality=nationality();
        gender=gender();
        contact = contact ();
        creditCard=creditCard();

        printGuestInfo();

        System.out.println(
        "Press Y to confirm,"+
        "N to discard and " +
        "(No.) to edit a field and (No.) to edit a field.");
        confirm = sc.nextLine().charAt(0);
        switch(confirm){
            case 'Y': break;
            case 'N': break;
            case '1': id = id(selection); break;
            case '2': name = name(); break;
            case '3': address = address(); break;
            case '4': country = country(); break;
            case '5': nationality = nationality(); break;
            case '6': gender  = gender (); break;
            case '7': contact  = contact (); break;
            case '8': creditCard  = creditCard(); break;
            default:
        }
    }





    public static String id(int selection){
        if(selection == 1){
         System.out.println("Passport:");
        }
        else{
        System.out.println("Driving license:");
        }
        String id = sc.nextLine();
        return id;
    }

    public static String name(){
        System.out.println("Name:");
        String name = sc.nextLine();
        return name;
    }

    public static String address(){
        System.out.println("Address:");
        String address = sc.nextLine();
        return address;
    }

    public static String country(){
        System.out.println("Country:");
        String country = sc.nextLine();
        return country;
    }

    public static String nationality(){
        System.out.println("Nationality:");
        String nationality = sc.nextLine();
        return nationality;
    }

    public static Character gender(){
        System.out.println("Gender:");
        Character gender = sc.nextLine().charAt(0);
        return gender;
    }

    public static String contact(){
        System.out.println("Contact:");
        String contact = sc.nextLine();
        return contact;
    }
    public static String creditCard(){
        System.out.println("Credit Card:");
        String creditCard = sc.nextLine();
        return creditCard;
    }

    public static void printGuestInfo() {
        String guestInfo = "Guest information:\n" +
         "1." + id + "\n" +
         "2." + name + "\n" +
         "3." + address + "\n" +
         "4." + country + "\n" +
         "5." + nationality + "\n" +
         "6." + gender + "\n" +
         "7." + contact + "\n" +
         "8." + creditCard;
        System.out.println(guestInfo);
    }

    public static void main(String[] args) {
        guestMain();
        int selection = sc.nextInt();
        if(selection == 1){
            createGuest();
        }
        
    }

}