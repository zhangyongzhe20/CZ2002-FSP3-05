package boundary;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;
import controller.PromotionMrg;
import entity.Promotion;
import entity.Reservation;
import entity.Room;

public class Promotion_Boundary {
	private Scanner sc = new Scanner(System.in);
	private PromotionMrg promotionMrg = PromotionMrg.getInstance();
	private Promotion promotion = PromotionMrg.createNewPromotion();

	public void promotionMain() {
		String choice;
		do {
			System.out.println("Promotion System\n" + "0. Return to Main Menu\n" + "1. Create Promotion\n"
					+ "2. Update Promotion\n" + "3. Remove Promotion\n" + "4. View All Promotion\n");
			choice = sc.nextLine();
		} while (!choice.equalsIgnoreCase("0"));
		switch (choice) {
		case "0":
			break;
		case "1":
			createPromotionMenu();
			break;
		case "2":
			updatePromotionMenu();
			break;
		case "3":
			deletePromotionMenu();
			break;
		case "4":
			viewAllPromotionMenu();
			break;
		default:
			break;

		}		
	}
	private void createPromotionMenu() {
		Character confirm;
		
		enterPromotionCode();
		enterPromotionDescription();
		enterDiscount();
		enterPromoStartDate();
		enterPromoEndDate();
		
		do {
			promotion.printPromotionInfo();
			System.out.println("Press Y to confirm," + "N to discard and " + "(No.) to edit a field.");
			confirm = sc.nextLine().toUpperCase().charAt(0);
			switch (confirm) {
			case 'Y':
				promotionMrg.createPromotion(promotion);
				break;
			case 'N':
				break;
			case '1':
				enterPromotionCode();
				break;
			case '2':
				enterPromotionDescription();
				break;
			case '3':
				enterDiscount();
				break;
			case '4':
				enterPromoStartDate();
				break;
			case '5':
				enterPromoEndDate();
				break;
			default:
				break;
			}
		} while (!(confirm.equals('Y') || confirm.equals('N')));
		
	}
	private void updatePromotionMenu() {
		System.out.println("Enter Promotion Code : ");
		String promotionCode = sc.nextLine();
		promotion = promotionMrg.getPromotionByPromotionCode(promotionCode);
		if(promotion !=null) {
			Character confirm;
			do {
				promotion.printPromotionInfo();
				System.out.println("Press Y to confirm," + "N to discard and " + "(No.) to edit a field."
						+ "(Unable to edit Promotion Code");
				confirm = sc.nextLine().toUpperCase().charAt(0);
				switch (confirm) {
				case 'Y':
					boolean success = promotionMrg.updatePromotion(promotion);
					if (success) {
						System.out.println("Sucessfully update room");
					} else {
						System.out.println("Unable to update room");
					}
					break;
				case 'N':
					break;
				case '2':
					enterPromotionDescription();
					break;
				case '3':
					enterDiscount();
					break;
				case '4':
					enterPromoStartDate();
					break;
				case '5':
					enterPromoEndDate();
					break;
				default:
					break;
				}
			} while (!(confirm.equals('Y') || confirm.equals('N')));
		}else {
			System.out.println("There are no promotion existed by this promotion code");
		}
	}
	private void deletePromotionMenu() {

		System.out.println("Enter Promotion Code:");
		String PromotionCode = sc.nextLine();
		promotion = promotionMrg.getPromotionByPromotionCode(PromotionCode);
		if (promotion != null) {
			promotion.printPromotionInfo();
			 char confirm;
			 do {
				 System.out.println("Press 'Y' to delete Promotion and 'N' to Return");
				  confirm = sc.nextLine().toUpperCase().charAt(0);
				if(confirm == 'Y') {
					promotionMrg.deletePromotion(promotion);
				}
			 }while(!(confirm == 'Y' || confirm == 'N'));
		} else {
			System.out.println("Promotion does not exist");
		}
	}
	private void viewAllPromotionMenu() {
		List<Promotion> promotionList = promotionMrg.getAllPromotion();
		for(Promotion p : promotionList) {
			p.printPromotionInfo();
		}
	}
	
	private void enterPromotionCode() {
		do {
			System.out.println("Enter Promotion Code: ");
			String promotionCode = sc.nextLine();
				Promotion p = promotionMrg.getPromotionByPromotionCode(promotionCode);
				if (p == null) {
					promotion.setPromotionCode(promotionCode);
					break;
				} else {
					System.out.println("Promotion already exist");
				}
		} while (true);
	}
	private void enterPromotionDescription() {	
			System.out.println("Enter Promotion Description: ");
			String promoDescription = sc.nextLine();
			promotion.setPromoDescription(promoDescription);
	}
	private void enterDiscount() {
		do {
			System.out.println("Enter Discount Percentage: ");
			if (sc.hasNextDouble()) {
				promotion.setDiscount(sc.nextDouble());
				sc.nextLine();
				break;
			} else {
				System.out.println("Please enter in digits!");
				sc.nextLine();
			}
		} while (true);
	}
	private void enterPromoStartDate() {
		do {
			try {
				System.out.println("Enter Promotion Start Date : (DD/MM/YYYY HH:mm)");
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
				String strPromoStartDate = sc.nextLine();
				LocalDateTime promoStartDate = LocalDateTime.parse(strPromoStartDate, formatter);

				if (promoStartDate.isAfter(LocalDateTime.now())) {
					promotion.setPromoStartDate(promoStartDate);
					break;
				} else {
					System.out.println("Please enter the correct Date");
				}
			} catch (DateTimeParseException e) {
				System.out.println("Please enter the correct Format");
			}
		} while (true);
	}

	private void enterPromoEndDate() {
		do {
			try {
				System.out.println("Enter Promotion End Date : (DD/MM/YYYY HH:mm)");
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
				String strPromoEndDate = sc.nextLine();
				LocalDateTime promoEndDate = LocalDateTime.parse(strPromoEndDate, formatter);

				if (promoEndDate.isAfter(promotion.getPromoStartDate())) {
					promotion.setPromoEndDate(promoEndDate);
					break;
				} else {
					System.out.println("Please enter the correct Date");
				}
			} catch (DateTimeParseException e) {
				System.out.println("Please enter the correct Format");
			}
		} while (true);
	}
	
}
