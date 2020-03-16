import java.util.List;

/**
 * The class managing room service information
 */
public class RoomService {

    private List<Order> orders;
    private List<MenuItem> menuItems;
    private Room room;

    /**
     * Constructor to enter a room service
     * @param orders Guest orders
     * @param menuItems MenuItems
     * @param room Service room
     */

    public RoomService(List<Order> orders,List<MenuItem> menuItems,Room room) {
        this.orders = orders;
        this.menuItems = menuItems;
        this.room = room;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public List<MenuItem> getMenuItems() {
        return menuItems;
    }

    //
    public void setMenuItems(MenuItem menuItem) {
        this.menuItems.add(menuItem);
    }

    public Room getRoom() {
        return room;
    }

    /**
     * Display MenuItems
     */
    public void displayMenuItems(){
        for(MenuItem item: menuItems) {
            System.out.println(item);
        }
    }

    /**
     * Create new orders
     */
    public void createOrder(Order order){
        this.orders.add(order);
    }
}

