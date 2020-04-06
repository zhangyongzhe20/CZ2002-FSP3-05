package controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import entity.*;

public class OrderMrg {
    List<MenuItem> menuItems;
    Map<Integer, List<Order>> roomOrders;

// the construvtor may be replaced after loadData()
    public OrderMrg() {
        roomOrders = new HashMap<Integer, ArrayList<Order>>();
        menuItems = new ArrayList<>();

    }

    /**
     * read data from local storage
     */
    public void loadData(){

    }

    /**
     * 
     * @return total charge of service
     */
    public double calculateRoomServiceCharge(int room_id) {
        double total_charge = 0;
        List<Order> orders = roomOrders.get(room_id);
        if (orders != null) {
            for (Order order : orders) {
                total_charge += total_charge + order.getOrderCharge();
            }
        }
        return total_charge;
    }

    /**
     * 
     * @param room_id
     * @param order
     * add/update order under the room_id
     */
    public void createOrders(int room_id, Order order){
         roomOrders.putIfAbsent(room_id, new ArrayList<order>());
         roomOrders.get(room_id).add(order);
    }


    /**
     * 
     * @param room_id
     * @return a list of orders under the room_id
     */
    public List<Order> getOrders(int room_id) {
        return roomOrders.get(room_id);
    }

    /**
     * 
     * @param room_id
     * display all orders under the room_id
     * this method may not belong to OrderMrg, just put here for now
     */
    public void displayOrders(int room_id){
        List<Order> orders = getOrders(room_id);
        int i=1;
        for(Order order_ : orders){
            System.out.println("Order" + String.valueOf(i) + ":");
            order_.toString();
            i++;
        }
    }


    public void displayMenus(){
        for(MenuItem menuitem_ : menuItems){
            menuitem_.toString();
        }
    }

    /**
     * hotel stuff update the menu by calling this method
     */
    public void updateMenu(){
    }





}