package entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Reservation {
	private String reservationCode;
	private String guestIC;
	private String roomNum;
	private LocalDateTime checkIn;
	private LocalDateTime checkOut;
	private int numOfAdults;
	private int numOfChild;
	private ReservationStatus reservationStatus;
	private CheckInType checkInType;

	public enum CheckInType {
		WALKIN, RESERVATION
	}

	public enum ReservationStatus {
		CONFIRMED, WAITLIST, CHECKIN, CHECKOUT, EXPIRED
	}

	public String getReservationCode() {
		return reservationCode;
	}

	public void setReservationCode(String reservationCode) {
		this.reservationCode = reservationCode;
	}

	public String getGuestIC() {
		return guestIC;
	}

	public void setGuestIC(String guestIC) {
		this.guestIC = guestIC;
	}

	public String getRoomNum() {
		return roomNum;
	}

	public void setRoomNum(String roomNum) {
		this.roomNum = roomNum;
	}

	public LocalDateTime getCheckIn() {
		return checkIn;
	}

	public void setCheckIn(LocalDateTime checkIn) {
		this.checkIn = checkIn;
	}

	public LocalDateTime getCheckOut() {
		return checkOut;
	}

	public void setCheckOut(LocalDateTime checkOut) {
		this.checkOut = checkOut;
	}

	public int getNumOfAdults() {
		return numOfAdults;
	}

	public void setNumOfAdults(int numOfAdults) {
		this.numOfAdults = numOfAdults;
	}

	public int getNumOfChild() {
		return numOfChild;
	}

	public void setNumOfChild(int numOfChild) {
		this.numOfChild = numOfChild;
	}

	public ReservationStatus getReservationStatus() {
		return reservationStatus;
	}

	public void setReservationStatus(ReservationStatus reservationStatus) {
		this.reservationStatus = reservationStatus;
	}

	public CheckInType getCheckInType() {
		return checkInType;
	}

	public void setCheckInType(CheckInType checkInType) {
		this.checkInType = checkInType;
	}

	public void printReservationInfo() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		System.out.println(" -------------------------------------------");
		System.out.println("Guest IC: " + this.getGuestIC());
		if (this.getCheckInType().equals(CheckInType.RESERVATION)) {
			System.out.println("Reservation Code : " + this.getReservationCode());
			System.out.println("Reservation Status : " + this.getReservationStatus());
			System.out.println("1.Check In Date: " + formatter.format(this.getCheckIn()));
			System.out.println("2.Check Out Date: " + formatter.format(this.getCheckOut()));
			System.out.println("3.Number of Adult(s): " + this.getNumOfAdults());
			System.out.println("4.Number of Child(ren): " + this.getNumOfChild());
			String roomNum;
			if (this.reservationStatus.equals(ReservationStatus.WAITLIST)) {
				roomNum = "No room selected";
			}else {
				roomNum = this.getRoomNum();
			}
			System.out.println("5.Room Number : " + roomNum);
		} else {		
			System.out.println("Check In Date: " + formatter.format(this.getCheckIn()));
			System.out.println("2.Check Out Date: " + formatter.format(this.getCheckOut()));
			System.out.println("3.Number of Adult(s): " + this.getNumOfAdults());
			System.out.println("4.Number of Child(ren): " + this.getNumOfChild());
			String roomNum;
			if(this.getRoomNum() == null) {
				roomNum = "No room selected";
			}else {
				roomNum = this.getRoomNum();
			}
			System.out.println("5.Room Number : " + roomNum);
		}
	}
}