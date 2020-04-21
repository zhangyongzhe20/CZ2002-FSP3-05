package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import entity.Reservation;
import entity.Reservation.CheckInType;
import entity.Reservation.ReservationStatus;
import entity.Room.RoomStatus;

public class ReservationMrg {
	public static List<Reservation> reservations = new ArrayList<Reservation>();
	final static String fileName = "reservation_data.txt";
	private Reservation reservation;
	private String roomNum;

	public static ReservationMrg getInstance() {
		ReservationMrg reservationMrg = new ReservationMrg();
		return reservationMrg;
	}

	public void createNewReservation() {
		reservation = new Reservation();
	}

	public void setReservationCode(String reservationCode) {
		if (checkReservationExist(reservationCode)) {
			reservation = getReservationByCode(reservationCode);
			if (reservation.getRoomNum() != null) {
				roomNum = reservation.getRoomNum();
			}
		} else {
			reservation.setReservationCode(reservationCode);
		}
	}

	public void setGuestIC(String guestIC) {
		reservation.setGuestIC(guestIC);
	}

	public void setRoomNum(String roomNum) {
		reservation.setRoomNum(roomNum);
	}

	public void setCheckIn(LocalDateTime checkIn) {
		reservation.setCheckIn(checkIn);
	}

	public void setCheckOut(LocalDateTime checkOut) {
		reservation.setCheckOut(checkOut);
	}

	public void setNumOfAdults(int numOfAdults) {
		reservation.setNumOfAdults(numOfAdults);
	}

	public void setNumOfChild(int numOfChild) {
		reservation.setNumOfChild(numOfChild);
	}

	public void setReservationStatus(ReservationStatus reservationStatus) {
		reservation.setReservationStatus(reservationStatus);
	}

	public void setCheckInType(CheckInType checkInType) {
		reservation.setCheckInType(checkInType);
	}

	public LocalDateTime getCheckIn() {
		return reservation.getCheckIn();
	}

	public LocalDateTime getCheckOut() {
		return reservation.getCheckOut();
	}

	public CheckInType getCheckInType() {
		return reservation.getCheckInType();
	}

	public ReservationStatus getReservationStatus() {
		return reservation.getReservationStatus();
	}

	public static boolean checkReservationExist(String reservationCode) {
		for (Reservation reservation : reservations) {
			if (reservation.getReservationCode().equalsIgnoreCase(reservationCode)) {
				return true;
			}
		}
		return false;
	}

	public ReservationStatus strToReservationStatus(String StrReservationStatus) {
		ReservationStatus reservationStatus = null;
		if (StrReservationStatus.equalsIgnoreCase("CONFIRMED")) {
			reservationStatus = Reservation.ReservationStatus.CONFIRMED;
		} else if (StrReservationStatus.equalsIgnoreCase("WAITLIST")) {
			reservationStatus = Reservation.ReservationStatus.WAITLIST;
		} else if (StrReservationStatus.equalsIgnoreCase("CHECKIN")) {
			reservationStatus = Reservation.ReservationStatus.CHECKIN;
		} else if (StrReservationStatus.equalsIgnoreCase("EXPIRED")) {
			reservationStatus = Reservation.ReservationStatus.EXPIRED;
		}
		return reservationStatus;
	}

	public CheckInType strToCheckInType(String strCheckInType) {
		CheckInType checkInType = null;
		if (strCheckInType.equalsIgnoreCase("WALKIN")) {
			checkInType = Reservation.CheckInType.WALKIN;
		} else if (strCheckInType.equalsIgnoreCase("RESERVATION")) {
			checkInType = Reservation.CheckInType.RESERVATION;
		}
		return checkInType;
	}

