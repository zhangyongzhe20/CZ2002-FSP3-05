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
import java.util.UUID;

import entity.ItemList;
import entity.MenuItem;
import entity.Order;
import entity.Order.OrderBillStatus;
import entity.Order.OrderStatus;


public class OrderMrg {
    private ItemList menu;
    private static List<Order> roomOrders;
    private static Order order;
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

    public OrderMrg() {
        menu = new ItemList();
        roomOrders = new ArrayList<>();
        order = new Order();
    }

    private static OrderStatus strToOrderType(String status) {
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

    private static OrderBillStatus strToOrderBillType(String status) {
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
            if(!data.isEmpty()){
            String[] temp = data.split(",");
            Order order = new Order();
            order.setOrderId(temp[0]);
            order.setRoomId(temp[1]);
            LocalDateTime orderTime = LocalDateTime.parse(temp[2], formatter);
            order.setOrderTime(orderTime);
            order.setRemarks(temp[3]);
            order.setOrderStatus(strToOrderType(temp[4]));
            order.setOrderBillStatus(strToOrderBillType(temp[5]));
            if (sc.hasNextLine()) {
                data = sc.nextLine();
                String[] temp2 = data.split(";");
                int numOfItems = temp2.length;
                ItemList orderList = new ItemList();
                for (int i = 0; i < numOfItems; i++) {
                    String[] temp3 = temp2[i].split(",");
                    MenuItem item = new MenuItem(temp3[0], temp3[1], Double.parseDouble(temp3[2]));
                    orderList.addItem(item);
                }
                order.setOrderLists(orderList);
            }
            roomOrders.add(order);
        }
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
                for (MenuItem Item_ : order.getOrderLists().getItemList()) {
                    fileOut.print(Item_.getName() + ",");
                    fileOut.print(Item_.getDescription() + ",");
                    fileOut.print(Item_.getPrice() + ";");
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
            if(!data.isEmpty()){
            String[] temp = data.split(",");
            MenuItem menuItem_ = new MenuItem(temp[0], temp[1], Double.parseDouble(temp[2]));
            menu.addItem(menuItem_);
        }
    }
        sc.close();
    }

    public void writeMenuData() throws IOException {
        FileWriter fileWriter = new FileWriter(menuFile);
        PrintWriter fileOut = new PrintWriter(fileWriter);
        if (menu.getNumOfItems() > 0) {
            for (MenuItem menu_item : menu.getItemList()) {
                fileOut.print(menu_item.getName() + ",");
                fileOut.print(menu_item.getDescription() + ",");
                fileOut.print(menu_item.getPrice() + ",");
                fileOut.println();
            }
            fileOut.close();
        }
    }

    /**
     * Used in Payment
     * 
     * @return total charge of service
     */
    public double getRoomServiceCharge(String room_id) {
        double total_charge = 0;
        if (roomOrders != null) {
            for (Order order : roomOrders) {
                if (order.getRoomId().equals(room_id) && order.getOrderBillStatus().equals(Order.OrderBillStatus.UNBILLED))
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
    public boolean createOrders() {
        boolean bool=false;
        if (order != null) {
            order.setOrderId(UUID.randomUUID().toString());
            roomOrders.add(order);
        }
        try {
            writeOrderData();
            System.out.println("Order is created successfully!");
            System.out.println("-------------------------------------------");
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
    public boolean updateOrderDetail() {
        boolean bool = false;
            for (Order order_ : roomOrders) {
                if (order_.getOrderId() == order.getOrderId()) {
                    order_.setOrderLists(order.getOrderLists());
                    order_.setOrderStatus(order.getStatus());
                    order_.setOrderTime(order.getOrderTime());
                    order_.setRemarks(order.getRemarks());
                    order_.setOrderBillStatus(order.getOrderBillStatus());
				    bool = true;
            }
        }
		try {
            writeOrderData();
            System.out.println("Order is updated successfully!");
            System.out.println("-------------------------------------------");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bool;	
    }

    

    public boolean setUnDeliverOrder(String room_id){
        List<Order> orders = searchOrderByRoomNum(room_id);
        if(orders != null){
            for(Order order_ : orders){
                Order.OrderStatus status = order_.getStatus();
                if(!status.equals(Order.OrderStatus.DELIVERED)){
                order = order_;
                return true;
                }
            }
        }
        System.out.println("No undelivered Order is found to update");
        return false;
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
    
    public void displayAllOrders(String roomId) {
        List<Order> roomOrders_ = searchOrderByRoomNum(roomId);
        if(roomOrders_!=null){
            for(Order order_ : roomOrders_){
                order_.printOrderInfo();
            }
        }
        else{
            System.out.println("No orders are found under the room");
        }
    }
    
 public void displayUnbilledOrders(String roomId) {
     List<Order> roomOrders_ = searchOrderByRoomNum(roomId);
     if(roomOrders_!=null){
         for(Order order_ : roomOrders_){
        	 if(order_.getOrderBillStatus().equals(OrderBillStatus.UNBILLED)){
             order_.printOrderInfo();
        	 }
         }
     }
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
    
	public void setOrderLists(List<Integer> selections) {
        ItemList orderLists = order.getOrderLists();
        for (int selection_ : selections) {
            orderLists.addItem(menu.getItemList().get(selection_ - 1));
        }
        order.setOrderLists(orderLists);
	}

	public void setRemarks(String remarks) {
        order.setRemarks(remarks);
	}

	public void setOrderTime(LocalDateTime orderTime) {
        order.setOrderTime(orderTime);
	}

    public boolean setAndVerifyRoomNum(String roomNum) {
        boolean foundRoom = false;
        if (roomNum.matches("^[0-9]*$")) {
            if (!RoomMrg.checkRoomExist(roomNum)) {
                System.out.println("Room does not exist");
                foundRoom = false;
            } else {
                order.setRoomId(roomNum);
                foundRoom = true;
            }
        } else {
            System.out.println("Please enter room number in digits");
            foundRoom = false;
        }
        return foundRoom;
    }

	public void printOrderInfo() {
        order.printOrderInfo();
	}

	public void setOrderStatus(OrderStatus status) {
        order.setOrderStatus(status);
	}

	public void displayItems() {
        order.getOrderLists().displayItems();
	}

	public void deleteItem(Boolean isMenu, int selection) {
        ItemList itemlist_;
        if(isMenu){
            itemlist_ = menu;
        }
        else{
            itemlist_ = order.getOrderLists();
        }
        if (selection > 0 && selection <= itemlist_.getNumOfItems()) {
            itemlist_.deleteItem(selection - 1);
            itemlist_.displayItems();
        }
    }
    
	public int showMenu() {
        System.out.println("Hotel Menu:");
        menu.displayItems();
        return menu.getNumOfItems();
	}

	public void addMenuItem(String name, String description, String price) {
        MenuItem newItem = new MenuItem(name, description, Double.parseDouble(price));
        menu.addItem(newItem);
    }

    public void deleteMenuItem(String index){
        menu.deleteItem(Integer.parseInt(index));
    }

    public boolean updateMenu(){
        boolean bool = false;
        try {
            writeMenuData();
            System.out.println("Menu is updated successfully!");
            System.out.println("-------------------------------------------");
            bool = true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
        }
        return bool;
    }

	public void setMenuItemName(int parseInt, String userInput2) {
        menu.getItemList().get(parseInt-1).setName(userInput2);
	}

	public void setMenuItemDescription(int parseInt, String userInput2) {
        menu.getItemList().get(parseInt-1).setDescription(userInput2);
	}

	public void setMenuItemPrice(int parseInt, String userInput2) {
        double new_price = Double.parseDouble(userInput2);
        menu.getItemList().get(parseInt-1).setPrice(new_price);
	}

	public void printCurrentOrder(String userInput2) {
        order.printOrderInfo();
	}

	public void setOrdersToBilled(String roomNum) {
        if(roomOrders!=null){
            for(Order order_ : roomOrders){
                if(order_.getRoomId().equals(roomNum))
                order_.setOrderBillStatus(Order.OrderBillStatus.BILLED);
            }
            try {
                writeOrderData();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
	}
    
}