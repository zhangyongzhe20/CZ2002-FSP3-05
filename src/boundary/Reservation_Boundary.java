package boundary;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import controller.GuestMrg;
import controller.ReservationMrg;
import controller.RoomMrg;
import entity.Reservation.CheckInType;
import entity.Reservation.ReservationStatus;
import entity.Room.BedType;
import entity.Room.RoomStatus;
import entity.Room.RoomType;

public class Reservation_Boundary extends Boundary {
	private ReservationMrg reservationMrg = ReservationMrg.getInstance();

	public void displayMain() {
		String choice;
		do {
			System.out.println("Reservation System\n" + "0. Return to Main Menu\n" + "1. Create Reservation\n"
					+ "2. Update Reservation\n" + "3. Delete Reservation\n" + "4. Get Reservation Details\n");
			choice = readInputString("Enter choice : ");

			switch (choice) {
			case "0":
				break;
			case "1":
				createReservationMenu();
				break;
			case "2":
				updateReservationMenu();
				break;
			case "3":
				deleteReservationMenu();
				break;
			case "4":
				displayReservationMenu();
				break;
			default:
				break;
			}
		} while (!choice.equalsIgnoreCase("0"));

	}

	private void createReservationMenu() {

		Character confirm;
		reservationMrg.createNewReservation();

		String ic = readInputString("Enter guest IC : ").toUpperCase();
		if (GuestMrg.checkGuestExist(ic)) {
			reservationMrg.setCheckInType(CheckInType.RESERVATION);
			reservationMrg.setGuestIC(ic);
			reservationMrg.setReservationCode(reservationMrg.generateReservationCode(ic));
			enterCheckInDate();
			enterCheckOutDate();
			enterNumOfAdult();
			enterNumOfChild();
			enterRoomNum();
			do {
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
			} while (!(confirm.equals('Y') || confirm.equals('N')));
		}
	}

