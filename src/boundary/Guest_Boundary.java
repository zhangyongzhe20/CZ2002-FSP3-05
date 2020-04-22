package boundary;

import entity.Guest;
import entity.Room;
import controller.GuestMrg;
import controller.RoomMrg;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Guest_Boundary extends Boundary {
    private Scanner sc = new Scanner(System.in);

    private GuestMrg guestMrg = GuestMrg.getInstance();

    private Guest guest;


    public void displayMain(){
        String choice;
        do {
            System.out.println(
                    "Guest System\n" +
                            "1. Create Guest\n" +
                            "2. Update Guest\n" +
                            "3. Find Guest\n" +
                            "0. Return to Main Menu\n" +
                            "Enter Choice");

            choice = sc.nextLine();

            switch (choice) {
                case "1":
                    createGuestMenu();
                    break;
                case "2":
                    updateGuestMenu();
                case "3":
                    findGuestMenu();
                default:
                    break;
            }
        } while (!choice.equalsIgnoreCase("0"));
    }

    private void findGuestMenu(){
        String guestNameToFind = readInputString("Enter guest name to find :");
        if (guestMrg.checkGuestExist(guestNameToFind)) {
            guestMrg.printGuestInfoByName(guestNameToFind);
        } else {
            System.out.println("Guest does not exist");
        }
    }

    private void updateGuestMenu() {
        String guestName = readInputString("Enter guest name : ");
        guestMrg.getGuestByName(guestName);

        if (guestMrg.checkGuestExist(guestName)) {
            Character confirm;
            guestMrg.printGuestInfoByName(guestName);
            System.out.println("Enter No. to edit a field.");

            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("IC:");
                    String ic = sc.nextLine();
                    guestMrg.setGuestIC(ic);
                    break;
                case 2:
                    System.out.println("Identity Type:");
                    String identityType = sc.nextLine();
                    guestMrg.setIdentityType(GuestMrg.strToIdentityType(identityType));
                    break;
                case 3:
                    System.out.println("Name:");
                    guestMrg.setGuestName(sc.nextLine());
                    break;
                case 4:
                    System.out.println("Gender:");
                    guestMrg.setGender(sc.nextLine());
                    break;
                case 5:
                    System.out.println("Contact:");
                    guestMrg.setContact(sc.nextLine());
                    break;
                case 6:
                    System.out.println("Country:");
                    guestMrg.setCountry(sc.nextLine());
                    break;

                case 7:
                    enterNationality();
                    guestMrg.setNationality(sc.nextLine());
                    break;
                case 8:
                    System.out.println("Address:");
                    guestMrg.setAddress(sc.nextLine());
                    break;
                case 9:
                    System.out.println("CreditCard:");
                    guestMrg.setCreditCard(sc.nextLine());
                    break;
                default:
                    break;
                }

                do {
                    guestMrg.printGuestInfoByName(guestName);
                    confirm = readInputString("Press Y to confirm," + "N to discard").toUpperCase().charAt(0);
                    switch (confirm) {
                        case 'Y':
                            guestMrg.updateGuest();
                            break;
                        default:
                            break;
                    }
                } while (!(confirm.equals('Y') || confirm.equals('N')));
        } else {
            System.out.println("There is no guest found by this name");
        }
    }


    private void createGuestMenu() {
        char confirm;
        guest = new Guest();
        enterIC();
        if (guestMrg.getGuestByIC(guest.getIC()) == null) {
            enterName();
            enterGender();
            enterContact();
            enterAddress();
            enterCountry();
            enterNationality();
            enterCreditCard();

            guest.printGuestInfo();

            System.out.println(
                    "Press Y to confirm," + "N to discard and " + "(No.) to edit a field and (No.) to edit a field.");
            confirm = sc.nextLine().toUpperCase().charAt(0);
            switch (confirm) {
                case 'Y':
                    guestMrg.createGuest(guest);
                    break;
                case '1':
                    enterIC();
                case '2':
                    enterName();
                    break;
                case '3':
                    enterGender();
                    break;
                case '4':
                    enterContact();
                    break;
                case '5':
                    enterAddress();
                    break;
                case '6':
                    enterNationality();
                    break;
                case '7':
                    enterGender();
                    break;
                case '8':
                    enterContact();
                    break;
                case '9':
                    enterCreditCard();
                    break;
                default:
                    break;
            }
        } else {
            System.out.println("Guest already exist");
        }
    }

    private void enterIC() {
        String choice;
        do {
            System.out.println("Create Guest\n" +
                    "Type of IC(identity confirmation):\n" +
                    "1. Passport\n" +
                    "2. Driving License\n");
            choice = sc.nextLine();
            if (choice.equalsIgnoreCase("1")) {
                guest.setIdentityType(GuestMrg.strToIdentityType("PASSPORT"));
            } else if (choice.equalsIgnoreCase("2")) {
                guest.setIdentityType(GuestMrg.strToIdentityType("DRIVING LICENSE"));
            }
        } while (!(choice.equalsIgnoreCase("1") || choice.equalsIgnoreCase("2")));

        System.out.println("IC(Passport/Driving License):");
        String ic = sc.nextLine();
        guest.setIC(ic);
    }

    private void enterIdentityType() {
        System.out.println("Identity Type:");
        String identityType = sc.nextLine();
        guest.setIdentityType(GuestMrg.strToIdentityType(identityType));
    }

    private void enterName() {
        System.out.println("Name:");
        String name = sc.nextLine();
        guest.setGuestName(name);

    }

    private void enterAddress() {
        System.out.println("Address:");
        String address = sc.nextLine();
        guest.setAddress(address);

    }

    private void enterCountry() {
        System.out.println("Country:");
        String country = sc.nextLine();
        guest.setCountry(country);

    }

    private void enterNationality() {
        System.out.println("Nationality:");
        String nationality = sc.nextLine();
        guest.setNationality(nationality);
    }

    private void enterGender() {
        System.out.println("Gender:");
        String gender = sc.nextLine();
        guest.setGender(gender);
    }

    private void enterContact() {
        System.out.println("Contact:");
        String contact = sc.nextLine();
        guest.setContact(contact);

    }

    private void enterCreditCard() {
        System.out.println("Credit Card:");
        String creditCard = sc.nextLine();
        guest.setCreditCard(creditCard);

    }

    public void loadData() {
        // TODO Auto-generated method stub
        try {
            guestMrg.loadGuestData();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}