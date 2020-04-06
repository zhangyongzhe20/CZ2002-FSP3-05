import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

public class Payment {
    private LocalDateTime checkOutTime;
    private double roomCharge;
    private double tax;
    private double roomServiceCharge;
    private double discount;
    private String paymentMethod;
    private RoomService roomService;

    public LocalDateTime getCheckOutTime() {
        return checkOutTime;
    }

    public double getRoomCharge() {
        return roomCharge;
    }

    public void setRoomCharge(double roomCharge) {
        this.roomCharge = roomCharge;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public double getRoomServiceCharge() {
        return roomServiceCharge;
    }

    public void setRoomServiceCharge(double roomServiceCharge) {
        this.roomServiceCharge = roomServiceCharge;
    }


    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public RoomService getRoomService() {
        return roomService;
    }

    public void setRoomService(RoomService roomService) {
        this.roomService = roomService;
    }

    //Calculate the room charge
    public double calRoomCharge(Room room) {
        double price=0;
        double total_price=0;
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

    public static List<Integer> getDays(LocalDateTime checkin, LocalDateTime checkout){
        List<Integer> days = new ArrayList<Integer>();
        long duration = Duration.between(checkin,checkout).toDays();
        int checkin_ = checkin.getDayOfWeek().getValue();

        for(int i = 0; i< duration; i++){
            days.add(checkin_);
            checkin_=(checkin_+1)%7;
        }
        return days;
    }

    public double calRoomServiceCharge(RoomService roomService) {
        double total_room_service = 0;
        double item_price = 0;
        for (Order orders : roomService.getOrders()){
            item_price=orders.getMenuItem().getPrice();
            total_room_service +=item_price;
        }
        return total_room_service;
    }
}
