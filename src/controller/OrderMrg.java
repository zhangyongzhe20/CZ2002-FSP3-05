package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import entity.*;
import entity.Order.OrderBillStatus;
import entity.Order.OrderStatus;

public class OrderMrg {
    private static ItemList menu;
    private static List<Order> roomOrders;
    final static String menuFile = "menu_data.txt";
    final static String orderFile = "order_data.txt";


        /**
     * Applied Singelton Desgin Pattern in Mrg classes
     */
    private static OrderMrg SINGLE_INSTANCE;

    public static OrderMrg getInstance() {
        if (SINGLE_INSTANCE == null) {
            SINGLE_INSTANCE = new OrderMrg();
        }
        return SINGLE_INSTANCE;
    }

    public OrderMrg(){
        menu = new ItemList();
        roomOrders = new ArrayList<>();

    }


	public static OrderStatus strToOrderType(String status) {
		OrderStatus orderStatus = null;
		if (status.equalsIgnoreCase("CONFIRMED")) {
			orderStatus = Order.OrderStatus.CONFIRMED;
		} else if (status.equalsIgnoreCase("PREPARING")) {
			orderStatus = Order.OrderStatus.PREPARING;
		} else if (status.equalsIgnoreCase("DELIVERED")) {
			orderStatus = Order.OrderStatus.DELIVERED;
		} 
		return orderStatus;
    }

    public static OrderBillStatus strToOrderBillType(String status) {
		Order.OrderBillStatus orderBillStatus = null;
		if (status.equalsIgnoreCase("BILLED")) {
			orderBillStatus = Order.OrderBillStatus.BILLED;
		} else if (status.equalsIgnoreCase("UNBILLED")) {
			orderBillStatus = Order.OrderBillStatus.UNBILLED;
        }
		return orderBillStatus;
    }
    

