package boundary;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import controller.GuestMrg;
import controller.ReservationMrg;
import controller.RoomMrg;
import entity.Reservation.CheckInType;
import entity.Reservation.ReservationStatus;
import entity.Room.BedType;
import entity.Room.RoomType;

public class Reservation_Boundary extends Boundary {
	private ReservationMrg reservationMrg = ReservationMrg.getInstance();
	private int days = 0;

	public void displayMain() {
		String choice;
		do {
			System.out.println("-------------------------------------------");
			System.out.println("Reservation System\n" + "0. Return to Main Menu\n" + "1. Create Reservation\n"
					+ "2. Update Reservation\n" + "3. Delete Reservation\n" + "4. Search Reservation\n"
					+ "5. Check In");
			System.out.println("-------------------------------------------");
			choice = readInputString("Enter choice : ");

			switch (choice) {
			case "0":
				break;
			case "1":
				createReservationMenu(CheckInType.RESERVATION);
				break;
			case "2":
				updateReservationMenu();
				break;
			case "3":
				deleteReservationMenu();
				break;
			case "4":
				viewReservationMenu();
				break;
			case "5":
				checkInMenu();
			default:
				break;
			}
		} while (!choice.equalsIgnoreCase("0"));

	}

	private void createReservationMenu(CheckInType checkInType) {

		Character confirm;
		reservationMrg.createNewReservation();

		// boolean bool = true;
		String ic = readInputString("Enter guest IC : ").toUpperCase();
		if (!GuestMrg.checkGuestByIC(ic)) {
			System.out.println("New guest, please type in guest information first.");
			// char input;
			// do {
			// input = readInputString("Press Y to create new guest or N to
			// return").toUpperCase()
			// .charAt(0);
			// if(input == 'Y') {
			Guest_Boundary gb = new Guest_Boundary();
			gb.createGuestMenu(ic);
			// bool = true;
		}
		// else if (input == 'N') {
		// bool = false;
		// }
		// }while(!(input == 'Y' ||input == 'N'));
		// }
		// if(bool) {
		if (!reservationMrg.checkReservationExistByGuestIC(ic)) {
			reservationMrg.setGuestIC(ic);
			reservationMrg.setCheckInType(checkInType);
			reservationMrg.setReservationCode(reservationMrg.generateReservationCode(ic));
			if (checkInType.equals(CheckInType.RESERVATION)) {
				enterCheckInDate();
			} else {
				reservationMrg.setCheckIn(LocalDateTime.now());
			}
			enterCheckOutDate();
			enterNumOfAdult();
			enterNumOfChild();
			enterRoomNum();
			do {
				if (reservationMrg.getRoomNum() == null) {
					reservationMrg.setReservationStatus(ReservationStatus.WAITLIST);
				} else {
					reservationMrg.setReservationStatus(ReservationStatus.CONFIRMED);
				}

				reservationMrg.printReservationInfo();
				confirm = readInputString("Press Y to confirm," + "N to discard and " + "(No.) to edit a field.")
						.toUpperCase().charAt(0);
				switch (confirm) {
				case 'Y':
					reservationMrg.createReservation();
					break;
				case 'N':
					break;
				case '1':
					if (checkInType.equals(CheckInType.RESERVATION)) {
						enterCheckInDate();
					}
					break;
				case '2':
					enterCheckOutDate();
					break;
				case '3':
					enterNumOfAdult();
					break;
				case '4':
					enterNumOfChild();
					break;
				case '5':
					enterRoomNum();
				default:
					break;
				}
			} while (!(confirm.equals('Y') || confirm.equals('N')));
		} else {
			System.out.println("The guest has already book a reservation");
		}
		// }
	}

	private void updateReservationMenu() {
		String i;
		do {
			System.out.println("Reservation System\n" + "0. Return to Main Menu\n"
					+ "1. Update Reservation By Details\n" + "2. Update Reservation WaitList");

			i = readInputString("Enter choice :");
			switch (i) {
			case "0":
				break;
			case "1":
				updateReservationDetails();
				
				break;
			case "2":
				updateReservationWaitList();
				break;
			}
		} while (!i.equalsIgnoreCase("0"));
	}

