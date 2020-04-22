package boundary;


import entity.Guest.IdentityType;
import controller.GuestMrg;
import java.io.FileNotFoundException;
import java.util.HashMap;


public class Guest_Boundary extends Boundary {
    private GuestMrg guestMrg = GuestMrg.getInstance();

    public void displayMain(){
        String choice;
        do {
            choice = readInputString( "Guest System\n" +
                    "1. Create Guest\n" +
                    "2. Update Guest\n" +
                    "3. Find Guest\n" +
                    "0. Return to Main Menu\n" +
                    "Enter Choice");

            switch (choice) {
                case "1":
                    createGuestMenu(null);
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
                    guestMrg.setIdentityType(guestMrg.strToIdentityType(identityType));
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


    public void createGuestMenu(String ic) {
        char confirm;
        guestMrg.createNewGuest();
        if(ic == null) {
        enterIC();
        }else {
        	guestMrg.setGuestIC(ic);
        }
        System.out.println(GuestMrg.checkGuestExist(ic));
        if (!GuestMrg.checkGuestExist(ic)) {
        	enterIdentityType();
            enterName();
            enterGender();
            enterContact();
            enterCountry();
            enterNationality();
            enterAddress();
            enterCreditCard();

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
        } 
    }

    private void enterIC() {
    	String ic = readInputString("Enter ic: ");
        guestMrg.setGuestIC(ic);
    }

    private void enterIdentityType() {
    	HashMap<String,String> enumData = getEnumTypeHashMap(IdentityType.class);
        String identityType = readInputEnum("Enter identity type: ",enumData);
        guestMrg.setIdentityType(guestMrg.strToIdentityType(identityType));
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
        String nationality =  readInputString("Enter nationality : ");
        guestMrg.setNationality(nationality);
    }

    private void enterGender() {
        String gender = readInputString("Enter gender : ");
        guestMrg.setGender(gender);
    }

    private void enterContact() {
        String contact = readInputString("Enter contact : ");
        guestMrg.setContact(contact);

    }

    private void enterCreditCard() {	
    	String creditCard = readInputString("Enter credit card : ");
        guestMrg.setCreditCard(creditCard);
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