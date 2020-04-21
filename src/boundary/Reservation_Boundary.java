package boundary;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import controller.GuestMrg;
import controller.ReservationMrg;
import controller.RoomMrg;
import entity.Guest;
import entity.Reservation;
import entity.Reservation.CheckInType;
import entity.Reservation.ReservationStatus;
import entity.Room;
import entity.Room.BedType;
import entity.Room.RoomType;

public class Reservation_Boundary extends Boundary{
	private Reservation reservation = ReservationMrg.createNewReservation();
	private Scanner sc = new Scanner(System.in);
	private ReservationMrg reservationMrg = ReservationMrg.getInstance();

	public void displayMain() {
		String choice;
		do {
			System.out.println("Reservation System\n" + "0. Return to Main Menu\n" + "1. Create Reservation\n"
					+ "2. Update Reservation\n" + "3. Delete Reservation\n" + "4. Get Reservation Details\n");
			choice = sc.nextLine();

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
		sc.close();
	}

	private void createReservationMenu() {

		Character confirm;
		ReservationMrg reservationMrg = new ReservationMrg();

		reservation = new Reservation();
		System.out.println("Enter Guest IC:");
		String ic = sc.nextLine().toUpperCase();
		Guest g = GuestMrg.getInstance().getGuestByIC(ic);
		if (g != null) {
			reservation.setCheckInType(CheckInType.RESERVATION);
			reservation.setGuestIC(ic);
			reservation.setReservationCode(reservationMrg.generateReservationCode(ic));
			enterCheckInDate();
			enterCheckOutDate();
			enterNumOfAdult();
			enterNumOfChild();
			enterRoomNum();
			if (reservation.getRoomNum() == null) {
				reservation.setReservationStatus(ReservationStatus.WAITLIST);
			}else {
				reservation.setReservationStatus(ReservationStatus.CONFIRMED);
			}
			do {
				reservation.printInfo();
				System.out.println("Press Y to confirm," + "N to discard and " + "(No.) to edit a field.");
				confirm = sc.nextLine().toUpperCase().charAt(0);
				switch (confirm) {
				case 'Y':
					reservationMrg.createReservation(reservation);
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

			i = sc.nextLine();
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
		System.out.println("Enter Reservation Code :");
		String reservationCode = sc.nextLine();
		reservation = reservationMrg.getReservationByCode(reservationCode);
		if (reservation != null) {
			String oldRoomNum = reservation.getRoomNum();
			if (!reservation.getReservationStatus().equals(Reservation.ReservationStatus.EXPIRED)) {
				char confirm;
				do {
					reservation.printInfo();
					System.out.println("Press Y to confirm," + "N to discard and "
							+ "(No.) to edit a field.(Unable to edit Guest IC and Reservation Code)");
					confirm = sc.nextLine().toUpperCase().charAt(0);
					switch (confirm) {
					case 'Y':
						 reservationMrg.updateReservation(reservation,oldRoomNum);
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
		List<Reservation> waitList = new ArrayList<Reservation>();
		waitList = reservationMrg.getReservationByReservationStatus(ReservationStatus.WAITLIST);
		for(Reservation r : waitList) {
			r.printInfo();
		}
		System.out.println("Enter Reservation Code : ");
		String reservationCode = sc.nextLine();
		reservation = reservationMrg.getReservationByCode(reservationCode);
		Character confirm;
		if (reservation != null && reservation.getCheckInType().equals(CheckInType.RESERVATION)) {
			if (reservation.getReservationStatus().equals(Reservation.ReservationStatus.WAITLIST)) {
					enterRoomNum();
					if (reservation.getRoomNum() == null) {
						reservation.setReservationStatus(ReservationStatus.WAITLIST);
					}else {
						reservation.setReservationStatus(ReservationStatus.CONFIRMED);
					}
				do {
					reservation.printInfo();
					System.out.println("Press Y to confirm," + "N to discard");
					confirm = sc.nextLine().toUpperCase().charAt(0);
					switch (confirm) {
					case 'Y':
					reservationMrg.updateReservation(reservation,null);
						break;
					case 'N':
						break;
					default:
						break;
					}
				} while (!(confirm.equals('Y') || confirm.equals('N')));
			}
		}
	}

	private void deleteReservationMenu() {

		System.out.println("Enter Reservation Code:");
		String reservationCode = sc.nextLine();
		reservation = reservationMrg.getReservationByCode(reservationCode);
		if (reservation != null) {
			reservation.printInfo();
			char confirm;
			do {
				System.out.println("Press 'Y' to delete Reservation and 'N' to Return");
				confirm = sc.nextLine().toUpperCase().charAt(0);
				if (confirm == 'Y') {
					reservationMrg.cancelReservation(reservation);
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

			choice = sc.nextLine();
			switch (choice) {
			case "0":
				break;
			case "1":
				searchByReservationCode();
				break;
			case "2":
				searchAllReservation();
				break;
			}
		} while (!choice.equalsIgnoreCase("0"));
	}

	private void searchByReservationCode() {
		System.out.println("Enter Reservation Code:");
		String reservationCode = sc.nextLine();
		Reservation r = reservationMrg.getReservationByCode(reservationCode);
		if (r != null && r.getCheckInType().equals(CheckInType.RESERVATION)) {
			if (!(r.getReservationStatus().equals(ReservationStatus.EXPIRED)
					|| r.getReservationStatus().equals(ReservationStatus.CHECKOUT))) {
				r.printInfo();
			} else {
				System.out.println("Reservation has already expired");
			}
		} else {
			System.out.println("Reservation does not exist");
		}

	}

	private void searchAllReservation() {
		List<Reservation> allReservationList = reservationMrg.getAllReservation();
		for (Reservation r : allReservationList) {
			if (r.getCheckInType().equals(CheckInType.RESERVATION)) {
				if (!(r.getReservationStatus().equals(Reservation.ReservationStatus.EXPIRED)
						|| r.getReservationStatus().equals(ReservationStatus.CHECKOUT))) {
					r.printInfo();
				}
			}
		}
	}

	private void enterCheckInDate() {
		do {
			try {
				System.out.println("Enter Check In Date: (DD/MM/YYYY HH:mm)");
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
				String strCheckInDate = sc.nextLine();
				LocalDateTime checkInDate = LocalDateTime.parse(strCheckInDate, formatter);

				if (checkInDate.isAfter(LocalDateTime.now())) {
					reservation.setCheckIn(checkInDate);
					break;
				} else {
					System.out.println("Please enter the correct Date");
				}
			} catch (DateTimeParseException e) {
				System.out.println("Please enter the correct Format");
			}
		} while (true);
	}

	private void enterCheckOutDate() {

		do {
			try {
				System.out.println("Enter Check Out Date: (DD/MM/YYYY HH:mm)");
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
				String strCheckInDate = sc.nextLine();
				LocalDateTime checkInDate = LocalDateTime.parse(strCheckInDate, formatter);

				if (checkInDate.isAfter(reservation.getCheckIn())) {
					reservation.setCheckOut(checkInDate);
					break;
				} else {
					System.out.println("Please enter the correct Date");
				}
			} catch (DateTimeParseException e) {
				System.out.println("Please enter the correct Format");
			}
		} while (true);
	}

	private void enterNumOfAdult() {
		do {
			System.out.println("Enter number of Adult(s)");
			if (sc.hasNextInt()) {
				reservation.setNumOfAdults(sc.nextInt());
				sc.nextLine();
				break;
			} else {
				System.out.println("Please enter the in digits!");
				sc.nextLine();
			}
		} while (true);

	}

	private void enterNumOfChild() {

		do {
			System.out.println("Enter number of Child(ren)");
			if (sc.hasNextInt()) {
				reservation.setNumOfChild(sc.nextInt());
				sc.nextLine();
				break;
			} else {
				System.out.println("Please enter the in digits!");
				sc.nextLine();
			}
		} while (true);

	}

	private void enterRoomNum() {
		RoomMrg roomMrg = RoomMrg.getInstance();
		List<Room> roomList = new ArrayList<Room>();
		RoomType roomType;
		BedType bedType;
		boolean hasWifiBool;
		boolean allowSmokingBool;
		do {
			System.out.println("Enter room type: SINGLE, DOUBLE, DELUXE, VIP  ");
			String StrRoomType = sc.nextLine();
			if (StrRoomType.equalsIgnoreCase("SINGLE") || StrRoomType.equalsIgnoreCase("DOUBLE")
					|| StrRoomType.equalsIgnoreCase("DELUXE") || StrRoomType.equalsIgnoreCase("VIP")) {
				roomType = RoomMrg.strToRoomType(StrRoomType);
				break;
			} else {
				System.out.println("Please enter the correct room type!");
			}
		} while (true);

		do {
			System.out.println("Enter bed type: SINGLE, DOUBLE, KING ");
			String StrBedType = sc.nextLine();
			if (StrBedType.equalsIgnoreCase("SINGLE") || StrBedType.equalsIgnoreCase("DOUBLE")
					|| StrBedType.equalsIgnoreCase("KING")) {
				bedType = RoomMrg.strToBedType(StrBedType);
				break;
			} else {
				System.out.println("Please enter the correct bed type!");
			}
		} while (true);

		do {
			System.out.println("Enter has wifi: (Y/N) ");
			String input = sc.nextLine();
			if (input.equalsIgnoreCase("y") || input.equalsIgnoreCase("N")) {
				if (input.equalsIgnoreCase("Y")) {
					hasWifiBool = true;
				} else {
					hasWifiBool = false;
				}
				break;
			} else {
				System.out.println("Please enter Y/N ");

			}
		} while (true);

		do {
			System.out.println("Enter allow smoking: (Y/N) ");
			String input = sc.nextLine();
			if (input.equalsIgnoreCase("y") || input.equalsIgnoreCase("N")) {
				if (input.equalsIgnoreCase("Y")) {
					allowSmokingBool = true;
				} else {
					allowSmokingBool = false;
				}
				break;
			} else {
				System.out.println("Please enter Y/N ");

			}
		} while (true);

		roomList = roomMrg.getAvailRoom(roomType, bedType, hasWifiBool, allowSmokingBool);
		String roomNum;
		List<String> roomNumList = new ArrayList<String>();
		if (roomList != null && roomList.size() > 0) {
			for (Room room : roomList) {
				System.out.println(
						"Room Number: " + room.getRoomNumber() + ", Weekday Rate: " +String.format("%.2f",room.getRoomRateWeekday())
								+ ", Weekend Rate: " +String.format("%.2f",room.getRoomRateWeekend()) + ", Facing: " + room.getFacing());
				roomNumList.add(room.getRoomNumber());
			}

			do {
				System.out.println("Enter the room number to be selected (Enter 0 to exit): ");
				roomNum = sc.nextLine();
				boolean isValid = false;
				for (String roomNo : roomNumList) {
					if (roomNo.equalsIgnoreCase(roomNum)) {
						isValid = true;
					}
				}
				if(isValid) {
				reservation.setRoomNum(roomNum);
				break;
				}
			} while (!roomNum.equalsIgnoreCase("0"));
		} else {
			System.out.println("There are no available room");
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