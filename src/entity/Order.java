import java.time.LocalDateTime;

public class Order {
    private LocalDateTime orderTime;
    private String remarks;
    private String status;
    private MenuItem menuItem;

    public LocalDateTime getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(LocalDateTime orderTime) {
        this.orderTime = orderTime;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }

    public void setMenuItem(MenuItem menuItem) {
        this.menuItem = menuItem;
    }

    public Order(LocalDateTime orderTime, String remarks, String status) {
        this.orderTime = orderTime;
        this.remarks =remarks;
        this.status=status;
    }
}
