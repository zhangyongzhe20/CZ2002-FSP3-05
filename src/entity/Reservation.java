package entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class Reservation {
    private String reservationCode;
    private String guestIC;
    private String paymentMethod;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    private int numOfAdults;
    private int numOfChild;
    private ReservationStatus reservationStatus;
    private List<String> roomList;

    
    public enum ReservationStatus {
    	CONFIRMED, WAITLIST, CHECKIN, EXPIRED
    }
    
    public String getReservationCode() {
        return reservationCode;
    }

    /*
     * @param the reservationCode to set
     */
    public void setReservationCode(String reservationCode) {
        this.reservationCode = reservationCode;
    }

    public String getGuestIC() {
        return guestIC;
    }

    /*
     * @param the guest to set
     */
    public void setGuestIC(String guestIC) {
        this.guestIC = guestIC;
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

    public LocalDateTime getCheckIn() {
        return checkIn;
    }

    /*
     * @param the checkIn to set
     */
    public void setCheckIn(LocalDateTime checkIn) {
        this.checkIn = checkIn;
    }

    public LocalDateTime getCheckOut() {
        return checkOut;
    }

    /*
     * @param the checkOut to set
     */
    public void setCheckOut(LocalDateTime checkOut) {
        this.checkOut = checkOut;
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

    public ReservationStatus getReservationStatus() {
        return reservationStatus;
    }

    /*
     * @param the reservationStatus to set
     */
    public void setReservationStatus(ReservationStatus reservationStatus) {
        this.reservationStatus = reservationStatus;
    }

	public List<String> getRoomList() {
		return roomList;
	}

	public void setRoomList(List<String> roomList) {
		this.roomList = roomList;
	}
    
}
    
