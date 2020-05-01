package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import entity.Room;
import entity.Room.BedType;
import entity.Room.Facing;
import entity.Room.RoomStatus;
import entity.Room.RoomType;

public class RoomMrg {
	private static List<Room> rooms;
	private final static String fileName = "room_data.txt";
	private Room room;

	    /**
     * Applied Singelton Desgin Pattern in Mrg classes
     */
    private static RoomMrg SINGLE_INSTANCE;
    public static RoomMrg getInstance() {
        if (SINGLE_INSTANCE == null) {
            SINGLE_INSTANCE = new RoomMrg();
        }
        return SINGLE_INSTANCE;
    }

    public RoomMrg() {
		rooms = new ArrayList<Room>();
	}
	

	public void createNewRoom() {
		room = new Room();
	}

	public void setRoomNumber(String roomNum) {
		if (checkRoomExist(roomNum)) {
			room = getRoomByRoomNum(roomNum);
		} else {
			room.setRoomNumber(roomNum);
		}
	}

	public void setRoomType(RoomType roomType) {
		room.setRoomType(roomType);
	}

	public void setBedType(BedType bedType) {
		room.setBedType(bedType);
	}

	public void setFacing(Facing facing) {
		room.setFacing(facing);
	}

	public void setRoomRateWeekday(double roomRateWeekday) {
		room.setRoomRateWeekday(roomRateWeekday);
	}

	public void setRoomRateWeekend(double roomRateWeekend) {
		room.setRoomRateWeekend(roomRateWeekend);
	}

	public void setHasWifi(boolean hasWifi) {
		room.setHasWifi(hasWifi);
	}

	public void setAllowSmoking(boolean allowSmoking) {
		room.setAllowSmoking(allowSmoking);
	}

	public void setRoomStatus(RoomStatus roomStatus) {
		room.setRoomStatus(roomStatus);
	}

	public RoomStatus getRoomStatus() {
		return room.getRoomStatus();
	}

	public RoomType strToRoomType(String type) {
		RoomType roomtype = null;
		if (type.equalsIgnoreCase("SINGLE")) {
			roomtype = Room.RoomType.SINGLE;
		} else if (type.equalsIgnoreCase("DOUBLE")) {
			roomtype = Room.RoomType.DOUBLE;
		} else if (type.equalsIgnoreCase("DELUXE")) {
			roomtype = Room.RoomType.DELUXE;
		} else if (type.equalsIgnoreCase("VIP")) {
			roomtype = Room.RoomType.VIP;
		}
		return roomtype;
	}

	public BedType strToBedType(String type) {
		BedType bedType = null;
		if (type.equalsIgnoreCase("SINGLE")) {
			bedType = Room.BedType.SINGLE;
		} else if (type.equalsIgnoreCase("DOUBLE")) {
			bedType = Room.BedType.DOUBLE;
		} else if (type.equalsIgnoreCase("KING")) {
			bedType = Room.BedType.KING;
		}
		return bedType;
	}

	public Facing strToFacing(String strFacing) {
		Facing facing = null;
		if (strFacing.equalsIgnoreCase("NORTH")) {
			facing = Room.Facing.NORTH;
		} else if (strFacing.equalsIgnoreCase("EAST")) {
			facing = Room.Facing.EAST;
		} else if (strFacing.equalsIgnoreCase("SOUTH")) {
			facing = Room.Facing.SOUTH;
		} else if (strFacing.equalsIgnoreCase("WEST")) {
			facing = Room.Facing.WEST;
		}
		return facing;
	}

	public RoomStatus strToRoomStatus(String strRoomStatus) {
		Room.RoomStatus roomStatus = null;
		if (strRoomStatus.equalsIgnoreCase("VACANT")) {
			roomStatus = Room.RoomStatus.VACANT;
		} else if (strRoomStatus.equalsIgnoreCase("OCCUPIED")) {
			roomStatus = Room.RoomStatus.OCCUPIED;
		} else if (strRoomStatus.equalsIgnoreCase("RESERVED")) {
			roomStatus = Room.RoomStatus.RESERVED;
		} else if (strRoomStatus.equalsIgnoreCase("UNDER_MAINTENANCE")) {
			roomStatus = Room.RoomStatus.UNDER_MAINTENANCE;
		}
		return roomStatus;
	}

	// modify to apply in Order Boundary
	public static boolean checkRoomExist(String roomNum) {
		for (Room room : rooms) {
			if (room.getRoomNumber().equalsIgnoreCase(roomNum)) {
				return true;
			}
		}
		return false;
	}