    public void loadOrderData() throws FileNotFoundException {
		File file = new File(orderFile);
		try {
			file.createNewFile();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		Scanner sc = new Scanner(file);
        String data;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		while (sc.hasNextLine()) {
			data = sc.nextLine();
            String[] temp = data.split(",");
            Order order = new Order();
            order.setOrderId(temp[0]);;
            order.setRoomId(temp[1]);
            LocalDateTime orderTime = LocalDateTime.parse(temp[2], formatter);
            order.setOrderTime(orderTime);
            order.setRemarks(temp[3]);
            order.setOrderStatus(strToOrderType(temp[4]));
            order.setOrderBillStatus(strToOrderBillType(temp[5]));
            if(sc.hasNextLine()){
                data = sc.nextLine();
                String[] temp2 = data.split(";");
                int numOfItems = temp2.length;
                ItemList orderList = new ItemList();
                for(int i=0; i< numOfItems; i++){
                    String[] temp3 = temp2[i].split(",");
                MenuItem item = new MenuItem(temp3[0], temp3[1], Double.parseDouble(temp3[2]));
               orderList.addItem(item);
                }
                order.setOrderLists(orderList);
            }
            roomOrders.add(order);
		}
		sc.close();
    }

    public void writeOrderData() throws IOException {
		FileWriter fileWriter = new FileWriter(orderFile);
        PrintWriter fileOut = new PrintWriter(fileWriter);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		if (roomOrders.size() > 0) {
			for (Order order : roomOrders) {
                fileOut.print(order.getOrderId() + ",");
				fileOut.print(order.getRoomId() + ",");
                fileOut.print(order.getOrderTime().format(formatter) + ",");
                fileOut.print(order.getRemarks() + ",");
                fileOut.print(order.getStatus() + ",");
                fileOut.print(order.getOrderBillStatus() + ",");
                fileOut.println();
                for(MenuItem menuItem_ : order.getOrderLists().getItemList()){
                    fileOut.print(menuItem_.getName() + ",");
                    fileOut.print(menuItem_.getDescription() + ",");
                    fileOut.print(menuItem_.getPrice() + ";");
                }
                fileOut.println();
                }
            fileOut.close();
        }
    }

    public void loadMenuData() throws FileNotFoundException {
        File file = new File(menuFile);
        try {
            file.createNewFile();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Scanner sc = new Scanner(file);
        String data;
        while (sc.hasNextLine()) {
            data = sc.nextLine();
            String[] temp = data.split(",");
            MenuItem menuItem_ = new MenuItem(temp[0], temp[1], Double.parseDouble(temp[2]));
            menu.addItem(menuItem_);
        }
        sc.close();
    }


    /**
     * Used in Payment
     * @return total charge of service
     */
    public double calculateRoomServiceCharge(String room_id) {
        double total_charge = 0;
        if (roomOrders != null) {
            for (Order order : roomOrders) {
                if (order.getRoomId().equals(room_id))
                    total_charge += total_charge + order.getOrderCharge();
            }
        }
        return total_charge;
    }


    /**
     * Used in Create-Order Page
     * 
     * @param order add new order
     */
    public boolean createOrders(Order order) {
        boolean bool=false;
        if (order != null) {
            roomOrders.add(order);
        }
        try {
            writeOrderData();
            bool = true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bool;
    }

    /**
     * Used in Update Order Page
     * 
     * @param order
     * @return
     */
    public boolean updateOrderDetail(Order order) {
        boolean bool = false;
            for (Order order_ : roomOrders) {
                if (order_.getOrderId() == order.getOrderId()) {
                    order_.setOrderLists(order.getOrderLists());
                    order_.setOrderStatus(order.getStatus());
                    order_.setOrderTime(order.getOrderTime());
                    order_.setRemarks(order.getRemarks());
				    bool = true;
            }
        }
		try {
			writeOrderData();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bool;	
    }
        /**
     * 
     * @param room_id
     * @return order with the status is not DELIVERED
     */
    private Order updateOrderByRoomNum(String room_id) {
        Order updatedOrder = new Order();
        if (roomOrders != null) {
            // Assume that a room only has an order is under "CONFIRMED" or "PREPARING"
            for (Order order : roomOrders) {
                if (order.getRoomId().equals(room_id) && !order.getStatus().equals("DELIVERED"))
                    return updatedOrder;
            }
        }
        return updatedOrder;
    }
    
    /**
     * 
     * @param room_id
     * display all orders under the room_id
     * this method is used in Report Page
     */
    public void displayAllOrders(String room_id){
        List<Order> orders = searchOrderByRoomNum(room_id);
        if(orders != null){
        for(Order order_ : orders){
            order_.printOrderInfo();
        }
    }
    else{
        System.out.println("No orders are under the room");
    }
    }


    public void displayUnDeliverOrder(String room_id){
        List<Order> orders = searchOrderByRoomNum(room_id);
        if(orders != null){
            for(Order order_ : orders){
                Order.OrderStatus status = order_.getStatus();
                if(!status.equals(Order.OrderStatus.DELIVERED)){
                order_.printOrderInfo();
                return;
                }
            }
           
        }
        System.out.println("No orders are found to update the room");
    }

    public Order getUnDeliverOrder(String room_id){
        List<Order> orders = searchOrderByRoomNum(room_id);
        if(orders != null){
            for(Order order_ : orders){
                Order.OrderStatus status = order_.getStatus();
                if(!status.equals(Order.OrderStatus.DELIVERED)){
                return order_;
                }
            }
        }
        System.out.println("No orders are found to update the room");
        return null;
    }


        /**
     * used in displayAllOrders
     * @param room_id
     * @return All orders under the room_id
     */
    public List<Order> searchOrderByRoomNum(String room_id) {
        List<Order> orders = new ArrayList<>();
        if (roomOrders != null) {
            for (Order order : roomOrders) {
                if (order.getRoomId().equals(room_id))
                    orders.add(order);
            }
        }
        return orders;
    }

	public static Order createNewOrder() {
		return new Order();
    }
    
    public static ItemList getMenu(){
        return menu;
    }

    // Used in Order Report page
	public void printOrderByStatus(OrderStatus status) {
        Boolean found = false;
        if (roomOrders != null) {
            // Assume that a room only has an order is under "CONFIRMED" or "PREPARING"
            for (Order order : roomOrders) {
                if (order.getStatus().equals(status))
                    order.printOrderInfo();
                    found = true;
            }
        }
        if(!found)
            System.out.println("No orders are found");
    }

    


}