	private void updateReservationDetails() {
		String reservationCode = readInputString("Enter Reservation Code:");
		if (ReservationMrg.checkReservationExist(reservationCode)) {
			reservationMrg.setReservationCode(reservationCode);
			if (reservationMrg.getCheckInType().equals(CheckInType.RESERVATION)) {
				if (!(reservationMrg.getReservationStatus().equals(ReservationStatus.EXPIRED)
						|| reservationMrg.getReservationStatus().equals(ReservationStatus.CHECKOUT))) {
					char confirm;
					do {
						if (!reservationMrg.getReservationStatus().equals(ReservationStatus.CHECKIN)) {
							if (reservationMrg.getRoomNum() == null) {
								reservationMrg.setReservationStatus(ReservationStatus.WAITLIST);
							} else {
								reservationMrg.setReservationStatus(ReservationStatus.CONFIRMED);
							}

						}

						reservationMrg.printReservationInfo();

						confirm = readInputString("Press Y to confirm," + "N to discard and "
								+ "(No.) to edit a field.(Unable to edit Guest IC and Reservation Code)").toUpperCase()
										.charAt(0);

						switch (confirm) {
						case 'Y':
							reservationMrg.updateReservationDetails();
							System.out.println("Reservation is updated successfully!");
							System.out.println("-------------------------------------------");
							break;
						case 'N':
							break;
						case '1':
							enterCheckInDate();
							break;
						case '2':
							enterCheckOutDate();
							break;
						case '3':
							enterNumOfAdult();
							break;
						case '4':
							enterNumOfChild();
							break;
						case '5':
								enterRoomNum();
						default:
							break;
						}
					} while (!(confirm == 'Y' || confirm == 'N'));
				} else {
					System.out.println("Reservation had already expired");
				}
			} else {
				System.out.println("Reservation does not exist by this Reservation code");
			}
		} else {
			System.out.println("Reservation does not exist by this Reservation code");
		}
	}

	private void updateReservationWaitList() {
		reservationMrg.printReservationsByStatus(ReservationStatus.WAITLIST);
		String reservationCode = readInputString("Enter Reservation Code:");
		Character confirm;
		if (ReservationMrg.checkReservationExist(reservationCode)) {
			reservationMrg.setReservationCode(reservationCode);
			if (reservationMrg.getReservationStatus().equals(ReservationStatus.WAITLIST)
					&& reservationMrg.getCheckInType().equals(CheckInType.RESERVATION)) {
				enterRoomNum();
				do {
					if (reservationMrg.getRoomNum() == null) {
						reservationMrg.setReservationStatus(ReservationStatus.WAITLIST);
					} else {
						reservationMrg.setReservationStatus(ReservationStatus.CONFIRMED);
					}

					reservationMrg.printReservationInfo();
					confirm = readInputString("Press Y to confirm," + "N to discard").toUpperCase().charAt(0);
					switch (confirm) {
					case 'Y':
						reservationMrg.updateReservationDetails();
						System.out.println("Reservation is updated successfully!");
						System.out.println("-------------------------------------------");
						break;
					case 'N':
						break;
					default:
						break;
					}
				} while (!(confirm.equals('Y') || confirm.equals('N')));
			} else {
				System.out.println("The selected reservation is not on waiting list");
			}
		} else {
			System.out.println("Please enter the correct reservation code");
		}

	}

	private void deleteReservationMenu() {
		String reservationCode = readInputString("Enter Reservation Code:");
		if (ReservationMrg.checkReservationExist(reservationCode)) {
			reservationMrg.setReservationCode(reservationCode);
			if(reservationMrg.getCheckInType().equals(CheckInType.RESERVATION)) {
			reservationMrg.printReservationInfo();
			char confirm;
			do {
				confirm = readInputString("Press Y to confirm," + "N to discard").toUpperCase().charAt(0);
				if (confirm == 'Y') {
					reservationMrg.cancelReservation();
				}
			} while (!(confirm == 'Y' || confirm == 'N'));
			System.out.println("Reservation has successfully been deleted");
			}else {
				System.out.println("Reservation Code does not exist");
			}
		} else {
			System.out.println("Reservation Code does not exist");
		}

	}

	private void viewReservationMenu() {
		String choice;
		do {
			System.out.println("Reservation System\n" + "0. Return to Main Menu\n"
					+ "1. Search reservation by reservation code\n" + "2. Display all reservation");
            reservationMrg.checkExpiredReservations();
			choice = readInputString("Enter choice :");
			switch (choice) {
			case "0":
				break;
			case "1":
				searchByReservationCodeMenu();
				break;
			case "2":
				searchAllReservationMenu();
				break;
			}
		} while (!choice.equalsIgnoreCase("0"));
	}

	private void searchByReservationCodeMenu() {
		String reservationCode = readInputString("Enter Reservation Code:");
		if (ReservationMrg.checkReservationExist(reservationCode)) {
			reservationMrg.setReservationCode(reservationCode);
			if (reservationMrg.getCheckInType().equals(CheckInType.RESERVATION)) {
				if (!(reservationMrg.getReservationStatus().equals(ReservationStatus.EXPIRED)
						|| reservationMrg.getReservationStatus().equals(ReservationStatus.CHECKOUT))) {
					reservationMrg.printReservationInfo();

				} else {
					System.out.println("Reservation has already expired");
				}
			} else {
				System.out.println("Reservation does not exist");
			}
		} else {
			System.out.println("Reservation does not exist");
		}

	}

