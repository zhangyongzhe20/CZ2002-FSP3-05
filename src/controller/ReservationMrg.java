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
import entity.Reservation.ReservationStatus;
import entity.Room;

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
		Reservation.ReservationStatus reservationStatus = null;
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

	public void createReservation(Reservation reservation) {
		reservations.add(reservation);
		RoomMrg roomMrg = new RoomMrg();

		if (reservation.getRoomList() != null && reservation.getRoomList().size() > 0) {
			for (String roomNum : reservation.getRoomList()) {
				Room r = roomMrg.searchRoomByNum(roomNum);
				roomMrg.updateRoom(r, reservation.getCheckIn(), reservation.getCheckOut(), reservation.getGuestIC(),
						Room.RoomStatus.RESERVED);
			}
		}
		try {
			writeReservationData();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void cancelReservation(Reservation reservation) {
		reservations.remove(reservation);
		RoomMrg roomMrg = new RoomMrg();
		roomMrg.cancelReservedRoom(reservation);
		
		try {
			writeReservationData();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean updateReservation(Reservation reservation) {
		boolean bool = false;
		for (Reservation r : reservations) {
			if (r.getReservationCode().equals(reservation.getReservationCode())) {
				r.setCheckIn(reservation.getCheckIn());
				r.setCheckOut(reservation.getCheckOut());
				r.setNumOfAdults(reservation.getNumOfAdults());
				r.setNumOfChild(reservation.getNumOfChild());
				r.setRoomList(reservation.getRoomList());
				r.setReservationStatus(reservation.getReservationStatus());
				bool = true;
			}
		}
		
		try {
			writeReservationData();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return bool;
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

	public List<Reservation> getAllReservation() {
		return reservations;
	}
	public List<Reservation> getCheckInReservationListByGuestIC(String IC){
		List<Reservation> rList = new ArrayList<Reservation>();
		for(Reservation r : reservations) {
			if(r.getGuestIC().equalsIgnoreCase(IC)) {
				if(r.getReservationStatus().equals(Reservation.ReservationStatus.CHECKIN)) {
					rList.add(r);
				}
			}
		}
		return rList;
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
			r.setCheckIn(LocalDateTime.parse(temp[2], formatter));
			r.setCheckOut(LocalDateTime.parse(temp[3], formatter));
			r.setNumOfAdults(Integer.parseInt(temp[4]));
			r.setNumOfChild(Integer.parseInt(temp[5]));
			r.setReservationStatus(ReservationMrg.strToReservationStatus(temp[6]));

			List<String> roomNumList = new ArrayList<String>();
			if (temp.length > 7) {
				for (int i = 7; i < temp.length; i++) {
					roomNumList.add(temp[i]);
				}
				r.setRoomList(roomNumList);
			}
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
				fileOut.print(r.getCheckIn().format(formatter) + ",");
				fileOut.print(r.getCheckOut().format(formatter) + ",");
				fileOut.print(r.getNumOfAdults() + ",");
				fileOut.print(r.getNumOfChild() + ",");
				fileOut.print(r.getReservationStatus() + ",");
				if (!r.getReservationStatus().equals(Reservation.ReservationStatus.WAITLIST)) {
					if (r.getRoomList() != null && r.getRoomList().size() > 0) {
						for (String roomNum : r.getRoomList()) {
							fileOut.print(roomNum + ",");
						}
					}
				}
				fileOut.println();
			}
			System.out.println("finish writing");
			fileOut.close();
		}
	}

}