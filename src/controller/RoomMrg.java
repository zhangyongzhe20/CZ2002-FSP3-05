import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    
  

    public static void creatRoom(){
    	Scanner sc = new Scanner(System.in);
    	Room r = new Room();
        System.out.println("Enter room number: ");
        r.setRoomNumber(rooms.size());
        
        System.out.println("Enter room type: SINGLE, DOUBLE, DELUXE, VIP  ");
        String roomType = sc.nextLine();
        r.setRoomType(RoomMrg.strToRoomType(roomType));
        
        System.out.println("Enter bed type: SINGLE, DOUBLE, KING ");
        String bedType = sc.nextLine();
        r.setBedType(RoomMrg.strToBedType(bedType));
        
        System.out.println("Enter facing: NORTH, SOUTH, EAST, WEST ");
        String facing = sc.nextLine();
        r.setFacing(RoomMrg.strToFacing(facing));
        
        r.setRoomStatus(Room.RoomStatus.VACANT);
        
        System.out.println("Enter weekday rate: ");
        r.setRoomRateWeekday(sc.nextDouble());
        System.out.println("Enter weekend rate: ");
        r.setRoomRateWeekend(sc.nextDouble());
        System.out.println("Enter allow smoking: ");
        r.setSmoking(sc.nextBoolean());
        System.out.println("Enter has wifi: ");
        r.setWifi(sc.nextBoolean());
        sc.close();
        
    }

    public static void updateRoom(){
    	
    }
    
    public static void searchRoom() {
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
    		roomList = RoomMrg.searchRoomByName(sc.nextLine());
    	case 2:
    		System.out.println("1. Enter Room Number: ");
    		roomList = RoomMrg.searchRoomByNum(sc.nextInt());
    	}
    	for(Room room : roomList) {
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
    	sc.close();
    }
    public static List<Room> searchRoomByName(String name){
    	List<Room> roomList = new ArrayList<Room>();
    	for(Room room : rooms) {
    		if(room.getGuest().getGuestName().equalsIgnoreCase(name)) {
    			roomList.add(room);
    		}
    	}
		return roomList;
    }

    public static  List<Room> searchRoomByNum(int roomNum){
    	List<Room> roomList = new ArrayList<Room>();
    	for(Room room : rooms) {
    		if(room.getRoomNumber() == roomNum) {
    			roomList.add(room);
    		}
    	}
		return roomList;
    }
    public static void checkIn(){
    	Scanner sc = new Scanner(System.in);
    	System.out.println(" -------------------------------------------");
    	System.out.println("1. Walk In");
    	System.out.println("2. Reservation");
    	System.out.println("Enter your choice: ");
    	int choice = sc.nextInt();
    	switch(choice) {
    	case 1:
    		
    	case 2:
    	}
    }
    
    public static void checkOut(){

    }

    public static void printBill(){

    }

    public static void printRoomInfo(){

    }
}