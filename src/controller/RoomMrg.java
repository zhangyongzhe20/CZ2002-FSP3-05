import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;



public class RoomMrg {
    public static List<Room> rooms;
  
    
    public static Room.RoomType strToRoomType(String type) {
    	Room.RoomType roomtype = null;
    	if(type.equalsIgnoreCase("SINGLE")) {
    		roomtype = Room.RoomType.SINGLE;
        }else if(type.equalsIgnoreCase("DOUBLE")) {
        	roomtype = Room.RoomType.DOUBLE;
        }else if(type.equalsIgnoreCase("DELUXE")) {
        	roomtype = Room.RoomType.DELUXE;
        }else if(type.equalsIgnoreCase("VIP")) {
        	roomtype = Room.RoomType.VIP;
        }
    	return roomtype;
    }
    
    public static Room.BedType strToBedType(String type) {
    	Room.BedType bedType = null;
    	if(type.equalsIgnoreCase("SINGLE")) {
    		bedType = Room.BedType.SINGLE;
        }else if(type.equalsIgnoreCase("DOUBLE")) {
        	bedType = Room.BedType.DOUBLE;
        }else if(type.equalsIgnoreCase("KING")) {
        	bedType = Room.BedType.KING;
        }
    	return bedType;
    }
    
    public static Room.Facing strToFacing(String strFacing) {
    	Room.Facing facing = null;
    	if(strFacing.equalsIgnoreCase("NORTH")) {
    		facing = Room.Facing.NORTH;
        }else if(strFacing.equalsIgnoreCase("EAST")) {
        	facing = Room.Facing.EAST;
        }else if(strFacing.equalsIgnoreCase("SOUTH")) {
        	facing = Room.Facing.SOUTH;
        }else if(strFacing.equalsIgnoreCase("WEST")) {
        	facing = Room.Facing.WEST;
        }
    	return facing;
    }
    
    public static Room.RoomStatus strToRoomStatus(String strRoomStatus) {
    	Room.RoomStatus roomStatus = null;
    	if(strRoomStatus.equalsIgnoreCase("VACANT")) {
    		roomStatus = Room.RoomStatus.VACANT;
        }else if(strRoomStatus.equalsIgnoreCase("OCCUPIED")) {
        	roomStatus = Room.RoomStatus.OCCUPIED;
        }else if(strRoomStatus.equalsIgnoreCase("RESERVED")) {
        	roomStatus = Room.RoomStatus.RESERVED;
        }else if(strRoomStatus.equalsIgnoreCase("UNDER MAINTENANCE")) {
        	roomStatus = Room.RoomStatus.UNDER_MAINTENANCE;
        }
    	return roomStatus;
    }
    

    public static void creatRoom(String roomType, String bedType,String facing , Double weekdayRate , Double weekendRate , boolean allowSmoking , boolean hasWifi){   	
    	Room r = new Room();
        r.setRoomNumber(rooms.size());  
        
        r.setRoomType(RoomMrg.strToRoomType(roomType));
        r.setBedType(RoomMrg.strToBedType(bedType));
        r.setFacing(RoomMrg.strToFacing(facing));
        r.setRoomStatus(Room.RoomStatus.VACANT);            
        r.setRoomRateWeekday(weekdayRate);
        r.setRoomRateWeekend(weekendRate);
        r.setSmoking(allowSmoking);
        r.setWifi(hasWifi);
        rooms.add(r);
    }

    public static void updateRoom(Room room , LocalDateTime checkoutDate , LocalDateTime checkInDate , String nric , Room.RoomStatus rs){
    	for(Room r : rooms) {
    		if(r.equals(room)) {
    			r.setCheckInDate(checkInDate);
    			r.setCheckOutDate(checkoutDate);
    			r.setRoomStatus(rs);
    			r.setGuestIC(nric);
    		}
    	}
    }
    
