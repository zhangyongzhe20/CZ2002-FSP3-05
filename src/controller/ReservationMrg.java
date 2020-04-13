package controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import entity.Reservation;
import entity.Room.RoomStatus;

public class ReservationMrg {
    public static List<Reservation> reservations;
    
    public static Reservation.ReservationStatus strToReservationStatus(String StrReservationStatus) {
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


    public static void makeReservation(String reservationCode,String guestIC ,LocalDateTime checkInDate, LocalDateTime checkOutDate , int numOfAdults , int numOfChild,String StrReservationStatus , List<String> roomList){
    		Reservation reservation = new Reservation();
    		reservation.setReservationCode(reservationCode);
    		reservation.setGuestIC(guestIC);
    	    reservation.setCheckIn(checkInDate);
    	    reservation.setCheckOut(checkOutDate);
    		reservation.setNumOfAdults(numOfAdults);
    		reservation.setNumOfChild(numOfChild);
    		reservation.setRoomList(roomList);
    		reservation.setReservationStatus(ReservationMrg.strToReservationStatus(StrReservationStatus));
    		reservations.add(reservation);
    }


    public static void cancelReservation(Reservation reservation){
    		reservations.remove(reservation);
    		RoomMrg.cancelReservedRoom(reservation);
    }

    public static void changeReservation(Reservation reservation ,String guestIC ,LocalDateTime checkInDate, LocalDateTime checkOutDate , int numOfAdults , int numOfChild,String StrReservationStatus , List<String> roomList){
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
    public static Reservation getReservationByCode(String reservationCode) {
    	Reservation r = null;
    	for(Reservation reservation : reservations) {
    		if(reservation.getReservationCode().equals(reservationCode)) {
    			r = reservation;
    		}
    	}
    	return r;
    }
}