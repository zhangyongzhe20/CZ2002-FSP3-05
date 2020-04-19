package entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * 
 * 
 * @param orderTime
 * @param remarks
 * @param status
 */

public class Order {
    private String orderId;
	private String orderRoomId;
    private LocalDateTime orderTime;
    private String remarks;
    private ItemList orderItems;
    private OrderStatus orderStatus;


    public void setOrderId(String orderId){
        this.orderId = orderId;
    }

    public String getOrderId(){
        return orderId;
    }

    public void setRoomId(String orderRoomId){
        this.orderRoomId = orderRoomId;
    }

    public String getRoomId(){
        return this.orderRoomId;
    }

    public enum OrderStatus {
    	CONFIRMED, PREPARING, DELIVERED
    }

    public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
    }

        /**
     * @return status of the order
     */
    public OrderStatus getStatus() {
        return orderStatus;
    }
    /**
     * Constructor of Order class
     */
    // public Order(LocalDateTime orderTime, String remarks, String status) {
    //     this.orderTime = orderTime;
    //     this.remarks = remarks;
    //     this.status = status;
    //     orderItems = new ItemList();
    // }

    // public Order() {
	// }

	/**
     * @return order time
     */
    public LocalDateTime getOrderTime() {
        return orderTime;
    }

    /**
     * 
     * @param orderTime order time to set
     */
    public void setOrderTime(LocalDateTime orderTime) {
        this.orderTime = orderTime;
    }

    /**
     * @return remarks
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * 
     * @param remarks remarks to set
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }




    public double getOrderCharge() {
        double charges = 0.0;
        List<MenuItem> totalOrderItems = orderItems.getItemList();
        if (totalOrderItems != null) {
            for (MenuItem menuItem_ : totalOrderItems) {
                charges = charges + menuItem_.getPrice();
            }
        }
        return charges;
    }

    public void setOrderLists(ItemList orderList){
        orderItems = orderList;
    }

    public ItemList getOrderLists(){
        return orderItems;
    }


    @Override
    public String toString() {
        String orders = "";
        int i = 1;
        List<MenuItem> totalOrderItems = orderItems.getItemList();
        for (MenuItem item : totalOrderItems) {
            orders = orders.concat("order" + String.valueOf(i) + " " + item.toString() + "\n");
            i++;
        }
        return "{" + " orderTime='" + getOrderTime() + "'" + ", remarks='" + getRemarks() + "'" + ", status='"
                + getStatus() + "'" + "}" + "\n" + orders;
    }

	public void printOrderInfo() {
		System.out.println(" -------------------------------------------");
		System.out.println("1.Room No: " + this.orderRoomId);
		System.out.println("2.Order Time: " + this.orderTime);
		System.out.println("3.Remarks: " + this.remarks);
        System.out.println("4.Order Status: " + this.orderStatus);
        System.out.println("5.Order Items: " + this.orderStatus);
        orderItems.displayItems();
		System.out.println(" -------------------------------------------");
	}
}