	public void createRoom() {
		rooms.add(room);
		try {
			writeRoomData();
			System.out.println("Room is created successfully!");
			System.out.println("-------------------------------------------");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void updateRoom() {
		try {
			writeRoomData();
			System.out.println("Room is updated successfully!");
			System.out.println("-------------------------------------------");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void updateRoomStatus(String roomNum, RoomStatus rs) {
		room = getRoomByRoomNum(roomNum);
		setRoomStatus(rs);
		try {
			writeRoomData();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public List<Room> getRoomByGuestName(String name) {
		List<Room> roomList = new ArrayList<Room>();
		List<String> guestICList = GuestMrg.getInstance().getGuestsICByGuestName(name);
		for (String ic : guestICList) {
				String roomNum = ReservationMrg.getInstance().getReservationRoomByIC(ic);
				if(roomNum != null) {
					roomList.add(getRoomByRoomNum(roomNum));
				}
		}
		return roomList;
	}

	public List<Room> getRoomByRoomType(RoomType roomType) {
		List<Room> roomList = new ArrayList<Room>();
		for (Room room : rooms) {
			if (room.getRoomType().equals(roomType)) {
				roomList.add(room);
			}
		}
		return roomList;
	}

	public List<Room> getRoomByRoomStatus(RoomStatus roomStatus) {
		List<Room> roomList = new ArrayList<Room>();
		for (Room room : rooms) {
			if (room.getRoomStatus().equals(roomStatus)) {
				roomList.add(room);
			}
		}
		return roomList;
	}

	public Room getRoomByRoomNum(String roomNum) {
		Room r = null;
		for (Room room : rooms) {
			if (room.getRoomNumber().equalsIgnoreCase(roomNum)) {
				r = room;
			}
		}
		return r;
	}

	public double getRoomCharge(String roomNum,LocalDateTime checkInDate, LocalDateTime checkOutDate) {
		room = getRoomByRoomNum(roomNum);
		double price = 0;
		double total_price = 0;

		List<Integer> days = new ArrayList<Integer>();
		long duration = Duration.between(checkInDate, checkOutDate).toDays() + 1;
		int checkin_ = checkInDate.getDayOfWeek().getValue();

		for (int i = 0; i < duration; i++) {
			days.add(checkin_);
			checkin_ = (checkin_ + 1) % 7;
		}

		for (int day : days) {
			if (day == 0 || day == 6) {
				price = room.getRoomRateWeekend();
			} else {
				price = room.getRoomRateWeekday();
			}
			total_price += price;

		}
		return total_price;
	}

	public List<String> getAndPrintAvailRoomNum(RoomType roomType, BedType bedType, boolean hasWifiBool,
			boolean allowSmokingBool) {
		List<String> roomNumList = new ArrayList<String>();
		for (Room room : rooms) {
			if (room.getRoomType().equals(roomType) && room.getBedType().equals(bedType)
					&& room.getHasWifi() == hasWifiBool && room.getAllowSmoking() == allowSmokingBool) {
				if (room.getRoomStatus().equals(Room.RoomStatus.VACANT)) {
					System.out.println("Room Number: " + room.getRoomNumber() + ", Weekday Rate: "
							+ String.format("%.2f", room.getRoomRateWeekday()) + ", Weekend Rate: "
							+ String.format("%.2f", room.getRoomRateWeekend()) + ", Facing: " + room.getFacing());
					roomNumList.add(room.getRoomNumber());
				}
			}
		}
		if (roomNumList.size() == 0) {
			System.out.println("There are no available room");
		}
		return roomNumList;
	}

	// For Room Boundary
	public void printRoomReport() {
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

		for (Room r : rooms) {
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
		System.out.println("-------------------------------------------");
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
		System.out.println("-------------------------------------------");
	}

	public void printRoomNumber(List<Room> list) {
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

	public void printRoomInfo() {
		room.printRoomInfo();
	}

	public void printRoomByGuestName(String name) {
		List<Room> roomList = getRoomByGuestName(name);
		if (roomList.size() > 0) {
			for (Room r : roomList) {
				r.printRoomInfo();
			}
		} else {
			System.out.println("No room found by the name " + name);
		}
	}

	public void printRoomByRoomNumber(String roomNum) {
	  room = getRoomByRoomNum(roomNum);
	  if(room !=null) {
	  printRoomInfo();
	  }else {
		  System.out.println("Unable to find such room");
	  }
	}
	public void printRoomStatusReport() {
		List<Room> vacantList = new ArrayList<Room>();
		List<Room> occupiedList = new ArrayList<Room>();
		List<Room> reservedList = new ArrayList<Room>();
		List<Room> maintenanceList = new ArrayList<Room>();
		System.out.println("-------------------------------------------");
		System.out.println("Room status");

		for (Room r : rooms) {
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
		printRoomNumber(occupiedList);

		System.out.println("RESERVED: ");
		System.out.print("	Room :");
		printRoomNumber(reservedList);

		System.out.println("UNDER_MAINTENANCE: ");
		System.out.print("	Room :");
		printRoomNumber(maintenanceList);
		System.out.println("-------------------------------------------");
	}
	public void loadRoomData() throws FileNotFoundException {
		File file = new File(fileName);
		try {
			file.createNewFile();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		Scanner sc = new Scanner(file);
		String data;
		if (sc.hasNextLine()) {
			while (sc.hasNextLine()) {
				data = sc.nextLine();
				if(!data.isEmpty()){
				String[] temp = data.split(",");
				Room r = new Room();
				r.setRoomNumber(temp[0]);
				r.setRoomType(strToRoomType(temp[1]));
				r.setBedType(strToBedType(temp[2]));
				r.setFacing(strToFacing(temp[3]));
				r.setRoomRateWeekday(Double.parseDouble(temp[4]));
				r.setRoomRateWeekend(Double.parseDouble(temp[5]));
				r.setHasWifi(Boolean.parseBoolean(temp[6]));
				r.setAllowSmoking(Boolean.parseBoolean(temp[7]));
				r.setRoomStatus(strToRoomStatus(temp[8]));
				rooms.add(r);
				}
			}
		} else {
			for (int i = 2; i < 8; i++) {
				for (int j = 1; j < 9; j++) {
					Room r = new Room();
					if (j < 10) {
						r.setRoomNumber("0" + i + "0" + j);
					} else {
						r.setRoomNumber("0" + i + j);
					}

					Random generator = new Random();

					switch (generator.nextInt(4)) {
					case 0:
						r.setRoomType(RoomType.SINGLE);
						break;
					case 1:
						r.setRoomType(RoomType.DOUBLE);
						break;
					case 2:
						r.setRoomType(RoomType.DELUXE);
						break;
					case 3:
						r.setRoomType(RoomType.VIP);
						break;
					}

					switch (generator.nextInt(3)) {
					case 0:
						r.setBedType(BedType.SINGLE);
						break;
					case 1:
						r.setBedType(BedType.DOUBLE);
						break;
					case 2:
						r.setBedType(BedType.KING);
						break;
					}

					switch (generator.nextInt(4)) {
					case 0:
						r.setFacing(Facing.NORTH);
						break;
					case 1:
						r.setFacing(Facing.EAST);
						break;
					case 2:
						r.setFacing(Facing.WEST);
						break;
					case 3:
						r.setFacing(Facing.SOUTH);
						break;
					}

					r.setRoomRateWeekday(generator.nextInt(400) + 100);
					r.setRoomRateWeekend(r.getRoomRateWeekday() + 20);

					switch (generator.nextInt(2)) {
					case 0:
						r.setHasWifi(true);
						break;
					case 1:
						r.setHasWifi(false);
						break;
					}
					switch (generator.nextInt(2)) {
					case 0:
						r.setAllowSmoking(true);
						break;
					case 1:
						r.setAllowSmoking(false);
						break;
					}
					r.setRoomStatus(RoomStatus.VACANT);
					rooms.add(r);
				}
			}
			try {
				writeRoomData();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		sc.close();
	}

	public void writeRoomData() throws IOException {
		FileWriter fileWriter = new FileWriter(fileName);
		PrintWriter fileOut = new PrintWriter(fileWriter);
		if (rooms.size() > 0) {
			for (Room room : rooms) {
				fileOut.print(room.getRoomNumber() + ",");
				fileOut.print(room.getRoomType() + ",");
				fileOut.print(room.getBedType() + ",");
				fileOut.print(room.getFacing() + ",");
				fileOut.print(room.getRoomRateWeekday() + ",");
				fileOut.print(room.getRoomRateWeekend() + ",");
				fileOut.print(room.getHasWifi() + ",");
				fileOut.print(room.getAllowSmoking() + ",");
				fileOut.print(room.getRoomStatus() + ",");
				fileOut.println();
			}
			fileOut.close();
		}
	}

}