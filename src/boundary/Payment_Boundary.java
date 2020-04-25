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
	private ReservationMrg reservationMrg = ReservationMrg.getInstance();
	private OrderMrg orderMrg = OrderMrg.getInstance();
	private RoomMrg roomMrg = RoomMrg.getInstance();
	final static double TAX = 17;
	private int days = 0;
	

	@Override
	public void displayMain() {
		// TODO Auto-generated method stub
		String choice;
		do {
			System.out.println("-------------------------------------------");
			System.out.println("Payment System\n" + "0. Return to Main Menu\n" + "1. Create Promotion\n"
					+ "2. Update Promotion\n" + "3. Delete Promotion\n" + "4. View Promotions\n" + "5. Check Out");
			System.out.println("-------------------------------------------");
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

	private void displayCheckOut() {

		String promoCode;
		double discount = 0;
		LocalDateTime checkOutDate = LocalDateTime.now();


		String roomNum = readInputString("Enter room number");


		if (reservationMrg.checkCheckInExist(roomNum)) {
			do {
				promoCode = readInputString("Enter Promotion Code (Enter 0 for no promotion): ");
				if (promotionMrg.checkValidPromotionExist(promoCode)) {
					discount = promotionMrg.getDiscount();
					break;
				} else if (promoCode.equalsIgnoreCase("0")) {
					discount = 0;
					promoCode = null;
					break;
				} else {
					System.out.println("The promotion does not exist");
				}
			} while (true);
			String choice;
			String creditCard = null;
			PaymentMethod paymentMethod = PaymentMethod.CASH;
			do {
				choice = readInputString("Select Payment Mode\n" + "1.Credit/Debit Card\n" + "2.Cash");
				switch (choice) {
				case "1":
					creditCard = GuestMrg.getInstance().getCreditCardByGuestIC(reservationMrg.getGuestIC());
					paymentMethod = PaymentMethod.CARD;
					if (creditCard != null) {
						char confirm;
						do {
							confirm = readInputString("Use existing card details for this payment(Y/N)").toUpperCase().charAt(0);
							if (confirm == 'N') {
								creditCard = readInputString("Enter new card details for this payment : ");
							}
						} while (! (confirm == 'Y' || confirm == 'N'));
					} else {
						creditCard = readInputString("Enter new card details for this payment : ");
					}
					break;
				case "2":
					paymentMethod = PaymentMethod.CASH;
					break;
				}
			} while (!(choice.equalsIgnoreCase("1") || choice.equalsIgnoreCase("2")));
			
			
			LocalDateTime checkInDate = reservationMrg.getCheckIn();
     		double roomCharge = roomMrg.getRoomCharge(roomNum,checkInDate, checkOutDate);					
			double totalRoomServiceCharge = orderMrg.getRoomServiceCharge(roomNum);
			double totalPay = (roomCharge + totalRoomServiceCharge) * (1 - discount/(double)100) * (1 + TAX/(double)100);
			
			paymentMrg.createNewPayment(reservationMrg.getReservationCode(), promoCode, roomCharge, totalRoomServiceCharge, TAX, discount,
			totalPay, paymentMethod, creditCard);
			
			printInvoice(roomNum,checkInDate , checkOutDate);
			
			reservationMrg.checkOutReservation(checkOutDate);
			orderMrg.setOrdersToBilled(roomNum);
			System.out.println("Successfully check out of the room");
		} else {
			System.out.println("Please enter the correct room number");
		}

	}
	private void printInvoice(String roomNum , LocalDateTime checkInDate,LocalDateTime checkOutDate) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		System.out.println("-------------------------------------------");
		System.out.println("Date Check In: " + formatter.format(checkInDate));
		System.out.println("Date Check Out:" + formatter.format(checkOutDate));
		orderMrg.displayUnbilledOrders(roomNum);
		paymentMrg.printPaymentInfo();
		
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
		String promotionCode = readInputString("Enter Promotion Code:");
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

		String promotionCode = readInputString("Enter Promotion Code:");
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
			String promotionCode = readInputString("Enter Promotion Code: ");
			if (!PromotionMrg.checkPromotionExist(promotionCode)) {
				promotionMrg.setPromotionCode(promotionCode);
				break;
			} else {
				System.out.println("Promotion already exist");
			}

		} while (true);
	}

	private void enterPromotionDescription() {
		String promoDescription = readInputString("Enter Promotion Description:");
		promotionMrg.setPromoDescription(promoDescription);
	}

	private void enterDiscount() {
		double discount = readInputDouble("Enter Discount Percentage:");
		promotionMrg.setDiscount(discount);
	}

	private void enterPromoStartDate() {
		do {
			LocalDateTime promoStartDate = readInputDate("Enter Promotion Start Date:(DD/MM/YYYY HH:mm)");
			if (promoStartDate.isAfter(LocalDateTime.now())) {
				promotionMrg.setPromoStartDate(promoStartDate);
				if(days > 0) {
					LocalDateTime promoEndDate = promotionMrg.getPromoStartDate().plusDays(days);
					promotionMrg.setPromoEndDate(promoEndDate);
				}
				break;
			} else {
				System.out.println("Please enter the correct Date");
			}
		} while (true);

	}

	private void enterPromoEndDate() {
		do {
			days = readInputInt("Enter the number of days for the promotion : ");
			if(days > 0) {
			LocalDateTime promoEndDate = promotionMrg.getPromoStartDate().plusDays(days);
			promotionMrg.setPromoEndDate(promoEndDate);
			break;
			}else {
				System.out.println("Please enter the correct days");
			}
			}while(true);
		
	}

	public void loadData() {
		// TODO Auto-generated method stub
		try {
			paymentMrg.loadPaymentData();
			promotionMrg.loadPromotionData();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
