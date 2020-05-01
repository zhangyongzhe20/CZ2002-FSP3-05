package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Duration;
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
	private static List<Reservation> reservations;
	private final static String FILENAME = "reservation_data.txt";
	private Reservation reservation;
	private String roomNum;

    /**
     * Applied Singelton Desgin Pattern in Mrg classes
     */
    private static ReservationMrg SINGLE_INSTANCE;
    public static ReservationMrg getInstance() {
        if (SINGLE_INSTANCE == null) {
            SINGLE_INSTANCE = new ReservationMrg();
        }
        return SINGLE_INSTANCE;
    }

    public ReservationMrg() {
		reservations = new ArrayList<Reservation>();
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

	public String getGuestIC() {
		return reservation.getGuestIC();
	}

	public String getRoomNum() {
		return reservation.getRoomNum();
	}
	public String getReservationCode() {
		return reservation.getReservationCode();
	}

	public static boolean checkReservationExist(String reservationCode) {
		for (Reservation reservation : reservations) {
			if (reservation.getReservationCode().equalsIgnoreCase(reservationCode)) {
				return true;
			}
		}
		return false;
	}

	public boolean checkCheckInExist(String roomNum) {

		for (Reservation reservation : reservations) {
			if (reservation.getRoomNum()!=null && reservation.getRoomNum().equalsIgnoreCase(roomNum)) {
				if (reservation.getReservationStatus().equals(ReservationStatus.CHECKIN)) {
					this.reservation = reservation;
					return true;
				}
			}

		}
		return false;
	}

	public boolean checkReservationExistByGuestIC(String ic) {

		for (Reservation reservation : reservations) {
			if (reservation.getGuestIC().equalsIgnoreCase(ic)) {
				if (reservation.getReservationStatus().equals(ReservationStatus.CHECKIN)
						|| reservation.getReservationStatus().equals(ReservationStatus.CONFIRMED)
						|| reservation.getReservationStatus().equals(ReservationStatus.WAITLIST)) {
					return true;
				}
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
		}else if (StrReservationStatus.equalsIgnoreCase("CHECKOUT")) {
			reservationStatus = Reservation.ReservationStatus.CHECKOUT;
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
				if(reservation.getCheckInType().equals(CheckInType.RESERVATION)) {
				System.out.println("Reservation is created successfully!");
				}else {
					System.out.println("Check In Successfully!");	
				}
				System.out.println("-------------------------------------------");
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

	public void updateReservationDetails() {
		RoomStatus rs;
		if (roomNum != null) {
			RoomMrg.getInstance().updateRoomStatus(roomNum, RoomStatus.VACANT);
		}
		if (reservation.getRoomNum() != null) {
			if(reservation.getReservationStatus().equals(ReservationStatus.CHECKIN)) {
				rs = RoomStatus.OCCUPIED;
			}else if(reservation.getReservationStatus().equals(ReservationStatus.CONFIRMED)) {
				rs = RoomStatus.RESERVED;
			}else if(reservation.getReservationStatus().equals(ReservationStatus.WAITLIST)) {
				rs = RoomStatus.RESERVED;
			}else {
				rs = RoomStatus.VACANT;
			}
			RoomMrg.getInstance().updateRoomStatus(reservation.getRoomNum(),rs);
		}
		try {
			writeReservationData();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void checkOutReservation(LocalDateTime checkOutDate) {
		reservation.setCheckOut(checkOutDate);
		reservation.setReservationStatus(ReservationStatus.CHECKOUT);
		try {
			writeReservationData();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		RoomMrg.getInstance().updateRoomStatus(reservation.getRoomNum(), RoomStatus.VACANT);
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
		for (Reservation reservation : reservations) {
			if (reservation.getRoomNum().equalsIgnoreCase(roomNum)) {
				if (reservation.getReservationStatus().equals(ReservationStatus.CHECKIN)) {
					return reservation;

				}
			}
		}
		return null;
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
	public String getReservationRoomByIC(String ic) {
		Reservation reservation = getReservationByGuestIC(ic);
		if (reservation.getReservationStatus().equals(Reservation.ReservationStatus.CHECKIN)||
				reservation.getReservationStatus().equals(ReservationStatus.CONFIRMED)) {
				return reservation.getRoomNum();
		}
		return null;
	}
	public void printReservationInfo() {
		reservation.printReservationInfo();
	}

	public void printReservationsByStatus(ReservationStatus rs) {
		List<Reservation> rList = new ArrayList<Reservation>();
		rList = getReservationByReservationStatus(rs);
		for (Reservation r : rList) {
			r.printReservationInfo();
		}
	}

	public void printActiveReservation() {
		for (Reservation r : reservations) {
			if (r.getCheckInType().equals(CheckInType.RESERVATION)) {
				if (!r.getReservationStatus().equals(Reservation.ReservationStatus.EXPIRED)) {
					r.printReservationInfo();
				}
			}
		}
	}

	public void loadReservationData() throws FileNotFoundException {
		File file = new File(FILENAME);
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
			if(!data.isEmpty()){
			String[] temp = data.split(",");
			Reservation r = new Reservation();
			r.setReservationCode(temp[0]);
			r.setGuestIC(temp[1]);
			if(!temp[2].equalsIgnoreCase("null")) {
			r.setRoomNum(temp[2]);
			}
			r.setCheckIn(LocalDateTime.parse(temp[3], formatter));
			r.setCheckOut(LocalDateTime.parse(temp[4], formatter));
			r.setNumOfAdults(Integer.parseInt(temp[5]));
			r.setNumOfChild(Integer.parseInt(temp[6]));
			r.setReservationStatus(strToReservationStatus(temp[7]));
			r.setCheckInType(strToCheckInType(temp[8]));
			reservations.add(r);
		}
		checkExpiredReservations();
	}
		sc.close();
	}

	public void writeReservationData() throws IOException {
		FileWriter fileWriter = new FileWriter(FILENAME);
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
			fileOut.close();
		}
	}

	public void checkExpiredReservations() {
		for (Reservation r : reservations) {
			LocalDateTime now = LocalDateTime.now();
			LocalDateTime checkIn = r.getCheckIn();

			Duration duration = Duration.between(now, checkIn);
			if (duration.toHours() <= -1) {
				if(r.getCheckInType().equals(CheckInType.RESERVATION)) {
				if (r.getReservationStatus().equals(ReservationStatus.CONFIRMED)) {
					r.setReservationStatus(ReservationStatus.EXPIRED);
				}
			}
			}
		}
		try {
			writeReservationData();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}