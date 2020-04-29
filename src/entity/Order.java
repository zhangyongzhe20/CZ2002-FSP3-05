package entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
    private OrderBillStatus billStatus;

    public Order() {
        orderItems = new ItemList();
        billStatus = OrderBillStatus.UNBILLED;
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
     * Used in Payment
     */
    public enum OrderBillStatus {
    	BILLED, UNBILLED
    }
    public void setOrderBillStatus(OrderBillStatus orderBillStatus) {
		this.billStatus = orderBillStatus;
    }

        /**
     * @return status of the order
     */
    public OrderBillStatus getOrderBillStatus() {
        return billStatus;
    }




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


    /**
     * Constructor of Order class
     */
    // public Order(LocalDateTime orderTime, String remarks, String status) {
    //     this.orderTime = orderTime;
    //     this.remarks = remarks;
    //     this.status = status;
    //     orderItems = new ItemList();
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


	public void printOrderInfo() {
		System.out.println(" -------------------------------------------");
		System.out.println("1.Room No: " + this.orderRoomId);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		System.out.println("2.Order Time: " + formatter.format(this.orderTime));
		System.out.println("3.Remarks: " + this.remarks);
        System.out.println("4.Order Status: " + this.orderStatus.toString());
        System.out.println("5.Order Items: ");
        orderItems.displayItems();
		System.out.println(" -------------------------------------------");
	}
}