    public static void updateRoom(Reservation reservation , Room.RoomStatus roomStatus){
    	if(roomStatus.equals(Room.RoomStatus.VACANT)) {
    	if(reservation.getRoomList().size() < 0) {
    		for(int roomNum : reservation.getRoomList()) {
    			for(Room r : rooms) {
    				if(r.getRoomNumber() == roomNum) {
    					r.setCheckInDate(reservation.getCheckIn());
    	    			r.setCheckOutDate(reservation.getCheckOut());
    	    			r.setRoomStatus(Room.RoomStatus.OCCUPIED);
    	    			r.setGuestIC(reservation.getGuestIC());
    				}
    			}
    		}
    	}
    	}else if(roomStatus.equals(Room.RoomStatus.RESERVED)){
        	if(reservation.getRoomList().size() < 0) {
        		for(int roomNum : reservation.getRoomList()) {
        			for(Room r : rooms) {
        				if(r.getRoomNumber() == roomNum) {
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
    
   /* public static void searchRoom() {
    	Scanner sc = new Scanner(System.in);
    	List<Room> roomList = new ArrayList<Room>();
    	System.out.println(" -------------------------------------------");
    	System.out.println("1. Search By Guest Name");
    	System.out.println("2. Search By Room Number");
    	System.out.println("Enter your choice: ");
    	int choice = sc.nextInt();
    	switch(choice) {
    	case 1:
    		System.out.println("1. Enter Guest Name: ");
    		roomList = RoomMrg.searchRoomByGuestName(sc.nextLine());
    	case 2:
    		System.out.println("1. Enter Room Number: ");
    		roomList.add(RoomMrg.searchRoomByNum(sc.nextInt()));
    	}
    	for(Room room : roomList) {
    		RoomMrg.printRoomInfo(room);
    	}
    	sc.close();
    }*/
    public static List<Room> searchRoomByGuestName(String name){
    	List<Room> roomList = new ArrayList<Room>();
    	List<Guest> guestlist = GuestMrg.searchGuestByName(name);
    	for(Guest guest : guestlist) {
    		for(Room room : rooms) {
    			if(room.getGuestIC().equalsIgnoreCase(guest.getIC())) {
    				roomList.add(room);
    			}
    		}
    	}
		return roomList;
    }
    public static List<Room> searchRoomByRoomType(String StrRoomType){
    	Room.RoomType roomType = RoomMrg.strToRoomType(StrRoomType);
    	List<Room> roomList = new ArrayList<Room>();
    	for(Room room : rooms) {
    		if(room.getRoomType().equals(roomType)) {
    			roomList.add(room);
    		}
    	}
		return roomList;
    }
    public static  Room searchRoomByNum(int roomNum){
    	Room r = null;
    	for(Room room : rooms) {
    		if(room.getRoomNumber() == roomNum) {
    		   r = room;
    		}
    	}
		return r;
    }
 /*   public static void checkIn(){
    	Scanner sc = new Scanner(System.in);
    	System.out.println(" -------------------------------------------");
    	System.out.println("1. Walk In");
    	System.out.println("2. Reservation");
    	System.out.println("Enter your choice: ");
    	int choice = sc.nextInt();
    	switch(choice) {
    	case 1:
    		RoomMrg.WalkIncheckIn();
    	case 2:
    		RoomMrg.reservationCheckIn();
    	default:
    		System.out.println("Please enter the correct input by selecting 1 or 2");
    	}
    	sc.close();
    }*/
    
    public static void WalkIncheckIn()  {
    	Scanner sc = new Scanner(System.in);
    	System.out.println("Enter the guest IC: ");
    	String ic = sc.nextLine();
        Guest g = GuestMrg.searchGuestByIC(ic);
        if(g != null) {
        	LocalDateTime checkOut = LocalDateTime.now(); 
        	LocalDateTime checkIn = LocalDateTime.now(); 
        	do {
        	System.out.println("Enter the check out date in (DD/MM/YYY)");
        	DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");  
        	checkOut = LocalDateTime.parse(sc.nextLine(), format);
        	}while(checkOut.isAfter(checkIn));
        	
        	System.out.println("Enter Room Type : SINGLE, DOUBLE, DELUXE, VIP  ");
        	List<Room> availableRoom = RoomMrg.searchRoomByRoomType(sc.nextLine().toUpperCase());
        	
        	
        	
        	int choice;
        	do {
        	System.out.println(" -------------------------------------------");
        	for(Room room : availableRoom) {
        		if(!room.getRoomStatus().equals(Room.RoomStatus.VACANT)) {
        			availableRoom.remove(room);
        		} else {
        			System.out.println(room.getRoomNumber());
        			}
        		}
        	System.out.println("Enter Room Number : ");
        	int roomNum = sc.nextInt();
        	Room selectedRoom = searchRoomByNum(roomNum);
        	RoomMrg.printRoomInfo(selectedRoom);
        	
        	System.out.println(" -------------------------------------------");
        	System.out.println("1. Confirm");
        	System.out.println("2. Back");
        	System.out.println("Enter your choice: ");
        	choice = sc.nextInt();
        	
        	if(choice == 1) {
        		RoomMrg.updateRoom(selectedRoom ,checkOut , checkIn , ic, Room.RoomStatus.OCCUPIED);
        	}
        	}while(choice == 2);
        }
        sc.close();
    }
    
    public static void reservationCheckIn() {
    	Scanner sc = new Scanner("System.in");
    	System.out.println("Please enter the reservation code: ");
    	String reservationCode = sc.nextLine();
    	Reservation reservation = ReservationMrg.getReservationByCode(reservationCode);
    	if(reservation != null) {
    		if(reservation.getReservationStatus().equals(Reservation.ReservationStatus.CONFIRMED)) {
    			RoomMrg.updateRoom(reservation , Room.RoomStatus.VACANT);
    			System.out.println("Sucessfully check in to the room");
    		}else {
    			System.out.println("Reservation is on "+ reservation.getReservationStatus());
    		}
    	}else {
    		System.out.println("Reservation not found");
    	}
    	sc.close();
    }
    
    public static void checkOut(){
    	Scanner sc = new Scanner(System.in);
    	System.out.println("Please enter the room number: ");
    	int roomNum = sc.nextInt();
    	Room room = RoomMrg.searchRoomByNum(roomNum);
    	if(room != null) {
    		if(room.getRoomStatus().equals(Room.RoomStatus.OCCUPIED)) {
    			RoomMrg.updateRoom(room ,null , null , null, Room.RoomStatus.VACANT);
    		}else {
    		 System.out.println("Room is not occupied");
    		}
    	}else {
    		System.out.println("Room not found");
    	}
    	
    	sc.close();
    }



    public static void printRoomInfo(Room room){
    	System.out.println(" -------------------------------------------");
		System.out.println("Room No: " + room.getRoomNumber());
		System.out.println("Room Type: " + room.getRoomType());
		System.out.println("Bed Type: " + room.getBedType());
		System.out.println("Room Facing: " + room.getFacing());
		System.out.println("Weekday Rate: $" + room.getRoomType());
		System.out.println("Weekend Rate: $" + room.getRoomType());
		System.out.println("Allowing Smoking: " + room.getRoomType());
		System.out.println("Has Wifi: " + room.getRoomType());
		System.out.println("Room Status: " + room.getRoomStatus());
		
		if(room.getRoomStatus().equals(Room.RoomStatus.OCCUPIED)) {
		SimpleDateFormat ft = new SimpleDateFormat ("dd/MM/yyyy");
		System.out.println("Check in Date: " + ft.format(room.getCheckInDate()));
		System.out.println("Check out Date: " + ft.format(room.getCheckOutDate()));
		}
		System.out.println(" -------------------------------------------");
    }
}