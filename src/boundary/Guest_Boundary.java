package boundary;

import entity.Guest;
import entity.Guest.IdentityType;
import controller.GuestMrg;

import java.io.FileNotFoundException;
import java.util.HashMap;

public class Guest_Boundary extends Boundary {
    private GuestMrg guestMrg = GuestMrg.getInstance();

    public void displayMain() {
        String choice;
        do {
            System.out.println("Guest System\n" +
                    "1. Create Guest\n" +
                    "2. Update Guest\n" +
                    "3. Find Guest\n" +
                    "0. Return to Main Menu");

            choice = readInputString("Please Enter Your Choice:");

            switch (choice) {
                case "1":
                    createGuestMenu();
                    break;
                case "2":
                    updateGuestMenu();
                    break;
                case "3":
                    findGuestByNameMenu();
                    break;
                default:
                    break;
            }
        } while (!choice.equalsIgnoreCase("0"));
    }

    public void createGuestMenu() {
        Character confirm;
        guestMrg.createNewGuest();
        //get user input
        enterIdentityType();
        enterIC();
        enterName();
        enterGender();
        enterContact();
        enterCountry();
        enterNationality();
        enterAddress();
        enterCreditCard();

        do {
            guestMrg.printGuestInfo();
            confirm = readInputString("Press Y to confirm," + "N to discard and " +
                    "(No.) to edit a field and (No.) to edit a field.").toUpperCase().charAt(0);
            switch (confirm) {
                case 'Y':
                    guestMrg.createGuest();
                    break;
                case '1':
                    enterIC();
                    break;
                case '2':
                    enterIdentityType();
                    break;
                case '3':
                    enterName();
                    break;
                case '4':
                    enterGender();
                    break;
                case '5':
                    enterContact();
                    break;
                case '6':
                    enterCountry();
                    break;
                case '7':
                    enterNationality();
                    break;
                case '8':
                    enterAddress();
                    break;
                case '9':
                    enterCreditCard();
                    break;
                default:
                    break;
            }
        } while (!(confirm.equals('Y') || confirm.equals('N')));
    }


    private void findGuestByNameMenu() {
        String guestName = readInputString("Enter guest name to find :");
        if (guestMrg.checkGuestExist(guestName)) {
            guestMrg.printGuestInfo();
        } else {
            System.out.println("Guest does not exist");
        }
    }

    private void updateGuestMenu() {
        String guestName = readInputString("Enter guest name : ");
        guestMrg.getGuestsByName(guestName);

        if (GuestMrg.checkGuestExist(guestName)) {
            Character confirm;
            guestMrg.setGuestName(guestName);
            do {
                guestMrg.printGuestInfo();

                confirm = readInputString("Press Y to confirm," + "N to discard and "
                        + "(No.) to edit a field.").toUpperCase().charAt(0);

                switch (confirm) {
                    case 'Y':
                        guestMrg.createGuest();
                        break;
                    case '1':
                        enterIC();
                        break;
                    case '2':
                        enterIdentityType();
                        break;
                    case '3':
                        enterName();
                        break;
                    case '4':
                        enterGender();
                        break;
                    case '5':
                        enterContact();
                        break;
                    case '6':
                        enterCountry();
                        break;
                    case '7':
                        enterNationality();
                        break;
                    case '8':
                        enterAddress();
                        break;
                    case '9':
                        enterCreditCard();
                        break;
                    default:
                        break;
                }
            } while (!(confirm.equals('Y') || confirm.equals('N')));

        } else {
            System.out.println("There is no guest found by this name");
        }
    }

    private void enterIdentityType() {
        HashMap<String, String> enumData = getEnumTypeHashMap(IdentityType.class);
        System.out.println();
        String identityType = readInputEnum("Enter identity type:", enumData);
        guestMrg.setIdentityType(guestMrg.strToIdentityType(identityType));
    }

    private void enterIC() {
        String ic = readInputString("Enter IC: ");
        guestMrg.setGuestIC(ic);
    }

    private void enterName() {
        String name = readInputString("Enter name : ");
        guestMrg.setGuestName(name);
    }

    private void enterAddress() {
        String address = readInputString("Enter address : ");
        guestMrg.setAddress(address);

    }

    private void enterCountry() {
        String country = readInputString("Enter country : ");
        guestMrg.setCountry(country);

    }

    private void enterNationality() {
        String nationality = readInputString("Enter nationality : ");
        guestMrg.setNationality(nationality);
    }

    private void enterGender() {
        do {
            String gender = readInputString("Enter gender (M/F): ");
            if ((!gender.equalsIgnoreCase("M")) && (!gender.equalsIgnoreCase("F"))) {
                System.out.println("Invalid Input! The gender should be M/F.");
            } else {
                guestMrg.setGender(gender);
                break;
            }
        } while (true);
    }

    private void enterContact() {
        do {
            Integer contact = readInputInt("Enter contact : ");
            if (contact.toString().length() != 8)
                System.out.println("Invalid Input! The contact should have 8 numbers.");
            else {
                guestMrg.setContact(contact.toString());
                break;
            }
        } while (true);
    }

    private void enterCreditCard() {
        do {
            String creditCard = readInputString("Enter credit card : ");
            if (creditCard.length() != 16)
                System.out.println("Invalid Input! The credit card should have 16 characters.");
            else {
                guestMrg.setCreditCard(creditCard.toString());
                break;
            }
        } while (true);
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