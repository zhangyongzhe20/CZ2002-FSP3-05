package entity;

import java.time.LocalDateTime;


public class Room {
	
    private RoomType roomType;
    private double roomRateWeekday;
    private double roomRateWeekend;
    private int roomNumber;
    private BedType bedType;
    private boolean isWifi;
    private Facing facing;
    private boolean isSmoking;
    private String guestIC;
    private LocalDateTime checkInDate;
    private LocalDateTime checkOutDate;
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
    
    /*
     * @param the roomType to set
     */
    
    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    /*
     * @param the roomRateWeekday to set
     */
    public void setRoomRateWeekday(double roomRateWeekday) {
        this.roomRateWeekday = roomRateWeekday;
    }

    /*
     * @param the roomRateWeekend to set
     */
    public void setRoomRateWeekend(double roomRateWeekend) {
        this.roomRateWeekend = roomRateWeekend;
    }

    /*
     * @param the roomNumber to set
     */
    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    /*
     * @param the bedType to set
     */
    public void setBedType(BedType bedType) {
        this.bedType = bedType;
    }

    /*
     * @param the wifi to set
     */
    public void setWifi(boolean wifi) {
        isWifi = wifi;
    }

    /*
     * @param the facing to set
     */
    public void setFacing(Facing facing) {
        this.facing = facing;
    }

    /*
     * @param the smoking to set
     */
    public void setSmoking(boolean smoking) {
        isSmoking = smoking;
    }


    public RoomType getRoomType() {
        return roomType;
    }

    public String getGuestIC() {
		return guestIC;
	}

	public void setGuestIC(String guestIC) {
		this.guestIC = guestIC;
	}

	public double getRoomRateWeekday() {
        return roomRateWeekday;
    }

    public double getRoomRateWeekend() {
        return roomRateWeekend;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public BedType getBedType() {
        return bedType;
    }

    public boolean isWifi() {
        return isWifi;
    }

    public Facing getFacing() {
        return facing;
    }

    public boolean isSmoking() {
        return isSmoking;
    }



    public OrderMrg getRoomService() {
        return roomService;
    }


	public LocalDateTime getCheckInDate() {
		return checkInDate;
	}

	public void setCheckInDate(LocalDateTime checkInDate) {
		this.checkInDate = checkInDate;
	}

	public LocalDateTime getCheckOutDate() {
		return checkOutDate;
	}

	public void setCheckOutDate(LocalDateTime checkOutDate) {
		this.checkOutDate = checkOutDate;
	}

	public RoomStatus getRoomStatus() {
		return this.roomStatus;
	}

	public void setRoomStatus(RoomStatus roomStatus) {
		this.roomStatus = roomStatus;
	}
    
    
}
