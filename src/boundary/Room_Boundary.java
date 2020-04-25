package boundary;

import java.io.FileNotFoundException;
import java.util.HashMap;

import controller.RoomMrg;
import entity.Room;
import entity.Room.BedType;
import entity.Room.Facing;
import entity.Room.RoomStatus;
import entity.Room.RoomType;

public class Room_Boundary extends Boundary {

	private RoomMrg roomMrg = RoomMrg.getInstance();

	public void displayMain() {
		String choice;
		do {
			System.out.println("-------------------------------------------");
			System.out.println("Room System\n" + "0. Return to Main Menu\n" + "1. Create Room\n" + "2. Update Room\n"
					+ "3. Search Room\n" + "4. Print Room Status Report");
			System.out.println("-------------------------------------------");
			choice = readInputString("Enter choice : ");

			switch (choice) {
			case "0":
				break;
			case "1":
				createRoomMenu();
				break;
			case "2":
				updateRoomMenu();
				break;
			case "3":
				searchRoomMenu();
				break;
			case "4":
				printRoomReportMenu();
				break;
			}
		} while (!choice.equalsIgnoreCase("0"));

	}

	private void createRoomMenu() {
		Character confirm;
		roomMrg.createNewRoom();
		// get user input
		enterRoomNum();
		enterRoomType();
		enterBedType();
		enterFacing();
		enterWeekdayRate();
		enterWeekendRate();
		enterAllowSmoking();
		enterHasWifi();
		roomMrg.setRoomStatus(RoomStatus.VACANT);
		do {
			roomMrg.printRoomInfo();
			confirm = readInputString("Press Y to confirm," + "N to discard and " + "(No.) to edit a field.")
					.toUpperCase().charAt(0);
			switch (confirm) {
			case 'Y':
				roomMrg.createRoom();
				break;
			case 'N':
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

	private void searchRoomMenu() {
		String i;
		do {
			System.out.println("Room System\n" + "0. Return to Main Menu\n" + "1. Search Room by Room number\n"
					+ "2. Search Room by Guest name");

			i = readInputString("Enter choice :");
			switch (i) {
			case "0":
				break;
			case "1":
				searchRoomByRoomNumMenu();
				break;
			case "2":
				searchRoomByGuestNameMenu();
				break;
			}
		} while (!i.equalsIgnoreCase("0"));
	}

	private void updateRoomMenu() {
		String i;
		do {
			System.out.println("Room System\n" + "0. Return to Main Menu\n" + "1. Update Room details\n"
					+ "2. Update Room Status");
			i = readInputString("Enter choice :");
			switch (i) {
			case "0":
				break;
			case "1":
				updateRoomBydetailsMenu();
				break;
			case "2":
				updateRoomStatusMenu();
				break;
			}
		} while (!i.equalsIgnoreCase("0"));
	}

	private void updateRoomBydetailsMenu() {
		String roomNum = readInputString("Enter room number : ");
		roomMrg.getRoomByRoomNum(roomNum);

		if (RoomMrg.checkRoomExist(roomNum)) {
			Character confirm;
			roomMrg.setRoomNumber(roomNum);
			do {
				roomMrg.printRoomInfo();
				confirm = readInputString("Press Y to confirm," + "N to discard and "
						+ "(No.) to edit a field.(Unable to edit Room Number and Room Status").toUpperCase().charAt(0);

				switch (confirm) {
				case 'Y':
					roomMrg.updateRoom();
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
		} else {
			System.out.println("There are no room existed by this room number");
		}

	}

	private void updateRoomStatusMenu() {
		String roomNum = readInputString("Enter room Number : ");
		if (RoomMrg.checkRoomExist(roomNum)) {
			Character confirm;
			String choice;
			roomMrg.setRoomNumber(roomNum);
			RoomStatus status = roomMrg.getRoomStatus();
			if (roomMrg.getRoomStatus().equals(RoomStatus.VACANT)|| 
					roomMrg.getRoomStatus().equals(RoomStatus.UNDER_MAINTENANCE)) {
				do {
				System.out.println("Enter new room status: ");
				choice = readInputString("1. VACANT\n2. UNDER_MAINTENANCE");
				switch (choice) {
				case "1":
					status = RoomStatus.VACANT;
					break;
				case "2":
					status = RoomStatus.UNDER_MAINTENANCE;
					break;
					default:
						break;
				}
				}while(!(choice.equalsIgnoreCase("1") || choice.equalsIgnoreCase("2")));
				roomMrg.setRoomStatus(status);

				do {
					roomMrg.printRoomInfo();
					confirm = readInputString("Press Y to confirm," + "N to discard").toUpperCase().charAt(0);
					switch (confirm) {
					case 'Y':
						roomMrg.updateRoom();
						break;
					case 'N':
						break;
					default:
						break;
					}
				} while (!(confirm.equals('Y') || confirm.equals('N')));
			} else {
				System.out.println("There are guests being assign to this room");
			}
		} else {
			System.out.println("There are no room existed by this room number");
		}
	}
	
	private void printRoomReportMenu() {
		String input;
		do {
		 input = readInputString("Print Room Status\n"
				+ "0. Return to Main Menu\n"
				+ "1. Print Room Type Occupancy Rate Report\n"
				+ "2. Print Room Report By Room Status");
		switch(input) {
		case "0":
			break;
		case "1":
			roomMrg.printRoomReport();
			break;
		case "2":
			roomMrg.printRoomStatusReport();
			break;
			default : break;
		}
		}while(!input.equalsIgnoreCase("0"));
	}
	
	private void searchRoomByRoomNumMenu() {
		String roomNum = readInputString("Enter room number :");
		if (RoomMrg.checkRoomExist(roomNum)) {
			roomMrg.setRoomNumber(roomNum);
			roomMrg.printRoomInfo();
		} else {
			System.out.println("Room does not exist");
		}

	}

	public void searchRoomByGuestNameMenu() {
		String name = readInputString("Enter guest name :");
		roomMrg.printRoomByGuestName(name);
	}

	private void enterRoomNum() {
		do {
			String roomNum = readInputString("Enter room number: ");
			if (roomNum.matches("^[0-9]*$")) {
				if (!RoomMrg.checkRoomExist(roomNum)) {
					roomMrg.setRoomNumber(roomNum);
					break;
				} else {
					System.out.println("Room number has already been used");
				}
			} else {
				System.out.println("Please enter room number in digits");
			}
		} while (true);
	}

	private void enterRoomType() {
		HashMap<String, String> enumData = getEnumTypeHashMap(RoomType.class);
		String roomType = readInputEnum("Enter room type: ", enumData);
		roomMrg.setRoomType(roomMrg.strToRoomType(roomType));
	}

	private void enterBedType() {
		HashMap<String, String> enumData = getEnumTypeHashMap(BedType.class);
		String bedType = readInputEnum("Enter Bed type: ", enumData);
		roomMrg.setBedType(roomMrg.strToBedType(bedType));

	}

	private void enterFacing() {
		HashMap<String, String> enumData = getEnumTypeHashMap(Facing.class);
		String facing = readInputEnum("Enter Bed type: ", enumData);
		roomMrg.setFacing(roomMrg.strToFacing(facing));

	}

	private void enterWeekdayRate() {
		Double weekdayRate = readInputDouble("Enter weekday rate: ");
		roomMrg.setRoomRateWeekday(weekdayRate);
	}

	private void enterWeekendRate() {

		Double weekendRate = readInputDouble("Enter weekend rate: ");
		roomMrg.setRoomRateWeekend(weekendRate);
		
	}

	private void enterAllowSmoking() {
		boolean bool = readInputBoolean("Enter allow Smoking (Y/N): ");
		roomMrg.setAllowSmoking(bool);
	}

	private void enterHasWifi() {
		boolean bool = readInputBoolean("Enter Has Wifi (Y/N): ");
		roomMrg.setHasWifi(bool);
	}

	public void loadData() {
		// TODO Auto-generated method stub
		try {
			roomMrg.loadRoomData();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}