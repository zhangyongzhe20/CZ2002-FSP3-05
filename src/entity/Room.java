public class Room {
    private String roomType;
    private double roomRateWeekday;
    private double roomRateWeekend;
    private int roomNumber;
    private String bedType;
    private boolean isWifi;
    private String facing;
    private boolean isSmoking;
    private boolean isAvailable;
    private RoomService roomService;
    private Guest guest;

    public Room(String roomType, double roomRateWeekday, double roomRateWeekend, int roomNumber, String bedType, boolean isWifi, String facing, boolean isSmoking, boolean isAvailable) {
        this.roomType = roomType;
        this.roomRateWeekday = roomRateWeekday;
        this.roomRateWeekend = roomRateWeekend;
        this.roomNumber = roomNumber;
        this.bedType = bedType;
        this.isWifi = isWifi;
        this.facing = facing;
        this.isSmoking = isSmoking;
        this.isAvailable = isAvailable;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
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

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getBedType() {
        return bedType;
    }

    public void setBedType(String bedType) {
        this.bedType = bedType;
    }

    public boolean isWifi() {
        return isWifi;
    }

    public void setWifi(boolean wifi) {
        isWifi = wifi;
    }

    public String getFacing() {
        return facing;
    }

    public void setFacing(String facing) {
        this.facing = facing;
    }

    public boolean isSmoking() {
        return isSmoking;
    }

    public void setSmoking(boolean smoking) {
        isSmoking = smoking;
    }

    public boolean isAvailable(int roomNumber) {
        return isAvailable;
    }

    public boolean isAvailable(String guestName) {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public Guest getGuest() {
        return guest;
    }
}
