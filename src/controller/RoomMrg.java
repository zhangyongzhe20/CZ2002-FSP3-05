package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import entity.Guest;
import entity.Reservation;
import entity.Room;
import entity.Room.BedType;
import entity.Room.Facing;
import entity.Room.RoomStatus;
import entity.Room.RoomType;

public class RoomMrg {
	public static List<Room> rooms = new ArrayList<Room>();
	final static String fileName = "room_data.txt";

	public static RoomType strToRoomType(String type) {
		Room.RoomType roomtype = null;
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
		Room.BedType bedType = null;
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

	public static Room.RoomStatus strToRoomStatus(String strRoomStatus) {
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

	public static void createRoom(Room room) {
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

	public static boolean updateRoom(Room room) {
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
		return bool;
	}

	public static void updateRoom(Room room, LocalDateTime checkoutDate, LocalDateTime checkInDate, String nric,
			Room.RoomStatus rs) {
		for (Room r : rooms) {
			if (r.equals(room)) {
				r.setCheckInDate(checkInDate);
				r.setCheckOutDate(checkoutDate);
				r.setRoomStatus(rs);
				r.setGuestIC(nric);
			}
		}

		try {
			writeRoomData();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void checkInReservedRoom(Reservation reservation) {
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
	}

	public static void cancelReservedRoom(Reservation reservation) {
		if (reservation.getRoomList().size() < 0) {
			for (String roomNum : reservation.getRoomList()) {
				for (Room r : rooms) {
					if (r.getRoomNumber().equalsIgnoreCase(roomNum)) {
						if (r.getRoomStatus().equals(Room.RoomStatus.RESERVED)) {
							r.setCheckInDate(null);
							r.setCheckOutDate(null);
							r.setRoomStatus(Room.RoomStatus.VACANT);
							r.setGuestIC(null);
						}
					}
				}
			}
		}
	}

	public static List<Room> searchRoomByGuestName(String name) {
		List<Room> roomList = new ArrayList<Room>();
		List<Guest> guestlist = GuestMrg.searchGuestByName(name);
		for (Guest guest : guestlist) {
			for (Room room : rooms) {
				if (room.getGuestIC().equalsIgnoreCase(guest.getIC())) {
					roomList.add(room);
				}
			}
		}
		return roomList;
	}

	public static List<Room> searchRoomByRoomType(String StrRoomType) {
		Room.RoomType roomType = RoomMrg.strToRoomType(StrRoomType);
		List<Room> roomList = new ArrayList<Room>();
		for (Room room : rooms) {
			if (room.getRoomType().equals(roomType)) {
				roomList.add(room);
			}
		}
		return roomList;
	}

	public static Room searchRoomByNum(String roomNum) {
		Room r = null;
		for (Room room : rooms) {
			if (room.getRoomNumber().equalsIgnoreCase(roomNum)) {
				r = room;
			}
		}
		return r;
	}

	public static void checkOut() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Please enter the room number: ");
		String roomNum = sc.nextLine();
		Room room = RoomMrg.searchRoomByNum(roomNum);
		if (room != null) {
			if (room.getRoomStatus().equals(Room.RoomStatus.OCCUPIED)) {
				RoomMrg.updateRoom(room, null, null, null, Room.RoomStatus.VACANT);
			} else {
				System.out.println("Room is not occupied");
			}
		} else {
			System.out.println("Room not found");
		}

		sc.close();
	}

	public static void loadRoomData() throws FileNotFoundException {
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
				r.setGuestIC((temp[9]));

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

	public static void writeRoomData() throws IOException {
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
					fileOut.print(room.getGuestIC() + ",");
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