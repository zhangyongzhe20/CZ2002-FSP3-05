package controller;

import java.util.HashMap;
import entity.*;

public class PaymentMrg {
    /**
     * Consider the guest could visit the hotel more than once
     */
    Map<Integer, List<Payment>> guestPayments;

    public PaymentMrg() {
        guestPayments = new HashMap<>();
    }

    /**
     * add/update payment under the guest_id
     * 
     * @param guest_id
     * @param payment
     */
    public void createPayment(int guest_id, Payment payment) {
        guestPayments.putIfAbsent(guest_id, new ArrayList<payment>());
        guestPayments.get(guest_id).add(payment);
    }

    // Calculate the room charge
    public double calRoomCharge(Room room) {
        double price = 0;
        double total_price = 0;
        LocalDateTime checkinTime = room.getGuest().getCheckIn();
        List<Integer> days = getDays(checkinTime, checkOutTime);
        for (int day : days) {
            if (day == 0 || day == 6) {
                price = room.getRoomRateWeekend();
            }
            price = room.getRoomRateWeekday();
            total_price += price;

        }
        return total_price;
    }

    public static List<Integer> getDays(LocalDateTime checkin, LocalDateTime checkout) {
        List<Integer> days = new ArrayList<Integer>();
        long duration = Duration.between(checkin, checkout).toDays();
        int checkin_ = checkin.getDayOfWeek().getValue();

        for (int i = 0; i < duration; i++) {
            days.add(checkin_);
            checkin_ = (checkin_ + 1) % 7;
        }
        return days;
    }

}