	private void searchAllReservationMenu() {
		reservationMrg.printActiveReservation();
		System.out.println("-------------------------------------------");
	}

	private void checkInMenu() {
		String i;
		do {
			System.out.println("Check In:\n" + "0.Return to Main Menu\n" + "1.Walk In \n" + "2.By Reservation");
			i = readInputString("Enter your choice:");
			switch (i) {
			case "0":
				break;
			case "1":
				createReservationMenu(CheckInType.WALKIN);
				break;
			case "2":
				reservationCheckInMenu();
				break;
			}
		} while (!i.equalsIgnoreCase("0"));
	}

	private void reservationCheckInMenu() {
		String reservationCode = readInputString("Enter Reservation Code:");
		if (ReservationMrg.checkReservationExist(reservationCode)) {
			reservationMrg.setReservationCode(reservationCode);
			if (reservationMrg.getReservationStatus().equals(ReservationStatus.CONFIRMED)
					&& reservationMrg.getCheckInType().equals(CheckInType.RESERVATION)) {

				reservationMrg.setReservationStatus(ReservationStatus.CHECKIN);
				reservationMrg.updateReservationDetails();
				System.out.println("Successfully check in to the room");
			} else {
				System.out.println("Reservation is not found");
			}

		} else {
			System.out.println("Reservation is not found");
		}

	}

	private void enterCheckInDate() {
		do {
			LocalDateTime checkInDate = readInputDate("Enter Check In Date: (DD/MM/YYYY HH:mm)");
			if (checkInDate.isAfter(LocalDateTime.now())) {
				reservationMrg.setCheckIn(checkInDate);
				if (days > 0) {
					LocalDateTime checkOutDate = reservationMrg.getCheckIn().plusDays(days);
					reservationMrg.setCheckOut(checkOutDate);
				}
				break;
			} else {
				System.out.println("Please enter the correct Date");
			}
		} while (true);
	}

	private void enterCheckOutDate() {
		do {
			days = readInputInt("Enter the number of days of staying : ");
			if (days > 0) {
				LocalDateTime checkOutDate = reservationMrg.getCheckIn().plusDays(days);
				reservationMrg.setCheckOut(checkOutDate);
				break;
			} else {
				System.out.println("Please enter the correct days");
			}
		} while (true);
	}

	private void enterNumOfAdult() {
		int numOfAdult = readInputInt("Enter Number of Adult(s): ");
		reservationMrg.setNumOfAdults(numOfAdult);

	}

	private void enterNumOfChild() {
		int numOfChild = readInputInt("Enter Number of Child(ren): ");
		reservationMrg.setNumOfChild(numOfChild);
	}

	private void enterRoomNum() {
		RoomMrg roomMrg = RoomMrg.getInstance();
		RoomType roomType;
		BedType bedType;
		boolean hasWifiBool;
		boolean allowSmokingBool;
		HashMap<String, String> enumData = getEnumTypeHashMap(RoomType.class);
		String strRoomType = readInputEnum("Enter Room Type: ", enumData);
		roomType = roomMrg.strToRoomType(strRoomType);

		enumData = getEnumTypeHashMap(BedType.class);
		String strBedType = readInputEnum("Enter Bed Type: ", enumData);
		bedType = roomMrg.strToBedType(strBedType);

		hasWifiBool = readInputBoolean("Enter Has Wifi (Y/N): ");
		allowSmokingBool = readInputBoolean("Enter Allow Smoking (Y/N): ");

		List<String> roomNumList = new ArrayList<String>();

		roomNumList = roomMrg.getAndPrintAvailRoomNum(roomType, bedType, hasWifiBool, allowSmokingBool);
		String roomNum;

		if (roomNumList.size() > 0) {
			do {
				roomNum = readInputString("Enter the room number to be selected (Enter 0 to exit): ");
				boolean isValid = false;
				for (String roomNo : roomNumList) {
					if (roomNo.equalsIgnoreCase(roomNum)) {
						isValid = true;
					}
				}
				if (isValid) {
					reservationMrg.setRoomNum(roomNum);
					break;
				}
				if (roomNum.equalsIgnoreCase("0")) {
					if (!(reservationMrg.getReservationStatus().equals(ReservationStatus.CHECKIN) ||
							reservationMrg.getReservationStatus().equals(ReservationStatus.CONFIRMED))) {
					reservationMrg.setRoomNum(null);
					}
				}
			} while (!roomNum.equalsIgnoreCase("0"));
		}
	}

	public void loadData() {
		// TODO Auto-generated method stub
		try {
			reservationMrg.loadReservationData();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}