package controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import entity.Reservation;
import entity.Reservation.ReservationStatus;
import entity.Room;
import entity.Room.RoomStatus;

public class ReservationMrg {
    public static List<Reservation> reservations = new ArrayList<Reservation>();
    
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

    public  void changeReservation(Reservation reservation ,String guestIC ,LocalDateTime checkInDate, LocalDateTime checkOutDate , int numOfAdults , int numOfChild,String StrReservationStatus , List<String> roomList){
    	 for (Reservation r : reservations) {
    		 if(r.equals(reservation)) {
    	    		r.setGuestIC(guestIC);
    	    	    r.setCheckIn(checkInDate);
    	    	    r.setCheckOut(checkOutDate);
    	    		r.setNumOfAdults(numOfAdults);
    	    		r.setNumOfChild(numOfChild);
    	    		r.setRoomList(roomList);
    	    		r.setReservationStatus(ReservationMrg.strToReservationStatus(StrReservationStatus));
    		 }
    	 }
    }
    public  Reservation getReservationByCode(String reservationCode) {
    	Reservation r = null;
    	for(Reservation reservation : reservations) {
    		if(reservation.getReservationCode().equals(reservationCode)) {
    			r = reservation;
    		}
    	}
    	return r;
    }
}