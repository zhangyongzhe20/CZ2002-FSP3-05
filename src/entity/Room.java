package entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class  Room{
	
	private String roomNumber;
    private RoomType roomType;
    private BedType bedType;
    private Facing facing;
    private double roomRateWeekday;
    private double roomRateWeekend;
    private boolean hasWifi;
    private boolean allowSmoking;
    private RoomStatus roomStatus;
    
    
    public enum RoomStatus {
    	VACANT, OCCUPIED, RESERVED, UNDER_MAINTENANCE
    	
    	
    }
    
    public enum Facing {
    	NORTH, SOUTH, EAST, WEST
    }
    
    public enum RoomType{
    	SINGLE, DOUBLE, DELUXE, VIP
    }
    
    public enum BedType{
    	SINGLE, DOUBLE, KING
    }
    
 
    
	public RoomType getRoomType() {
		return roomType;
	}



	public void setRoomType(RoomType roomType) {
		this.roomType = roomType;
	}



	public double getRoomRateWeekday() {
		return roomRateWeekday;
	}



	public void setRoomRateWeekday(double roomRateWeekday) {
		this.roomRateWeekday = roomRateWeekday;
	}



	public double getRoomRateWeekend() {
		return roomRateWeekend;
	}



	public void setRoomRateWeekend(double roomRateWeekend) {
		this.roomRateWeekend = roomRateWeekend;
	}



	public String getRoomNumber() {
		return roomNumber;
	}



	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}



	public BedType getBedType() {
		return bedType;
	}



	public void setBedType(BedType bedType) {
		this.bedType = bedType;
	}



	public boolean getHasWifi() {
		return hasWifi;
	}



	public void setHasWifi(boolean hasWifi) {
		this.hasWifi = hasWifi;
	}



	public Facing getFacing() {
		return facing;
	}



	public void setFacing(Facing facing) {
		this.facing = facing;
	}



	public boolean getAllowSmoking() {
		return allowSmoking;
	}



	public void setAllowSmoking(boolean allowSmoking) {
		this.allowSmoking = allowSmoking;
	}

	public RoomStatus getRoomStatus() {
		return roomStatus;
	}



	public void setRoomStatus(RoomStatus roomStatus) {
		this.roomStatus = roomStatus;
	}



	public void printRoomInfo() {
		System.out.println("-------------------------------------------");
		System.out.println("1.Room No: " + this.getRoomNumber());
		System.out.println("2.Room Type: " + this.getRoomType());
		System.out.println("3.Bed Type: " + this.getBedType());
		System.out.println("4.Room Facing: " + this.getFacing());
		System.out.println("5.Weekday Rate: $" + String.format("%.2f", this.getRoomRateWeekday()));
		System.out.println("6.Weekend Rate: $" + String.format("%.2f", this.getRoomRateWeekend()));
		System.out.println("7.Allowing Smoking: " + this.getAllowSmoking());
		System.out.println("8.Has Wifi: " + this.getHasWifi());
		System.out.println("9.Room Status: " + this.getRoomStatus());
	}
    
}