import java.util.Date;

public class Room {
	
    private RoomType roomType;
    private double roomRateWeekday;
    private double roomRateWeekend;
    private int roomNumber;
    private BedType bedType;
    private boolean isWifi;
    private Facing facing;
    private boolean isSmoking;
    private OrderMrg roomService;
    private Guest guest;
    private Date checkInDate;
    private Date checkOutDate;
    private RoomStatus roomStatus;
    
    
    enum RoomStatus {
    	VACANT, OCCUPIED, RESERVED, UNDER_MAINTENANCE
    }
    
    enum Facing {
    	NORTH, SOUTH, EAST, WEST
    }
    
    enum RoomType{
    	SINGLE, DOUBLE, DELUXE, VIP
    }
    
    enum BedType{
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


    /*
     * @param the roomService to set
     */
    public void setRoomService(OrderMrg roomService) {
        this.roomService = roomService;
    }

    /*
     * @param the guest to set
     */
    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    public RoomType getRoomType() {
        return roomType;
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

    public Guest getGuest() {
        return guest;
    }

	public Date getCheckInDate() {
		return checkInDate;
	}

	public void setCheckInDate(Date checkInDate) {
		this.checkInDate = checkInDate;
	}

	public Date getCheckOutDate() {
		return checkOutDate;
	}

	public void setCheckOutDate(Date checkOutDate) {
		this.checkOutDate = checkOutDate;
	}

	public RoomStatus getRoomStatus() {
		return this.roomStatus;
	}

	public void setRoomStatus(RoomStatus roomStatus) {
		this.roomStatus = roomStatus;
	}
    
    
}
