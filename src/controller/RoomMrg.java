package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import entity.Guest;
import entity.Order;
import entity.Reservation;
import entity.Room;
import entity.Room.BedType;
import entity.Room.Facing;
import entity.Room.RoomStatus;
import entity.Room.RoomType;

public class RoomMrg {
	public static List<Room> rooms = new ArrayList<Room>();
	final static String fileName = "room_data.txt";

	public static RoomMrg getInstance() {
		return new RoomMrg();
	}

	public static Room createNewRoom() {
		return new Room();
	}

	public static RoomType strToRoomType(String type) {
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

	public static BedType strToBedType(String type) {
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

	public static Facing strToFacing(String strFacing) {
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

	public static RoomStatus strToRoomStatus(String strRoomStatus) {
		Room.RoomStatus roomStatus = null;
		if (strRoomStatus.equalsIgnoreCase("VACANT")) {
			roomStatus = Room.RoomStatus.VACANT;
		} else if (strRoomStatus.equalsIgnoreCase("OCCUPIED")) {
			roomStatus = Room.RoomStatus.OCCUPIED;
		} else if (strRoomStatus.equalsIgnoreCase("RESERVED")) {
			roomStatus = Room.RoomStatus.RESERVED;
		} else if (strRoomStatus.equalsIgnoreCase("UNDER MAINTENANCE")) {
			roomStatus = Room.RoomStatus.UNDER_MAINTENANCE;
		}
		return roomStatus;
	}

	public void createRoom(Room room) {
		rooms.add(room);
		for (Room s : rooms) {
			System.out.println(s.getRoomNumber());
		}
		try {
			writeRoomData();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean updateRoom(Room room) {
		boolean bool = false;
		for (Room r : rooms) {
			if (r.getRoomNumber() == room.getRoomNumber()) {
				r.setRoomType(room.getRoomType());
				r.setBedType(room.getBedType());
				r.setFacing(room.getFacing());
				r.setRoomRateWeekday(room.getRoomRateWeekday());
				r.setRoomRateWeekend(room.getRoomRateWeekend());
				r.setSmoking(room.isSmoking());
				r.setWifi(room.isWifi());
				bool = true;
			}
		}
		
		try {
			writeRoomData();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return bool;	
	}

	public void updateRoom(Room room, LocalDateTime checkInDate, LocalDateTime checkOutDate, String reservationCode,
			RoomStatus rs) {
		for (Room r : rooms) {
			if (r.equals(room)) {
				r.setCheckInDate(checkInDate);
				r.setCheckOutDate(checkOutDate);
				r.setRoomStatus(rs);
				r.setReservationCode(reservationCode);
			}
		}

		try {
			writeRoomData();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void checkInReservedRoom(Reservation reservation) {
		if (reservation.getRoomList().size() < 0) {
			for (String roomNum : reservation.getRoomList()) {
				for (Room r : rooms) {
					if (r.getRoomNumber().equalsIgnoreCase(roomNum)) {
						if (r.getRoomStatus().equals(Room.RoomStatus.RESERVED)) {
							r.setRoomStatus(Room.RoomStatus.OCCUPIED);
						}
					}
				}
			}
		}
		
		try {
			writeRoomData();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void cancelReservedRoom(Reservation reservation) {
		if (reservation.getRoomList().size() < 0) {
			for (String roomNum : reservation.getRoomList()) {
				for (Room r : rooms) {
					if (r.getRoomNumber().equalsIgnoreCase(roomNum)) {
						if (r.getRoomStatus().equals(Room.RoomStatus.RESERVED)) {
							r.setCheckInDate(null);
							r.setCheckOutDate(null);
							r.setRoomStatus(Room.RoomStatus.VACANT);
							r.setReservationCode(null);
						}
					}
				}
			}
		}
		
		try {
			writeRoomData();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public List<Room> searchRoomByGuestName(String name) {
		List<Room> roomList = new ArrayList<Room>();
		GuestMrg guestMrg = GuestMrg.getInstance();
		List<Guest> guestlist = guestMrg.searchGuestByName(name);
		for (Guest guest : guestlist) {
			System.out.println(guest.getIC());
			if(guest.getRoomNumList()!= null && guest.getRoomNumList().size()>0) {
				for(String roomNum : guest.getRoomNumList()) {
					roomList.add(searchRoomByNum(roomNum));
				}
			}
		}
		return roomList;
	}

	public List<Room> searchRoomByRoomType(RoomType roomType) {
		List<Room> roomList = new ArrayList<Room>();
		for (Room room : rooms) {
			if (room.getRoomType().equals(roomType)) {
				roomList.add(room);
			}
		}
		return roomList;
	}

	public List<Room> searchRoomByRoomStatus(RoomStatus roomStatus) {
		List<Room> roomList = new ArrayList<Room>();
		for (Room room : rooms) {
			if (room.getRoomStatus().equals(roomStatus)) {
				roomList.add(room);
			}
		}
		return roomList;
	}

	public Room searchRoomByNum(String roomNum) {
		Room r = null;
		for (Room room : rooms) {
			if (room.getRoomNumber().equalsIgnoreCase(roomNum)) {
				r = room;
			}
		}
		return r;
	}

	public List<Room> getAvailRoom(RoomStatus roomStatus, RoomType roomType, BedType bedType, boolean hasWifiBool,
			boolean allowSmokingBool) {
		List<Room> returnList = new ArrayList<Room>();
		for (Room room : rooms) {
			if (room.getRoomStatus().equals(roomStatus) && room.getRoomType().equals(roomType)
					&& room.getBedType().equals(bedType) && room.isWifi() == hasWifiBool
					&& room.isSmoking() == allowSmokingBool) {
				returnList.add(room);
			}
		}
		return returnList;
	}
	public double getRoomCharge(Room room) {
		  double price = 0;
	        double total_price = 0;
	        LocalDateTime checkinTime = room.getCheckInDate();
	        LocalDateTime checkOutTime = room.getCheckOutDate();
	        List<Integer> days = getDays(checkinTime, checkOutTime);
	        for (int day : days) {
	            if (day == 0 || day == 6) {
	                price = room.getRoomRateWeekend();
	            }
	            price = room.getRoomRateWeekday();
	            total_price += price;

	        }
	        return total_price;
	}
	
	   public static List<Integer> getDays(LocalDateTime checkin, LocalDateTime checkout) {
	        List<Integer> days = new ArrayList<Integer>();
	        long duration = Duration.between(checkin, checkout).toDays();
	        int checkin_ = checkin.getDayOfWeek().getValue();

	        for (int i = 0; i < duration; i++) {
	            days.add(checkin_);
	            checkin_ = (checkin_ + 1) % 7;
	        }
	        return days;
	    }
	
	// For Room Boundary
	public void getRoomReportMenu() {
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

	public void loadRoomData() throws FileNotFoundException {
		File file = new File(fileName);
		try {
			file.createNewFile();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		Scanner sc = new Scanner(file);
		String data;
		while (sc.hasNextLine()) {
			data = sc.nextLine();
			String[] temp = data.split(",");
			Room r = new Room();
			r.setRoomNumber(temp[0]);
			r.setRoomType(RoomMrg.strToRoomType(temp[1]));
			r.setBedType(RoomMrg.strToBedType(temp[2]));
			r.setFacing(RoomMrg.strToFacing(temp[3]));
			r.setRoomRateWeekday(Double.parseDouble(temp[4]));
			r.setRoomRateWeekend(Double.parseDouble(temp[5]));
			r.setWifi(Boolean.parseBoolean(temp[6]));
			r.setSmoking(Boolean.parseBoolean(temp[7]));
			r.setRoomStatus(RoomMrg.strToRoomStatus(temp[8]));
			if (r.getRoomStatus() == Room.RoomStatus.OCCUPIED || r.getRoomStatus() == Room.RoomStatus.RESERVED) {
				r.setReservationCode(temp[9]);

				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
				LocalDateTime CheckInDate = LocalDateTime.parse(temp[10], formatter);
				r.setCheckInDate(CheckInDate);
				LocalDateTime CheckOutDate = LocalDateTime.parse(temp[11], formatter);
				r.setCheckOutDate(CheckOutDate);
			}
			rooms.add(r);
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
				fileOut.print(room.isWifi() + ",");
				fileOut.print(room.isSmoking() + ",");
				fileOut.print(room.getRoomStatus() + ",");
				if (room.getRoomStatus() == Room.RoomStatus.OCCUPIED
						|| room.getRoomStatus() == Room.RoomStatus.RESERVED) {
					fileOut.print(room.getReservationCode() + ",");
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
					fileOut.print(room.getCheckInDate().format(formatter) + ",");
					fileOut.print(room.getCheckOutDate().format(formatter) + ",");
				}
				fileOut.println();
			}
			System.out.println("finish writing");
			fileOut.close();
		}
	}
}