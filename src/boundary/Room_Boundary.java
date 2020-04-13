package boundary;

import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import controller.GuestMrg;
import controller.ReservationMrg;
import controller.RoomMrg;
import entity.Guest;
import entity.Reservation;
import entity.Room;

public class Room_Boundary {
	static Room room;
	static Scanner sc = new Scanner(System.in);

	public static void roomMain() {
		int choice = 0;
		do {
			System.out.println("Room System\n" + "0. Return to Main Menu\n" + "1. Create Room\n" + "2. Update Room\n"
					+ "3. Search Room\n" + "4. Check In\n" + "5. Check Out\n" + "6. Print Room Status Report\n");
			choice = sc.nextInt();
			sc.hasNextLine();
			switch (choice) {
			case 0:
				break;
			case 1:
				createRoom();
				break;
			case 2:
				updateRoomBydetails();
				break;
			case 3:
				System.out.println("Room System\n" + "0. Return to Main Menu\n" + "1. Search Room by Room number\n"
						+ "2. Search Room by Guest name\n");

				int i = sc.nextInt();
				sc.nextLine();
				switch (i) {
				case 0:
					break;
				case 1:
					searchRoomByRoomNum();
					break;
				case 2:
					searchRoomByGuestName();
					break;
				}

				break;
			case 4:
				System.out.println("Room System\n" + "0. Return to Main Menu\n" + "1. Walk In \n" + "2. Reservation\n");

				int choice2 = sc.nextInt();
				sc.nextLine();
				switch (choice2) {
				case 0:
					break;
				case 1:
					WalkIncheckIn();
					break;
				case 2:
					reservationCheckIn();
					break;
				}
				break;
			case 5:
				// printCheckOutMenu();
			case 6:
				getRoomReport();
			}
		} while (choice != 0);
	}

	public static void createRoom() {
		Character confirm;
		System.out.println("Create Room");
		room = new Room();
		enterRoomNum();
		enterRoomType();
		enterBedType();
		enterFacing();
		enterWeekdayRate();
		enterWeekendRate();
		enterAllowSmoking();
		enterHasWifi();
		room.setRoomStatus(Room.RoomStatus.VACANT);

		do {
			printRoomInfo(room);
			System.out.println("Press Y to confirm," + "N to discard and " + "(No.) to edit a field.");
			confirm = sc.nextLine().toUpperCase().charAt(0);
			switch (confirm) {
			case 'Y':
				RoomMrg.createRoom(room);
				break;
			case 'N':
				roomMain();
				break;
			case '1':
				enterRoomNum();
				break;
			case '2':
				enterRoomType();
				break;
			case '3':
				enterBedType();
				break;
			case '4':
				enterFacing();
				break;
			case '5':
				enterWeekdayRate();
				break;
			case '6':
				enterWeekendRate();
				break;
			case '7':
				enterAllowSmoking();
				break;
			case '8':
				enterHasWifi();
				break;
			default:
				break;
			}
		} while (!(confirm.equals('Y') || confirm.equals('N')));
	}

	public static void updateRoomBydetails() {
		System.out.println("update Room");
		System.out.println("Enter room number : ");
		String roomNum = sc.nextLine();
		room = RoomMrg.searchRoomByNum(roomNum);
		if (room != null) {
			Character confirm;
			enterRoomType();
			enterBedType();
			enterFacing();
			enterWeekdayRate();
			enterWeekendRate();
			enterAllowSmoking();
			enterHasWifi();

			do {
				printRoomInfo(room);
				System.out.println("Press Y to confirm," + "N to discard and "
						+ "(No.) to edit a field.(Unable to select Room Number)");
				confirm = sc.nextLine().charAt(0);
				switch (confirm) {
				case 'Y':
					boolean success = RoomMrg.updateRoom(room);
					if (success) {
						System.out.println("Sucessfully update room");
					} else {
						System.out.println("Unable to update room");
					}
					break;
				case 'N':
					break;
				case '2':
					enterRoomType();
					break;
				case '3':
					enterBedType();
					break;
				case '4':
					enterFacing();
					break;
				case '5':
					enterWeekdayRate();
					break;
				case '6':
					enterWeekendRate();
					break;
				case '7':
					enterAllowSmoking();
					break;
				case '8':
					enterHasWifi();
					break;
				default:
					break;
				}
			} while (!(confirm.equals('Y') || confirm.equals('N')));
		}

	}

	public static void searchRoomByRoomNum() {
		System.out.println("Enter room number: ");
		String roomNum = sc.nextLine();
		room = RoomMrg.searchRoomByNum(roomNum);
		printRoomInfo(room);
	}

