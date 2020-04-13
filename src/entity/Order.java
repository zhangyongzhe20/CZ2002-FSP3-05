package entity;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 
 * 
 * @param orderTime
 * @param remarks
 * @param status
 */

public class Order {
    private LocalDateTime orderTime;
    private String remarks;
    private String status;
    private List<MenuItem> menuItems;

    /**
     * Constructor of Order class
     */
    public Order(LocalDateTime orderTime, String remarks, String status) {
        this.orderTime = orderTime;
        this.remarks = remarks;
        this.status = status;
    }

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

    /**
     * @return status of the order
     */
    public String getStatus() {
        return status;
    }

    /**
     * 
     * @param status status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 
     * @return menu lists of the order
     */
    public List<MenuItem> getMenuItems() {
        return menuItems;
    }

    /**
     * 
     * @param menuItem add an item to the order
     */
    public void addMenuItem(MenuItem menuItem) {
        this.menuItems.add(menuItem);
    }

    public double getOrderCharge() {
        double charges = 0.0;
        for (MenuItem menuItem_ : this.menuItems) {
            charges = charges + menuItem_.getPrice();
        }
        return charges;
    }

    @Override
    public String toString() {
        String orders ="";
        int i = 1;
        for (MenuItem item : menuItems) {
            orders = orders.concat("order" + String.valueOf(i) + " " + item.toString() + "\n");
            i++;
        }
        return "{" + " orderTime='" + getOrderTime() + "'" + ", remarks='" + getRemarks() + "'" + ", status='"
                + getStatus() + "'" + "}" + "\n" + orders;
    }
}
