package boundary;

import entity.Guest.IdentityType;
import controller.GuestMrg;

import java.io.FileNotFoundException;
import java.util.HashMap;

public class Guest_Boundary extends Boundary {
    private GuestMrg guestMrg = GuestMrg.getInstance();

    public void displayMain() {
        String choice;
        do {
            System.out.println("-------------------------------------------");
            System.out.println("Guest System\n" +
                            "0. Return to Main Menu\n"+
                    "1. Create Guest\n" +
                    "2. Update Guest\n" +
                    "3. Search Guest"
                    );
            System.out.println("-------------------------------------------");
            choice = readInputString("Enter choice : ");

            switch (choice) {
                case "1":
                    createGuestMenu(null);
                    break;
                case "2":
                    updateGuestMenu();
                    break;
                case "3":
                findGuestMenu();
                    break;
                default:
                    break;
            }
        } while (!choice.equalsIgnoreCase("0"));
    }

    public void createGuestMenu(String ic) {
        char confirm;
        guestMrg.createNewGuest();
        if(ic == null) {
        enterIC();
        }else {
        	guestMrg.setGuestIC(ic);
        }
       // System.out.println(GuestMrg.checkGuestExist(ic));
        if (!GuestMrg.checkGuestExist(ic)) {
        	enterIdentityType();
            enterName();
            enterGender();
            enterContact();
            enterCountry();
            enterNationality();
            enterAddress();
            enterCreditCard();
         do{
            guestMrg.printGuestInfo();
            confirm = readInputString("Press Y to confirm," + "N to discard and " + 
            "(No.) to edit a field and (No.) to edit a field.").toUpperCase().charAt(0);
            switch (confirm) {
                case 'Y':
                    guestMrg.createGuest();
                    break;
                case '1':
                    enterIdentityType();
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
                    enterCountry();
                    break;
                case '6':
                    enterNationality();
                    break;
                case '7':
                    enterAddress();
                    break;
                case '8':
                	enterCreditCard();
                    break;
                default:
                    break;
            }
        }while(confirm!='Y' && confirm!='N');
        } 
    }

    private void findGuestMenu(){
        String selection = readInputString("Search guest:\n" + "0. Return to previous page\n"+"1. By IC\n" + "2. By Name");
        switch(selection){
            case "0":
            break;
            case "1":findGuestByICMenu(); break;
            case "2":findGuestByNameMenu(); break;
        }
    }
    private void findGuestByNameMenu() {
        String guestName = readInputString("Enter guest name to find :");
        guestMrg.printGuestInfoByGuestName(guestName);
    }
    private void findGuestByICMenu(){
        String guestIC = readInputString("Enter guest IC to find :");
        if (GuestMrg.checkGuestByIC(guestIC)) {
            guestMrg.printGuestInfo();
        } else {
            System.out.println("Guest does not exist");
        }
    }

    private void updateGuestMenu() {
        String ic = readInputString("Enter guest ic : ");
        if (GuestMrg.checkGuestByIC(ic)) {
            Character confirm;
            do {
                guestMrg.printGuestInfo();
                confirm = readInputString("Press Y to confirm," + "N to discard and "
                        + "(No.) to edit a field.").toUpperCase().charAt(0);

                switch (confirm) {
                case 'Y':
                guestMrg.updateGuest();
                    break;
                case '1':
                    enterIdentityType();
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
                    enterCountry();
                    break;
                case '6':
                    enterNationality();
                    break;
                case '7':
                    enterAddress();
                    break;
                case '8':
                	enterCreditCard();
                        break;
                    default:
                        break;
                }
            } while (!(confirm.equals('Y') || confirm.equals('N')));

        } else {
            System.out.println("There is no guest found by this ic");
        }
    }

    private void enterIdentityType() {
        HashMap<String, String> enumData = getEnumTypeHashMap(IdentityType.class);
        System.out.println();
        String identityType = readInputEnum("Enter Identity Type : ", enumData);
        guestMrg.setIdentityType(guestMrg.strToIdentityType(identityType));
    }

    private void enterIC() {
        String ic = readInputString("Enter IC : ");
        guestMrg.setGuestIC(ic);
    }

    private void enterName() {
        String name = readInputString("Enter Name : ");
        guestMrg.setGuestName(name);
    }

    private void enterAddress() {
        String address = readInputString("Enter Address : ");
        guestMrg.setAddress(address);

    }

    private void enterCountry() {
        String country = readInputString("Enter Country : ");
        guestMrg.setCountry(country);

    }

    private void enterNationality() {
        String nationality = readInputString("Enter Nationality : ");
        guestMrg.setNationality(nationality);
    }

    private void enterGender() {
        do {
            String gender = readInputString("Enter Gender (M/F) : ");
            if ((!gender.equalsIgnoreCase("M")) && (!gender.equalsIgnoreCase("F"))) {
                System.out.println("The gender should be M/F.");
            } else {
                guestMrg.setGender(gender);
                break;
            }
        } while (true);
    }

    private void enterContact() {
        do {
            String contact = readInputString("Enter Contact : ");
            if (contact.length() != 8)
                System.out.println("The contact should have 8 numbers.");
            else {
                guestMrg.setContact(contact);
                break;
            }
        } while (true);
    }

    private void enterCreditCard() {
        do {
            String creditCard = readInputString("Enter Credit Card : ");
            if (creditCard.length() != 16)
                System.out.println("The credit card should have 16 characters.");
            else {
                guestMrg.setCreditCard(creditCard);
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