	public static void searchRoomByGuestName() {
		System.out.println("Enter Guest Name: ");
		String name = sc.nextLine();
		List<Room> roomList = RoomMrg.searchRoomByGuestName(name);
		if (roomList.size() > 0) {
			for (Room r : roomList) {
				printRoomInfo(r);
			}
		} else {
			System.out.println("No room found by the name " + name);
		}
	}
	
	
	public static void WalkIncheckIn()  {
    	System.out.println("Enter the guest IC: ");
    	String ic = sc.nextLine();
        Guest g = GuestMrg.searchGuestByIC(ic);
        if(g != null) {
        	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyy HH:mm");
        	
        	LocalDateTime checkOut = LocalDateTime.now(); 
        	LocalDateTime checkIn = LocalDateTime.now(); 
        	
        	checkIn = LocalDateTime.parse(checkIn.format(formatter) ,formatter);
        	do {
        	System.out.println("Enter the check out date in (DD/MM/YYYY HH:mm)");
        	checkOut = LocalDateTime.parse(sc.nextLine(), formatter);
        	}while(checkOut.isBefore(checkIn));
        	
        	System.out.println("Enter Room Type : SINGLE, DOUBLE, DELUXE, VIP  ");
        	List<Room> RoomTypeList = RoomMrg.searchRoomByRoomType(sc.nextLine().toUpperCase());
 
        	char confirm;
        	do {
        	System.out.println(" -------------------------------------------");
        	List<Room> availableRoomList = new ArrayList<Room>();
        	for(Room room : RoomTypeList) {
           		if(room.getRoomStatus().equals(Room.RoomStatus.VACANT)) {
           			availableRoomList.add(room);
           		}
        	}
        	System.out.print("	Rooms: ");
        	printRoomNumber(availableRoomList);
        	String roomNum = sc.nextLine();
        	Room selectedRoom = RoomMrg.searchRoomByNum(roomNum);
        	//RoomMrg.printRoomInfo(selectedRoom);
        	System.out.println(" -------------------------------------------");
        	System.out.println("Press Y to confirm or N to discard");
        	confirm = sc.nextLine().toUpperCase().charAt(0);
        	
        	if(confirm == 'Y') {
        		RoomMrg.updateRoom(selectedRoom ,checkOut , checkIn , ic, Room.RoomStatus.OCCUPIED);
        	}      
        	}while(confirm != 'Y');
        }
     
    }
	

    public static void reservationCheckIn() {
    	System.out.println("Please enter the reservation code: ");
    	String reservationCode = sc.nextLine();
    	Reservation reservation = ReservationMrg.getReservationByCode(reservationCode);
    	if(reservation != null) {
    		if(reservation.getReservationStatus().equals(Reservation.ReservationStatus.CONFIRMED)) {
    			RoomMrg.checkInReservedRoom(reservation);
    			System.out.println("Sucessfully check in to the room");
    		}else {
    			System.out.println("Reservation is on "+ reservation.getReservationStatus());
    		}
    	}else {
    		System.out.println("Reservation not found");
    	}
    	sc.close();
    }
    
    
    
	public static void getRoomReport() {
		int singleRoomTotal = 0;
		int doubleRoomTotal = 0;
		int deluxeRoomTotal = 0;
		int vipRoomListTotal = 0;

		int singleRoomVacantCount = 0;
		int doubleRoomVacantCount = 0;
		int deluxeRoomVacantCount = 0;
		int vipRoomListVacantCount = 0;

		List<Room> singleRoomList = new ArrayList<Room>();
		List<Room> doubleRoomList = new ArrayList<Room>();
		List<Room> deluxeRoomList = new ArrayList<Room>();
		List<Room> vipRoomList = new ArrayList<Room>();

		for (Room r : RoomMrg.rooms) {
			if (r.getRoomType().equals(Room.RoomType.SINGLE)) {
				if (r.getRoomStatus().equals(Room.RoomStatus.VACANT)) {
					singleRoomList.add(r);
					singleRoomVacantCount++;
				}
				singleRoomTotal++;
			}
			if (r.getRoomType().equals(Room.RoomType.DOUBLE)) {
				if (r.getRoomStatus().equals(Room.RoomStatus.VACANT)) {
					doubleRoomList.add(r);
					doubleRoomVacantCount++;
				}
				doubleRoomTotal++;
			}
			if (r.getRoomType().equals(Room.RoomType.DELUXE)) {
				if (r.getRoomStatus().equals(Room.RoomStatus.VACANT)) {
					deluxeRoomList.add(r);
					deluxeRoomVacantCount++;
				}
				deluxeRoomTotal++;
			}
			if (r.getRoomType().equals(Room.RoomType.VIP)) {
				if (r.getRoomStatus().equals(Room.RoomStatus.VACANT)) {
					vipRoomList.add(r);
					vipRoomListVacantCount++;
				}
				vipRoomListTotal++;
			}

		}
		System.out.println("Room type occupancy rate");

		System.out.println("Single: Number: " + singleRoomVacantCount + " out of " + singleRoomTotal);
		System.out.print("	Rooms: ");
		printRoomNumber(singleRoomList);

		System.out.println("Double: Number: " + doubleRoomVacantCount + " out of " + doubleRoomTotal);
		System.out.print("	Rooms: ");
		printRoomNumber(doubleRoomList);

		System.out.println("Deluxe: Number: " + deluxeRoomVacantCount + " out of " + deluxeRoomTotal);
		System.out.print("	Rooms: ");
		printRoomNumber(deluxeRoomList);

		System.out.println("VIP:    Number: " + vipRoomListVacantCount + " out of " + vipRoomListTotal);
		System.out.print("	Rooms: ");
		printRoomNumber(vipRoomList);

		List<Room> vacantList = new ArrayList<Room>();
		List<Room> occupiedList = new ArrayList<Room>();
		List<Room> reservedList = new ArrayList<Room>();
		List<Room> maintenanceList = new ArrayList<Room>();

		System.out.println("Room status");

		for (Room r : RoomMrg.rooms) {
			if (r.getRoomStatus().equals(Room.RoomStatus.VACANT)) {
				vacantList.add(r);
			}
			if (r.getRoomStatus().equals(Room.RoomStatus.OCCUPIED)) {
				occupiedList.add(r);
			}
			if (r.getRoomStatus().equals(Room.RoomStatus.RESERVED)) {
				reservedList.add(r);
			}
			if (r.getRoomStatus().equals(Room.RoomStatus.UNDER_MAINTENANCE)) {
				maintenanceList.add(r);
			}
		}
		System.out.println("Vacant: ");
		System.out.print("	Room :");
		printRoomNumber(vacantList);

		System.out.println("OCCUPIED: ");
		System.out.print("	Room :");
		printRoomNumber(vacantList);

		System.out.println("RESERVED: ");
		System.out.print("	Room :");
		printRoomNumber(vacantList);

		System.out.println("UNDER_MAINTENANCE: ");
		System.out.print("	Room :");
		printRoomNumber(vacantList);
	}

