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
import entity.Room;
import entity.Room.BedType;
import entity.Room.RoomType;

public class Reservation_Boundary {
	private Reservation reservation = ReservationMrg.createNewReservation();
	private Scanner sc = new Scanner(System.in);
	private ReservationMrg reservationMrg = ReservationMrg.getInstance();


	public void reservationMain() {
		String choice;
		do {
			System.out.println("Reservation System\n" + "0. Return to Main Menu\n" + "1. Create Reservation\n"
					+ "2. Update Reservation\n" + "3. Delete Reservation\n" + "4. Get Reservation Details\n");
			choice = sc.nextLine();

			switch (choice) {
			case "0":
				break;
			case "1":
				createReservationMenu("RESERVATION1");
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

	private void createReservationMenu(String type) {

		Character confirm;
		ReservationMrg reservationMrg = new ReservationMrg();

		reservation = new Reservation();
		reservation.setRoomList(new ArrayList<String>());
		System.out.println("Enter Guest IC:");
		String ic = sc.nextLine().toUpperCase();
		Guest g = GuestMrg.getInstance().searchGuestByIC(ic);
		if (g != null) {
			reservation.setGuestIC(ic);
			if(type.equalsIgnoreCase("RESERVATION")) {
			enterReservationCode();
			enterCheckInDate();
			}else {
				LocalDateTime checkInDate = LocalDateTime.now();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
				checkInDate = LocalDateTime.parse(checkInDate.format(formatter), formatter);
				reservation.setCheckIn(checkInDate);
				reservation.setReservationCode("");
			}
			enterCheckOutDate();
			enterNumOfAdult();
			enterNumOfChild();

			char c;

			do {
				enterRoomNum();
				System.out.println("Press Y to confirm," + "and any key to continue adding rooms");
				c = sc.nextLine().toUpperCase().charAt(0);
			} while (c != 'Y');

			do {
				reservation.printReservationInfo();
				System.out.println("Press Y to confirm," + "N to discard and " + "(No.) to edit a field.");
				confirm = sc.nextLine().toUpperCase().charAt(0);
				switch (confirm) {
				case 'Y':
					if(type.equalsIgnoreCase("RESERVATION")) {
						if (reservation.getRoomList() != null && reservation.getRoomList().size() > 0) {
							reservation.setReservationStatus(Reservation.ReservationStatus.CONFIRMED);
						} else {
							reservation.setReservationStatus(Reservation.ReservationStatus.WAITLIST);
						}
					reservationMrg.createReservation(reservation);
					}else {
						if (reservation.getRoomList() != null && reservation.getRoomList().size() > 0) {
							reservation.setReservationStatus(Reservation.ReservationStatus.CHECKIN);
							reservation.setReservationType(Reservation.ReservationType.WALKIN);
							reservationMrg.createReservation(reservation);
							RoomMrg.getInstance().checkInReservedRoom(reservation);
			
						}else {
							System.out.println("Unable to Check in as there are no room selected");
						}
	
					}
					break;
				case 'N':
					break;
				case '1':
					enterReservationCode();
					break;
				case '2':
					enterCheckInDate();
					break;
				case '3':
					enterCheckOutDate();
					break;
				case '4':
					enterNumOfAdult();
					break;
				case '5':
					enterNumOfChild();
					break;
				case '6':
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
				updateReservationStatus();
				break;
			}
		} while (!i.equalsIgnoreCase("0"));
	}
	private void updateReservationDetails() {
		System.out.println("Enter Reservation Code :");
		String reservationCode = sc.nextLine();
		reservation = reservationMrg.getReservationByCode(reservationCode);
		if (reservation != null) {
			if (!reservation.getReservationStatus().equals(Reservation.ReservationStatus.EXPIRED)) {
				char confirm;
				do {
					reservation.printReservationInfo();
					System.out.println("Press Y to confirm," + "N to discard and "
							+ "(No.) to edit a field.(Unable to edit Guest IC and Reservation Code)");
					confirm = sc.nextLine().toUpperCase().charAt(0);
					switch (confirm) {
					case 'Y':
						boolean success = reservationMrg.updateReservation(reservation);
						if (success) {
							System.out.println("Sucessfully update Reservation");
						} else {
							System.out.println("Unable to update Reservation");
						}
						break;
					case 'N':
						break;
					case '2':
						enterCheckInDate();
						break;
					case '3':
						enterCheckOutDate();
						break;
					case '4':
						enterNumOfAdult();
						break;
					case '5':
						enterNumOfChild();
						break;
					case '6':
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

	private void updateReservationStatus() {
		System.out.println("Enter Reservation Code : ");
		String reservationCode = sc.nextLine();
		reservation = reservationMrg.getReservationByCode(reservationCode);
		Character confirm;
		if (reservation != null) {
			if (reservation.getReservationStatus().equals(Reservation.ReservationStatus.WAITLIST)) {

				do {
					enterRoomNum();
					System.out.println("Press Y to confirm," + "and any key to continue adding rooms");
					if (reservation.getRoomList() != null && reservation.getRoomList().size() > 0) {
						reservation.setReservationStatus(Reservation.ReservationStatus.CONFIRMED);
					} else {
						reservation.setReservationStatus(Reservation.ReservationStatus.WAITLIST);
					}
					confirm = sc.nextLine().toUpperCase().charAt(0);
				} while (confirm != 'Y');

				do {
					reservation.printReservationInfo();
					System.out.println("Press Y to confirm," + "N to discard");
					confirm = sc.nextLine().charAt(0);
					switch (confirm) {
					case 'Y':
						boolean success = reservationMrg.updateReservation(reservation);
						if (success) {
							System.out.println("Sucessfully update room");
						} else {
							System.out.println("Unable to update room");
						}
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
			Reservation r = reservationMrg.getReservationByCode(reservationCode);
			if (r != null) {
				 r.printReservationInfo();
				 char confirm;
				 do {
					 System.out.println("Press 'Y' to delete Reservation and 'N' to Return");
					  confirm = sc.nextLine().toUpperCase().charAt(0);
					if(confirm == 'Y') {
						reservationMrg.cancelReservation(r);
					}
				 }while(!(confirm == 'Y' || confirm == 'N'));
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
			if (r != null) {
				if(!r.getReservationStatus().equals(Reservation.ReservationStatus.EXPIRED)){
					r.printReservationInfo();
				}else {
					System.out.println("Reservation has already expired");
				}
			}else {
				System.out.println("Reservation does not exist");
			}


	}
	private void searchAllReservation() {
		List<Reservation> allReservationList = reservationMrg.getAllReservation();
		for(Reservation r :allReservationList) {
			if(!r.getReservationStatus().equals(Reservation.ReservationStatus.EXPIRED)){
				r.printReservationInfo();
			}
		}
	}
	private void enterReservationCode() {
		do {
			System.out.println("Enter Reservation Code:");
			String reservationCode = sc.nextLine();
			Reservation r = reservationMrg.getReservationByCode(reservationCode);
			if (r == null) {
				reservation.setReservationCode(reservationCode);
				break;
			} else {
				System.out.println("Reservation Code already exist");
			}

		} while (true);
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
		String choice;
		do {
			System.out.println("0. Exit edit Rooms");
			System.out.println("1. Add Rooms");
			System.out.println("2. Remove Rooms");
			choice = sc.nextLine();
			switch (choice) {
			case "0":
				break;
			case "1":
				addRoomNum();
				break;

			case "2":
				removeRoomNum();
				break;
			}

		} while (!choice.equalsIgnoreCase("0"));
	}

	private void addRoomNum() {
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

		roomList = roomMrg.getAvailRoom(Room.RoomStatus.VACANT, roomType, bedType, hasWifiBool, allowSmokingBool);

		if (roomList != null && roomList.size() > 0) {
			for (Room room : roomList) {
				System.out.println(
						"Room Number: " + room.getRoomNumber() + ", Weekday Rate: " + +room.getRoomRateWeekday()
								+ ", Weekend Rate: " + room.getRoomRateWeekend() + ", Facing: " + room.getFacing());
			}

			String input;

			do {
				System.out.println("Enter the room number to be selected (Enter 0 to exit): ");
				input = sc.nextLine();
				boolean addRoomNum = true;
				for (Room room : roomList) {
					if (room.getRoomNumber().equalsIgnoreCase(input)) {
						for (String roomNum : reservation.getRoomList()) {
							if (roomNum.equalsIgnoreCase(input)) {
								addRoomNum = false;
							}
						}
						if (addRoomNum) {
							reservation.getRoomList().add(room.getRoomNumber());
						}
					}
				}
			} while (!input.equalsIgnoreCase("0"));
		} else {
			System.out.println("No Available Room");
		}
	}

	private void removeRoomNum() {
		System.out.println("REMOVE");
		String roomNum = null;
		do {
			if (reservation.getRoomList() != null && reservation.getRoomList().size() > 0) {
				System.out.println("Room Number: ");
				for (String room : reservation.getRoomList()) {
					System.out.print(room + " ");
				}
				System.out.println();
				System.out.println("Please enter room number to be remove:(Enter 0 to exit) ");
				
					roomNum = sc.nextLine();
					if(roomNum.matches("^[0-9]*$")) {
					boolean remove = false;
					for (String room : reservation.getRoomList()) {
						if (room.equalsIgnoreCase(roomNum)) {
							remove= true;
						}
					}
					if (remove) {
					reservation.getRoomList().remove(roomNum);
					}
				} else {
					System.out.println("Please enter room number in digits");

				}
			} else {
				System.out.println("No room has been selected");
				roomNum = "0";
			}
		} while (!roomNum.equalsIgnoreCase("0"));

	}

	public static void main(String[] args) {
		try {
			RoomMrg roomMrg = new RoomMrg();
			GuestMrg guestMrg = new GuestMrg();
			roomMrg.loadRoomData();
			guestMrg.loadGuestData();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Reservation_Boundary reservation_Boundary = new Reservation_Boundary();
		reservation_Boundary.reservationMain();
	}

}
