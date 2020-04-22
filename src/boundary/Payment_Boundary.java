package boundary;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import controller.GuestMrg;
import controller.OrderMrg;
import controller.PaymentMrg;
import controller.PromotionMrg;
import controller.ReservationMrg;
import controller.RoomMrg;
import entity.Payment.PaymentMethod;

public class Payment_Boundary extends Boundary {

	private PaymentMrg paymentMrg = PaymentMrg.getInstance();
	private PromotionMrg promotionMrg = PromotionMrg.getInstance();
	final static double TAX = 0.17;

	public static Payment_Boundary getInstance() {
		return new Payment_Boundary();
	}

	@Override
	public void displayMain() {
		// TODO Auto-generated method stub
		String choice;
		do {
			System.out.println("Payment System\n" + "0. Return to Main Menu\n" + "1. Create Promotion\n"
					+ "2. Update Promotion\n" + "3. Delete Promotion\n" + "4. View Promotions\n" + "5.Check Out");
			choice = readInputString("Enter choice : ");

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
			case "5":
				displayCheckOut();
			default:
				break;
			}
		} while (!choice.equalsIgnoreCase("0"));
	}

	public void displayCheckOut() {

		String promoCode;
		double discount = 0;
		LocalDateTime checkOutDate = LocalDateTime.now();

		String roomNum = readInputString("Enter room number");
		boolean success = ReservationMrg.getInstance().setCheckOutReservationByRoomNum(roomNum);

		if (success) {
			do {
				promoCode = readInputString("Enter Promtion Code (Enter 0 for no promotion): ");
				if (PromotionMrg.checkValidPromotionExist(promoCode)) {
					promotionMrg.setPromotionCode(promoCode);
					discount = promotionMrg.getDiscount();
					break;
				} else if (promoCode.equalsIgnoreCase("0")) {
					promoCode = "No promotion";
					discount = 0;
					break;
				} else {
					System.out.println("The promotion does not exist");
				}
			} while (true);

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
			System.out.println("Date Check In: " + formatter.format(ReservationMrg.getInstance().getCheckIn()));
			System.out.println("Date Check Out:" + formatter.format(checkOutDate));

			double roomCharge = RoomMrg.getInstance().getRoomCharge(ReservationMrg.getInstance().getCheckIn(),
					checkOutDate);

			RoomMrg.getInstance().printRoomInfo();
			System.out.println("Total Room Charge: $" + String.format("%.2f", roomCharge));

			double totalRoomServiceCharge = OrderMrg.calculateRoomServiceCharge(roomNum);
			OrderMrg.getInstance().displayAllOrders(roomNum);
			System.out.println("Room Service Charge: $" + String.format("%.2f", totalRoomServiceCharge));

			System.out.println("Discount: " + discount + " % (" + promoCode + ")");
			System.out.println("Tax: " + TAX + "%");

			double totalPay = (roomCharge + totalRoomServiceCharge) * (1 - discount) * (1 + TAX);
			System.out.println("Total Price: $" + String.format("%.2f", totalPay));

			String choice;
			PaymentMethod paymentMethod = PaymentMethod.CASH;
			String creditCard = GuestMrg.getInstance().getCreditCard();
			do {
				choice = readInputString("Select Payment Mode\n" + "1. Credit/Debit Card\n" + "2. Cash");
				switch (choice) {
				case "1":
					paymentMethod = PaymentMethod.CASH;
					GuestMrg.getInstance().setGuestIC(ReservationMrg.getInstance().getGuestIC());
					if (creditCard == null) {
						char confirm;
						do {
							System.out.println("Use existing card details for this payment(Y/N)");
							confirm = sc.nextLine().toUpperCase().charAt(0);
							if (confirm == 'N') {
								creditCard = readInputString("Enter new card details for this payment:");
							}
						} while (confirm != 'Y' || confirm != 'N');
					} else {
						creditCard = readInputString("Enter new card details for this payment:");
					}
					break;
				case "2":
					paymentMethod = PaymentMethod.CASH;
					break;
				}
			} while (!(choice.equalsIgnoreCase("1") || choice.equalsIgnoreCase("2")));

			String reservationCode = ReservationMrg.getInstance().getReservationCode();
			if (paymentMethod.equals(PaymentMethod.CASH)) {
				creditCard = null;
			}
			paymentMrg.createNewPayment(reservationCode, promoCode, roomCharge, totalRoomServiceCharge, TAX, discount,
					totalPay, paymentMethod, creditCard);
			paymentMrg.createPayment();
			ReservationMrg.getInstance().checkOutReservation(checkOutDate);
		} else {
			System.out.println("Unable to check out");
		}

	}

	private void createPromotionMenu() {
		Character confirm;
		promotionMrg.createNewPromotion();

		enterPromotionCode();
		enterPromotionDescription();
		enterDiscount();
		enterPromoStartDate();
		enterPromoEndDate();

		do {
			promotionMrg.printPromotionInfo();
			confirm = readInputString("Press Y to confirm," + "N to discard and " + "(No.) to edit a field.")
					.toUpperCase().charAt(0);
			switch (confirm) {
			case 'Y':
				promotionMrg.createPromotion();
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
		String promotionCode = readInputString("Enter promotion code:");
		if (PromotionMrg.checkPromotionExist(promotionCode)) {
			char confirm;
			do {
				promotionMrg.printPromotionInfo();
				confirm = readInputString("Press Y to confirm," + "N to discard and " + "(No.) to edit a field."
						+ "(Unable to edit Promotion Code").toUpperCase().charAt(0);

				switch (confirm) {
				case 'Y':
					promotionMrg.updatePromotion();
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
			} while (!(confirm == 'Y' || confirm == 'N'));
		} else {
			System.out.println("There are no promotion existed by this promotion code");
		}
	}

	private void deletePromotionMenu() {

		String promotionCode = readInputString("Enter promotion code:");
		char confirm;
		if (PromotionMrg.checkPromotionExist(promotionCode)) {
			do {
				promotionMrg.printPromotionInfo();
				confirm = readInputString("Press 'Y' to delete Promotion and 'N' to Return").toUpperCase().charAt(0);
				if (confirm == 'Y') {
					promotionMrg.deletePromotion();
				}
			} while (!(confirm == 'Y' || confirm == 'N'));
		} else {
			System.out.println("Promotion does not exist");
		}
	}

	private void viewAllPromotionMenu() {
		promotionMrg.printAllPromotionInfo();
	}

	private void enterPromotionCode() {
		do {
			String promotionCode = readInputString("Enter promotion code: ");
			if (!PromotionMrg.checkPromotionExist(promotionCode)) {
				promotionMrg.setPromotionCode(promotionCode);
			} else {
				System.out.println("Promotion already exist");
			}

		} while (true);
	}

	private void enterPromotionDescription() {
		String promoDescription = readInputString("Enter promotion description:");
		promotionMrg.setPromoDescription(promoDescription);
	}

	private void enterDiscount() {
		double discount = readInputDouble("Enter discount percentage:");
		promotionMrg.setDiscount(discount);
	}

	private void enterPromoStartDate() {
		do {
			LocalDateTime promoStartDate = readInputDate("Enter promotion start date:(DD/MM/YYYY HH:mm)");
			if (promoStartDate.isAfter(LocalDateTime.now())) {
				promotionMrg.setPromoStartDate(promoStartDate);
				break;
			} else {
				System.out.println("Please enter the correct Date");
			}
		} while (true);

	}

	private void enterPromoEndDate() {
		do {
			LocalDateTime promoEndDate = readInputDate("Enter promotion end date:(DD/MM/YYYY HH:mm)");
			if (promoEndDate.isAfter(LocalDateTime.now())) {
				promotionMrg.setPromoEndDate(promoEndDate);
				break;
			} else {
				System.out.println("Please enter the correct Date");
			}
		} while (true);
	}

	public void loadData() {
		// TODO Auto-generated method stub
		try {
			paymentMrg.loadPaymentData();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
