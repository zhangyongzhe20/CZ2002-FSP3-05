import java.time.LocalDateTime;

public class Reservation {
    private int reservationCode;
    private Guest guest;
    private String paymentMethod;
    private LocalDateTime checkin;
    private LocalDateTime checkout;
    private int numOfAdults;
    private int numOfChild;
    private String reservationStatus;
    private Map<roomNumber, Guest> roomGuest;

    public int getReservationCode() {
        return reservationCode;
    }

    /*
     * @param the reservationCode to set
     */
    public void setReservationCode(int reservationCode) {
        this.reservationCode = reservationCode;
    }

    public Guest getGuest() {
        return guest;
    }

    /*
     * @param the guest to set
     */
    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    /*
     * @param the paymentMethod to set
     */
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public LocalDateTime getCheckin() {
        return checkin;
    }

    /*
     * @param the checkin to set
     */
    public void setCheckin(LocalDateTime checkin) {
        this.checkin = checkin;
    }

    public LocalDateTime getCheckout() {
        return checkout;
    }

    /*
     * @param the checkout to set
     */
    public void setCheckout(LocalDateTime checkout) {
        this.checkout = checkout;
    }

    public int getNumOfAdults() {
        return numOfAdults;
    }

    /*
     * @param the numOfAdults to set
     */
    public void setNumOfAdults(int numOfAdults) {
        this.numOfAdults = numOfAdults;
    }

    public int getNumOfChild() {
        return numOfChild;
    }

    /*
     * @param the numOfChild to set
     */
    public void setNumOfChild(int numOfChild) {
        this.numOfChild = numOfChild;
    }

    public String getReservationStatus() {
        return reservationStatus;
    }

    /*
     * @param the reservationStatus to set
     */
    public void setReservationStatus(String reservationStatus) {
        this.reservationStatus = reservationStatus;
    }

    public Map<roomNumber, Guest> getRoomGuest() {
        return roomGuest;
    }

    /*
     * @param the roomGuest to set
     */
    public void setRoomGuest(Map<roomNumber, Guest> roomGuest) {
        this.roomGuest = roomGuest;
    }
}
