package controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


import entity.Reservation;
import entity.Reservation.ReservationStatus;
import entity.Room;


public class ReservationMrg {
    public static List<Reservation> reservations = new ArrayList<Reservation>();
    
    public static ReservationMrg getInstance() {
    	ReservationMrg reservationMrg = new ReservationMrg();
    	return reservationMrg;
    }
    
	public static Reservation createNewReservation() {
		return new Reservation();
	}
    public static ReservationStatus strToReservationStatus(String StrReservationStatus) {
    	Reservation.ReservationStatus reservationStatus = null;
    	if(StrReservationStatus.equalsIgnoreCase("CONFIRMED")) {
    		reservationStatus = Reservation.ReservationStatus.CONFIRMED;
        }else if(StrReservationStatus.equalsIgnoreCase("WAITLIST")) {
        	reservationStatus = Reservation.ReservationStatus.WAITLIST;
        }else if(StrReservationStatus.equalsIgnoreCase("CHECKIN")) {
        	reservationStatus = Reservation.ReservationStatus.CHECKIN;
        }else if(StrReservationStatus.equalsIgnoreCase("EXPIRED")) {
        	reservationStatus = Reservation.ReservationStatus.EXPIRED;
        }
    	return reservationStatus;
    }


    public  void createReservation(Reservation reservation){
    			reservations.add(reservation);
    			RoomMrg roomMrg = new RoomMrg();
    			if(reservation.getRoomList() != null && reservation.getRoomList().size() > 0) {
    			   for(String roomNum : reservation.getRoomList()) {
    				  Room r = roomMrg.searchRoomByNum(roomNum);
    				  roomMrg.updateRoom(r, reservation.getCheckIn(), reservation.getCheckOut(), reservation.getGuestIC(), Room.RoomStatus.RESERVED);
    			   }
    			}
    }


    public  void cancelReservation(Reservation reservation){
    		reservations.remove(reservation);
    		RoomMrg roomMrg = new RoomMrg();
    		roomMrg.cancelReservedRoom(reservation);
    }

    public  boolean updateReservation(Reservation reservation) {
    	boolean bool = false;
    	for (Reservation r : reservations) {
    		 if(r.getReservationCode().equals(reservation.getReservationCode())) {
    	    	    r.setCheckIn(reservation.getCheckIn());
    	    	    r.setCheckOut(reservation.getCheckOut());
    	    		r.setNumOfAdults(reservation.getNumOfAdults());
    	    		r.setNumOfChild(reservation.getNumOfChild());
    	    		r.setRoomList(reservation.getRoomList());
    	    		r.setReservationStatus(reservation.getReservationStatus());
    	    		bool = true;
    	   	 }
    	 }
    	return bool;
    }
    public Reservation getReservationByCode(String reservationCode) {
    	Reservation r = null;
    	for(Reservation reservation : reservations) {
    		if(reservation.getReservationCode().equals(reservationCode)) {
    			r = reservation;
    		}
    	}
    	return r;
    }
}