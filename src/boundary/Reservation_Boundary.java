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
import entity.Room.Facing;
import entity.Room.RoomType;

public class Reservation_Boundary {
	private Reservation reservation = ReservationMrg.createNewReservation();
	private Scanner sc = new Scanner(System.in);
	private ReservationMrg reservationMrg = ReservationMrg.getInstance();
	
	public void reservationMain() {
		int choice = 0;
		do {
			System.out.println("Reservation System\n" + "0. Return to Main Menu\n" + "1. Create Reservation\n"
					+ "2. Update Reservation\n" + "3.Delete Reservation\n" + "4. Get Reservation Details\n");
			choice = sc.nextInt();
			sc.nextLine();

			switch (choice) {
			case 0:
				break;
			case 1:
				createReservationMenu();
				break;
			case 2:
				System.out.println("Reservation System\n" + "0. Return to Main Menu\n"
						+ "1. Update Reservation By Details\n" + "2. Update Reservation WaitList\n");

				int i = sc.nextInt();
				sc.nextLine();
				switch (i) {
				case 0:
					break;
				case 1:
					updateReservationDetails();
					break;
				case 2:
					updateReservationStatus();
					break;
				}
				break;
			case 3:
				// deleteReservationMenu();
				break;
			case 4:
				System.out.println("Reservation System\n" + "0. Return to Main Menu\n"
						+ "1. Search reservation by reservation code\n" + "2. Display all reservation\n");

				int choice2 = sc.nextInt();
				sc.nextLine();
				switch (choice2) {
				case 0:
					break;
				case 1:
					// searchReservationMenu();
					break;
				case 2:
					// searchAllReservationMenu();
					break;
				}
				break;
			default:
				break;
			}
		} while (choice != 0);
		sc.close();
	}

	private void createReservationMenu() {

		Character confirm;
		ReservationMrg reservationMrg = new ReservationMrg();

		System.out.println("Create Reservation");
		reservation = new Reservation();
		reservation.setRoomList(new ArrayList<String>());
		System.out.println("Enter Guest IC:");
		String ic = sc.nextLine();
		Guest g = GuestMrg.getInstance().searchGuestByIC(ic);
		System.out.println(g.getIC());
		if (g != null) {
			reservation.setGuestIC(ic);
			enterReservationCode();
			enterCheckInDate();
			enterCheckOutDate();
			enterNumOfAdult();
			enterNumOfChild();
			
			char c;
			
			do {
			enterRoomNum();
			System.out.println("Press Y to confirm," + "and any key to continue adding rooms");
			if(reservation.getRoomList() != null && reservation.getRoomList().size()>0) {
				reservation.setReservationStatus(Reservation.ReservationStatus.CONFIRMED);
			}else {
				reservation.setReservationStatus(Reservation.ReservationStatus.WAITLIST);			
			}
			c = sc.nextLine().toUpperCase().charAt(0);
			}while(c != 'Y');
			
			do {
				reservation.printReservationInfo();
				System.out.println("Press Y to confirm," + "N to discard and " + "(No.) to edit a field.");
				confirm = sc.nextLine().toUpperCase().charAt(0);
				switch (confirm) {
				case 'Y':
					reservationMrg.createReservation(reservation);
					break;
				case 'N':
					break;
				case '1':
					enterReservationCode();
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
					break;
				default:
					break;
				}
			} while (!(confirm.equals('Y') || confirm.equals('N')));
		}
	}
	
	private void updateReservationDetails() {
		System.out.println("Enter Reservation Code :");
		String reservationCode = sc.nextLine();
		reservation = reservationMrg.getReservationByCode(reservationCode);
		if(reservation != null) {
			if(!reservation.getReservationStatus().equals(Reservation.ReservationStatus.EXPIRED)) {
				char confirm;
				do {
					reservation.printReservationInfo();
					System.out.println("Press Y to confirm," + "N to discard and "
							+ "(No.) to edit a field.(Unable to edit Guest IC and Reservation Code)");
					confirm = sc.nextLine().charAt(0);
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
			}else {
			 System.out.println("Reservation had already expired");
			}
		}else {
			System.out.println("Reservation does not exist by this Reservation code");
		}
	}

	private void enterReservationCode() {
		Reservation_Boundary reservation_Boundary = new Reservation_Boundary();
		Scanner sc = reservation_Boundary.sc;
		ReservationMrg reservationMrg = new ReservationMrg();

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
		Reservation_Boundary reservation_Boundary = new Reservation_Boundary();
		Scanner sc = reservation_Boundary.sc;
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
		Reservation_Boundary reservation_Boundary = new Reservation_Boundary();
		Scanner sc = reservation_Boundary.sc;

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
		Reservation_Boundary reservation_Boundary = new Reservation_Boundary();
		Scanner sc = reservation_Boundary.sc;
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
		Reservation_Boundary reservation_Boundary = new Reservation_Boundary();
		Scanner sc = reservation_Boundary.sc;
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
		Reservation_Boundary reservation_Boundary = new Reservation_Boundary();
		RoomMrg roomMrg = new RoomMrg();
		Scanner sc = reservation_Boundary.sc;
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

		roomList = roomMrg.getAvailRoom(Room.RoomStatus.VACANT ,roomType,bedType,hasWifiBool, allowSmokingBool);
	
		if(roomList != null && roomList.size() > 0) {
			for(Room room : roomList) {
				System.out.println("Room Number: " + room.getRoomNumber() + ", Weekday Rate: "+
			+ room.getRoomRateWeekday() +", Weekend Rate: " + room.getRoomRateWeekend() + ", Facing: " + room.getFacing());
			}
		
		String input;

		do {
			System.out.println("Enter the room number to be selected (Enter 0 to exit): ");
			input = sc.nextLine();
			boolean addRoomNum = true;
			for(Room room : roomList) {
				if(room.getRoomNumber().equalsIgnoreCase(input)) {
					for(String roomNum : reservation.getRoomList()) {
						if(roomNum.equalsIgnoreCase(input)) {
							addRoomNum = false;
						}
					}
					if(addRoomNum) {
						 reservation.getRoomList().add(room.getRoomNumber());
					}
				}
			}
		}while(!input.equalsIgnoreCase("0"));
		}else {
			System.out.println("No Available Room");
		}
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
