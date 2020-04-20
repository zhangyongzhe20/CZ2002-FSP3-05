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

import entity.Guest;
import entity.Reservation;
import entity.Reservation.CheckInType;
import entity.Reservation.ReservationStatus;
import entity.Room;
import entity.Room.RoomStatus;

public class ReservationMrg {
	public static List<Reservation> reservations = new ArrayList<Reservation>();
	final static String fileName = "reservation_data.txt";

	public static ReservationMrg getInstance() {
		ReservationMrg reservationMrg = new ReservationMrg();
		return reservationMrg;
	}

	public static Reservation createNewReservation() {
		return new Reservation();
	}

	public static ReservationStatus strToReservationStatus(String StrReservationStatus) {
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

	public static CheckInType strToCheckInType(String StrCheckInType) {
		CheckInType checkInType = null;
		if (StrCheckInType.equalsIgnoreCase("WALKIN")) {
			checkInType = Reservation.CheckInType.WALKIN;
		} else if (StrCheckInType.equalsIgnoreCase("RESERVATION")) {
			checkInType = Reservation.CheckInType.RESERVATION;
		}
		return checkInType;
	}
	public String generateReservationCode() {
		return "";
	}
	
	public void createReservation(Reservation reservation) {
		reservations.add(reservation);
		Room room = RoomMrg.getInstance().getRoomByRoomNum(reservation.getRoomNum());
		RoomMrg.getInstance().updateRoomStatus(room, RoomStatus.RESERVED);
	}

	public void cancelReservation(Reservation reservation) {
		reservations.remove(reservation);
		Room room = RoomMrg.getInstance().getRoomByRoomNum(reservation.getRoomNum());
		RoomMrg.getInstance().updateRoomStatus(room, RoomStatus.VACANT);
	}

	public void updateReservation(Reservation reservation) {
		for (Reservation r : reservations) {
			if (r.getReservationCode().equals(reservation.getReservationCode())) {
				r.setCheckIn(reservation.getCheckIn());
				r.setCheckOut(reservation.getCheckOut());
				r.setNumOfAdults(reservation.getNumOfAdults());
				r.setNumOfChild(reservation.getNumOfChild());
				r.setRoomNum(reservation.getRoomNum());
				r.setReservationStatus(reservation.getReservationStatus());
			}
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
				if(!(reservation.getReservationStatus().equals(ReservationStatus.CHECKOUT)||
						reservation.getReservationStatus().equals(ReservationStatus.EXPIRED)))
				r = reservation;
			}
		}
		return r;
	}
	
	public Reservation getReservationByRoomNum(String roomNum) {
		Reservation r = null;
		for (Reservation reservation : reservations) {
			if (reservation.getRoomNum().equalsIgnoreCase(roomNum)) {
				if(!(reservation.getReservationStatus().equals(ReservationStatus.CHECKOUT)||
						reservation.getReservationStatus().equals(ReservationStatus.EXPIRED)))
				r = reservation;
			}
		}
		return r;
	}
	public List<Reservation> getAllReservation() {
		return reservations;
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
			Reservation r = ReservationMrg.createNewReservation();
			r.setReservationCode(temp[0]);
			r.setGuestIC(temp[1]);
			r.setRoomNum(temp[2]);
			r.setCheckIn(LocalDateTime.parse(temp[3], formatter));
			r.setCheckOut(LocalDateTime.parse(temp[4], formatter));
			r.setNumOfAdults(Integer.parseInt(temp[5]));
			r.setNumOfChild(Integer.parseInt(temp[6]));
			r.setReservationStatus(ReservationMrg.strToReservationStatus(temp[7]));
			r.setCheckInType(ReservationMrg.strToCheckInType(temp[8]));
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
				fileOut.print(r.getCheckInType()+ ",");
				fileOut.println();
			}
			System.out.println("finish writing");
			fileOut.close();
		}
	}

}