	public static void enterRoomNum() {
		System.out.println("Enter room number: ");
		room.setRoomNumber(sc.nextLine());
	}

	public static void enterRoomType() {
		System.out.println("Enter room type: SINGLE, DOUBLE, DELUXE, VIP  ");
		String roomType = sc.nextLine();
		room.setRoomType(RoomMrg.strToRoomType(roomType));
	}

	public static void enterBedType() {
		System.out.println("Enter bed type: SINGLE, DOUBLE, KING ");
		String bedType = sc.nextLine();
		room.setBedType(RoomMrg.strToBedType(bedType));
	}

	public static void enterFacing() {
		System.out.println("Enter facing: NORTH, SOUTH, EAST, WEST ");
		String facing = sc.nextLine();
		room.setFacing(RoomMrg.strToFacing(facing));
	}

	public static void enterWeekdayRate() {
		System.out.println("Enter weekday rate: ");
		room.setRoomRateWeekday(sc.nextDouble());
		sc.nextLine();
	}

	public static void enterWeekendRate() {
		System.out.println("Enter weekend rate: ");
		room.setRoomRateWeekend(sc.nextDouble());
		sc.nextLine();
	}

	public static void enterAllowSmoking() {
		System.out.println("Enter allow smoking: (Y/N) ");

		boolean bool;
		if (sc.nextLine().equalsIgnoreCase("y"))
			bool = true;
		else
			bool = false;

		room.setSmoking(bool);
	}

	public static void enterHasWifi() {
		System.out.println("Enter has wifi: (Y/N) ");
		boolean bool;
		if (sc.nextLine().equalsIgnoreCase("y"))
			bool = true;
		else
			bool = false;
		room.setWifi(bool);
	}

	public static void printRoomNumber(List<Room> list) {
		if (list.isEmpty()) {
			System.out.println("Contain no room");
		} else {
			for (Room r : list) {
				String displayRoomNumber = r.getRoomNumber().substring(0, 2) + "-"
						+ r.getRoomNumber().substring(2, r.getRoomNumber().length());
				System.out.print(displayRoomNumber + " ");
			}
			System.out.println();
		}
	}

	public static void printRoomInfo(Room room) {
		System.out.println(" -------------------------------------------");
		System.out.println("1.Room No: " + room.getRoomNumber());
		System.out.println("2.Room Type: " + room.getRoomType());
		System.out.println("3.Bed Type: " + room.getBedType());
		System.out.println("4.Room Facing: " + room.getFacing());
		System.out.println("5.Weekday Rate: $" + room.getRoomRateWeekday());
		System.out.println("6.Weekend Rate: $" + room.getRoomRateWeekend());
		System.out.println("7.Allowing Smoking: " + room.isSmoking());
		System.out.println("8.Has Wifi: " + room.isWifi());
		System.out.println("9.Room Status: " + room.getRoomStatus());

		if (room.getRoomStatus().equals(Room.RoomStatus.OCCUPIED)) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
			System.out.println("10.Check in Date: " + formatter.format(room.getCheckInDate()));
			System.out.println("11.Check out Date: " + formatter.format(room.getCheckOutDate()));
		}
		System.out.println(" -------------------------------------------");
	}

	public static void main(String[] args) {
		 try {
		 RoomMrg.loadRoomData();
		 GuestMrg.loadGuestData();
		 } catch (FileNotFoundException e) {
		 //TODO Auto-generated catch block
		 e.printStackTrace();
		 }
		 getRoomReport();
		 
		roomMain();

	}
}