	private void updateReservationMenu() {
		String i;
		do {
			System.out.println("Reservation System\n" + "0. Return to Main Menu\n"
					+ "1. Update Reservation By Details\n" + "2. Update Reservation WaitList\n");

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
		String reservationCode = readInputString("Enter reservation code:");
		if (ReservationMrg.checkReservationExist(reservationCode)) {
			if (!(reservationMrg.isReservationStatus(ReservationStatus.EXPIRED)
					|| reservationMrg.isReservationStatus(ReservationStatus.CHECKOUT))) {
				char confirm;
				do {
					reservationMrg.printReservationInfo();

					confirm = readInputString("Press Y to confirm," + "N to discard and "
							+ "(No.) to edit a field.(Unable to edit Guest IC and Reservation Code)").toUpperCase()
									.charAt(0);

					switch (confirm) {
					case 'Y':
						reservationMrg.updateReservation();
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
	}

	private void updateReservationWaitList() {
		reservationMrg.printReservationsByStatus(ReservationStatus.WAITLIST);
		String reservationCode = readInputString("Enter reservation code:");
		Character confirm;
		if (ReservationMrg.checkReservationExist(reservationCode, ReservationStatus.WAITLIST,
				CheckInType.RESERVATION)) {
			reservationMrg.setReservationCode(reservationCode);
			enterRoomNum();

			do {
				reservationMrg.printReservationInfo();
				confirm = readInputString("Press Y to confirm," + "N to discard").toUpperCase().charAt(0);
				switch (confirm) {
				case 'Y':
					reservationMrg.updateReservation();
					break;
				case 'N':
					break;
				default:
					break;
				}
			} while (!(confirm.equals('Y') || confirm.equals('N')));
		}
	}

	private void deleteReservationMenu() {
		String reservationCode = readInputString("Enter reservation code:");
		if (ReservationMrg.checkReservationExist(reservationCode)) {
			reservationMrg.setReservationCode(reservationCode);
			reservationMrg.printReservationInfo();
			char confirm;
			do {
				confirm = readInputString("Press Y to confirm," + "N to discard").toUpperCase().charAt(0);
				if (confirm == 'Y') {
					reservationMrg.cancelReservation();
				}
			} while (!(confirm == 'Y' || confirm == 'N'));
		} else {
			System.out.println("Reservation Code does not exist");
		}

	}

	private void displayReservationMenu() {
		String choice;
		do {
			System.out.println("Reservation System\n" + "0. Return to Main Menu\n"
					+ "1. Search reservation by reservation code\n" + "2. Display all reservation\n");

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
		String reservationCode = readInputString("Enter reservation code:");
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
	}

	public void checkInMenu() {
		System.out.println("Room System\n" + "0. Return to Main Menu\n" + "1. Walk In \n" + "2. Reservation\n");
		String i;
		do {
			i = readInputString("Enter your choice:");
			switch (i) {
			case "0":
				break;
			case "1":
				WalkIncheckInMenu();
				break;
			case "2":
				reservationCheckInMenu();
				break;
			}
		} while (!i.equalsIgnoreCase("0"));
	}

	private void reservationCheckInMenu() {
		String reservationCode = readInputString("Enter reservation code:");
		if (ReservationMrg.checkReservationExist(reservationCode, ReservationStatus.CONFIRMED, CheckInType.RESERVATION)) {
			reservationMrg.setReservationCode(reservationCode);
			reservationMrg.setReservationStatus(ReservationStatus.CHECKIN);
			reservationMrg.checkInReservation();
			System.out.println("Sucessfully check in to the room");
		}else {
			System.out.println("Reservation is not found");
		}

	}

	private void WalkIncheckInMenu() {

	}

	private void enterCheckInDate() {
		do {
			LocalDateTime checkInDate = readInputDate("Enter Check In Date: (DD/MM/YYYY HH:mm)");
			if (checkInDate.isAfter(LocalDateTime.now())) {
				reservationMrg.setCheckIn(checkInDate);
				break;
			} else {
				System.out.println("Please enter the correct Date");
			}
		} while (true);
	}

	private void enterCheckOutDate() {

		do {
			LocalDateTime checkOutDate = readInputDate("Enter Check In Date: (DD/MM/YYYY HH:mm)");
			if (checkOutDate.isAfter(reservationMrg.getCheckIn())) {
				reservationMrg.setCheckOut(checkOutDate);
				break;
			} else {
				System.out.println("Please enter the correct Date");
			}
		} while (true);

	}

	private void enterNumOfAdult() {
		int numOfAdult = readInputInt("Enter number of adult(s): ");
		reservationMrg.setNumOfAdults(numOfAdult);

	}

	private void enterNumOfChild() {
		int numOfChild = readInputInt("Enter number of Child(ren): ");
		reservationMrg.setNumOfAdults(numOfChild);
	}

	private void enterRoomNum() {
		RoomMrg roomMrg = RoomMrg.getInstance();
		RoomType roomType;
		BedType bedType;
		boolean hasWifiBool;
		boolean allowSmokingBool;

		HashMap<String, String> enumData = RoomMrg.getInstance().getEnumTypeHashMap(RoomType.class);
		String strRoomType = readInputEnum("Enter room type: ", enumData);
		roomType = roomMrg.strToRoomType(strRoomType);

		enumData = RoomMrg.getInstance().getEnumTypeHashMap(BedType.class);
		String strBedType = readInputEnum("Enter bed type: ", enumData);
		bedType = roomMrg.strToBedType(strBedType);

		hasWifiBool = readInputBoolean("Enter Has Wifi (Y/N): ");
		allowSmokingBool = readInputBoolean("Enter allow Smoking (Y/N): ");

		List<String> roomNumList = new ArrayList<String>();

		roomNumList = roomMrg.getAndPrintAvailRoom(roomType, bedType, hasWifiBool, allowSmokingBool);
		String roomNum;

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
		} while (!roomNum.equalsIgnoreCase("0"));
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