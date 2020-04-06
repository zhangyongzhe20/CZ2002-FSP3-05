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
    private OrderMrg roomService;
    private Guest guest;

    /*
     * @param the roomType to set
     */
    public void setRoomType(String roomType) {
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
    public void setBedType(String bedType) {
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
    public void setFacing(String facing) {
        this.facing = facing;
    }

    /*
     * @param the smoking to set
     */
    public void setSmoking(boolean smoking) {
        isSmoking = smoking;
    }

    /*
     * @param the available to set
     */
    public void setAvailable(boolean available) {
        isAvailable = available;
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

    public String getRoomType() {
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

    public String getBedType() {
        return bedType;
    }

    public boolean isWifi() {
        return isWifi;
    }

    public String getFacing() {
        return facing;
    }

    public boolean isSmoking() {
        return isSmoking;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public OrderMrg getRoomService() {
        return roomService;
    }

    public Guest getGuest() {
        return guest;
    }
}
