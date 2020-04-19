package boundary;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import controller.PromotionMrg;
import entity.Promotion;
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
		enterPromoFrom();
		enterPromoTo();
		
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
				enterPromoFrom();
				break;
			case '5':
				enterPromoTo();
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
					boolean success = promotionUpdat.updateRoom(room);
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
					enterPromoFrom();
					break;
				case '5':
					enterPromoTo();
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
	}
	private void viewAllPromotionMenu() {
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
			String promo_desc = sc.nextLine();
			promotion.setPromo_desc(promo_desc);
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
	private void enterPromoFrom() {
		do {
			try {
				System.out.println("Enter Promotion Start Date : (DD/MM/YYYY HH:mm)");
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
				String str_promo_from = sc.nextLine();
				LocalDateTime promo_from = LocalDateTime.parse(str_promo_from, formatter);

				if (promo_from.isAfter(LocalDateTime.now())) {
					promotion.setPromo_from(promo_from);
					break;
				} else {
					System.out.println("Please enter the correct Date");
				}
			} catch (DateTimeParseException e) {
				System.out.println("Please enter the correct Format");
			}
		} while (true);
	}

	private void enterPromoTo() {
		do {
			try {
				System.out.println("Enter Promotion End Date : (DD/MM/YYYY HH:mm)");
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
				String str_promo_to = sc.nextLine();
				LocalDateTime promo_to = LocalDateTime.parse(str_promo_to, formatter);

				if (promo_to.isAfter(promotion.getPromo_from())) {
					promotion.setPromo_from(promo_to);
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