	public String generateReservationCode(String ic) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyHdmM");
		String code = ic.substring(ic.length() - 3) + LocalDateTime.now().format(formatter);
		return code;
	}

	public void createReservation() {
		boolean updateData = false;
		if (reservation.getCheckInType().equals(CheckInType.RESERVATION)) {
			updateData = true;
			reservations.add(reservation);
			if (reservation.getRoomNum() != null) {
				RoomMrg.getInstance().updateRoomStatus(reservation.getRoomNum(), RoomStatus.RESERVED);
			}
		} else if (reservation.getCheckInType().equals(CheckInType.WALKIN)) {
			if (reservation.getRoomNum() != null) {
				updateData = true;
				reservation.setReservationStatus(ReservationStatus.CHECKIN);
				RoomMrg.getInstance().updateRoomStatus(reservation.getRoomNum(), RoomStatus.OCCUPIED);
				reservations.add(reservation);

			} else {
				System.out.println("Unable to check in as no room is selected");
			}
		}
		if (updateData) {
			try {
				writeReservationData();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void cancelReservation() {
		RoomMrg.getInstance().updateRoomStatus(reservation.getRoomNum(), RoomStatus.VACANT);
		reservations.remove(reservation);

		try {
			writeReservationData();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void updateReservation() {
		if (roomNum != null) {
			RoomMrg.getInstance().updateRoomStatus(roomNum, RoomStatus.VACANT);
		}
		if (reservation.getRoomNum() != null) {
			RoomMrg.getInstance().updateRoomStatus(reservation.getRoomNum(), RoomStatus.OCCUPIED);
		}
		try {
			writeReservationData();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void checkInReservation() {
		RoomMrg.getInstance().updateRoomStatus(reservation.getRoomNum(), RoomStatus.OCCUPIED);

		try {
			writeReservationData();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Reservation getReservationByCode(String reservationCode) {
		Reservation r = null;
		for (Reservation reservation : reservations) {
			if (reservation.getReservationCode().equals(reservationCode)) {
				r = reservation;
			}
		}
		return r;
	}

	public Reservation getReservationByGuestIC(String ic) {
		Reservation r = null;
		for (Reservation reservation : reservations) {
			if (reservation.getGuestIC().equalsIgnoreCase(ic)) {
				if (!(reservation.getReservationStatus().equals(ReservationStatus.CHECKOUT)
						|| reservation.getReservationStatus().equals(ReservationStatus.EXPIRED)))
					r = reservation;
			}
		}
		return r;
	}

	public Reservation getReservationByRoomNum(String roomNum) {
		Reservation r = null;
		for (Reservation reservation : reservations) {
			if (reservation.getRoomNum().equalsIgnoreCase(roomNum)) {
				if (!(reservation.getReservationStatus().equals(ReservationStatus.CHECKOUT)
						|| reservation.getReservationStatus().equals(ReservationStatus.EXPIRED)))
					r = reservation;
			}
		}
		return r;
	}

	public List<Reservation> getReservationByReservationStatus(ReservationStatus rs) {
		List<Reservation> rList = new ArrayList<Reservation>();
		for (Reservation reservation : reservations) {
			if (reservation.getReservationStatus().equals(rs)) {
				rList.add(reservation);
			}
		}
		return rList;
	}

	public void printReservationInfo() {

		if (reservation.getRoomNum() == null) {
			reservation.setReservationStatus(ReservationStatus.WAITLIST);
		} else {
			reservation.setReservationStatus(ReservationStatus.CONFIRMED);
		}

		reservation.printInfo();
	}

	public void printReservationsByStatus(ReservationStatus rs) {
		List<Reservation> rList = new ArrayList<Reservation>();
		rList = getReservationByReservationStatus(rs);
		for (Reservation r : rList) {
			r.printInfo();
		}
	}

	public void printActiveReservation() {
		for (Reservation r : reservations) {
			if (r.getCheckInType().equals(CheckInType.RESERVATION)) {
				if (!(r.getReservationStatus().equals(Reservation.ReservationStatus.EXPIRED)
						|| r.getReservationStatus().equals(ReservationStatus.CHECKOUT))) {
					r.printInfo();
				}
			}
		}
	}

	public void loadReservationData() throws FileNotFoundException {
		File file = new File(fileName);
		try {
			file.createNewFile();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		Scanner sc = new Scanner(file);
		String data;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		while (sc.hasNextLine()) {
			data = sc.nextLine();
			String[] temp = data.split(",");
			Reservation r = new Reservation();
			r.setReservationCode(temp[0]);
			r.setGuestIC(temp[1]);
			r.setRoomNum(temp[2]);
			r.setCheckIn(LocalDateTime.parse(temp[3], formatter));
			r.setCheckOut(LocalDateTime.parse(temp[4], formatter));
			r.setNumOfAdults(Integer.parseInt(temp[5]));
			r.setNumOfChild(Integer.parseInt(temp[6]));
			r.setReservationStatus(strToReservationStatus(temp[7]));
			r.setCheckInType(strToCheckInType(temp[8]));
			reservations.add(r);
		}
		sc.close();
	}

	public void writeReservationData() throws IOException {
		FileWriter fileWriter = new FileWriter(fileName);
		PrintWriter fileOut = new PrintWriter(fileWriter);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		if (reservations.size() > 0) {
			for (Reservation r : reservations) {
				fileOut.print(r.getReservationCode() + ",");
				fileOut.print(r.getGuestIC() + ",");
				fileOut.print(r.getRoomNum() + ",");
				fileOut.print(r.getCheckIn().format(formatter) + ",");
				fileOut.print(r.getCheckOut().format(formatter) + ",");
				fileOut.print(r.getNumOfAdults() + ",");
				fileOut.print(r.getNumOfChild() + ",");
				fileOut.print(r.getReservationStatus() + ",");
				fileOut.print(r.getCheckInType() + ",");
				fileOut.println();
			}
			System.out.println("finish writing");
			fileOut.close();
		}
	}

}