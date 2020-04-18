package entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


public class Reservation{
    private String reservationCode;
    private String guestIC;
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
	
	public void printReservationInfo() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		
		System.out.println(" -------------------------------------------");
		System.out.println("Guest IC: " + this.getGuestIC());
		System.out.println("1.Reservation Code : " + this.getReservationCode());
		
		System.out.println("2.Check In Date: " + formatter.format(this.getCheckIn()));
		System.out.println("3.Check Out Date: " + formatter.format(this.getCheckOut()));
		System.out.println("4.Number of Adult(s): " + this.getNumOfAdults());
		System.out.println("5.Number of Child(ren): " + this.getNumOfChild());
		System.out.println("Reservation Status : " + this.getReservationStatus());
		if (this.getRoomList() != null && this.getRoomList().size() > 0) {

			System.out.print("6.Room Number : ");
			for (String roomNum : this.getRoomList()) {
				System.out.print(roomNum + " ");
			}

			System.out.println();
		}
		System.out.println(" -------------------------------------------");
	}
}
    
