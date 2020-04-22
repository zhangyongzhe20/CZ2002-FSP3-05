package boundary;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import controller.OrderMrg;
import controller.RoomMrg;
import entity.*;

public class Order_Boundary extends Boundary {
    Scanner sc = new Scanner(System.in);
    // get the instance of Order Mrg
    public static OrderMrg orderMrg = OrderMrg.getInstance();


    public void displayMain() {
        int choice = 0;
        do {
            System.out.println("Order System:\n" + "0. Return to previous page\n" + "1. Create Order\n"
                    + "2. Update Order\n" + "3. Search Order\n");
            choice = Integer.parseInt(sc.nextLine());
            switch (choice) {
                case 0:
                    break;
                case 1:
                    createOrderMenu();
                    break;
                case 2:
                    updateOrderMenu();
                    break;
                case 3:
                    OrderReportMenu();
                    break;
            }
        } while (choice != 0);
    }

    private void createOrderMenu(){

        Character confirm;
        System.out.println("Create Order:");
        // get user input
        enterRoomNum();
        enterOrderItem();
        enterRemarks();
        enterOrderTime();
        orderMrg.setOrderStatus(Order.OrderStatus.CONFIRMED);
        //order.setOrderStatus(Order.OrderStatus.CONFIRMED);
        do {
            orderMrg.printOrderInfo();
            System.out.println("Press Y to confirm," + "N to discard and " + "(No.) to edit a field.");
            confirm = sc.nextLine().toUpperCase().charAt(0);
            switch (confirm) {
                case 'Y':
                    // Confirm order then set the order time and set the order status
                    if (orderMrg.createOrders()) {
                        System.out.println("Order is created successfully!");
                    }
                    break;
                case 'N':
                    break;
                case '1':
                    enterRoomNum();
                    break;
                case '2':
                    enterOrderItem();
                    break;
                case '3':
                    enterRemarks();
                    break;
                case '4':
                    updateOrderStatus();
                    break;
                case '5':
                    updateOrderItem();
                    break;
                default:
                    break;
            }
        } while (!(confirm.equals('Y') || confirm.equals('N')));
    }

    // use RoomMrg.checkRoomExsit here
    private String enterRoomNum() {
        String roomNum = new String();
        do{
        System.out.println("Enter room number of the Order: ");
        roomNum = sc.nextLine();
        }while(!orderMrg.setAndVerifyRoomNum(roomNum));
        return roomNum;
    }


    private void enterOrderItem() {
        System.out.println("Hotel Menu:");
        int selection;
        List<Integer> selections = new ArrayList<>();
        int numOfItems = orderMrg.showMenu();
        System.out.println("Press 0 to confirm," + "(No.) to add an item to order.");
        selection = Integer.parseInt(sc.nextLine());
        while (selection != 0 || selection > numOfItems || selection < 0) {
            selections.add(selection);
            selection = Integer.parseInt(sc.nextLine());
            System.out.println("Press 0 to confirm," + "(No.) to add an item to order.");
        }
         // collect Item from menu and add to OrderLists
        orderMrg.setOrderLists(selections);
    }

    private void enterRemarks() {
        String remarks = "";
        System.out.println("Remarks to put for the order:");
        remarks = sc.nextLine();
        orderMrg.setRemarks(remarks);
    }

    private void enterOrderTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyy HH:mm");
        LocalDateTime orderTime = LocalDateTime.now();
        orderTime = LocalDateTime.parse(orderTime.format(formatter), formatter);
        orderMrg.setOrderTime(orderTime);
    }

    private void updateOrderMenu() {
        System.out.println("update Order");
        String roomNum = enterRoomNum();
        Character confirm;
        do {
            orderMrg.printUndeliveredOrderInfo(roomNum);
            System.out.println("Press Y to confirm," + "N to discard and " + "(No.) to edit a field.");
            confirm = sc.nextLine().charAt(0);
            switch (confirm) {
                case 'Y':
                    boolean success = orderMrg.updateOrderDetail();
                    if (success) {
                        System.out.println("Sucessfully update order");
                    } else {
                        System.out.println("Unable to update order");
                    }
                    break;
                case 'N':
                    break;
                case '1':
                    enterRoomNum();
                    break;
                case '2':
                    enterOrderTime();
                    break;
                case '3':
                    enterRemarks();
                    break;
                case '4':
                    updateOrderStatus();
                    break;
                case '5':
                    updateOrderItem();
                    break;
                default:
                    break;
            }
        } while (!(confirm.equals('Y') || confirm.equals('N')));
    }

    private void updateOrderItem() {
        String i;
        System.out.println("Update order items:");
        System.out.println("0. Return to previous page\n" + "1. Add new order items\n" + "2. Delete order items\n");
        i = sc.nextLine();
        switch (i) {
            case "0":
                break;
            case "1":
                enterOrderItem();
                break;
            case "2":
                deleteOrderItem();
                break;
        }
    }

    private void deleteOrderItem(){
        System.out.println("Press 0 to previous page " + "(No.) to delete a order item.");
        int selection = 1;
        do {
            selection = Integer.parseInt(sc.nextLine());
            orderMrg.deleteItem(selection);
        } while (selection != 0);
    }


    private void updateOrderStatus() {
        String i;
        System.out.println("Update order status:");
        System.out.println(
                "0. Return to previous page\n" + "1. Mark order as Preparing\n" + "2. Mark Order as Delivered\n");
        i = sc.nextLine();
        switch (i) {
            case "0":
                break;
            case "1":
                orderMrg.setOrderStatus(Order.OrderStatus.PREPARING);
                break;
            case "2":
                orderMrg.setOrderStatus(Order.OrderStatus.DELIVERED);
                break;
        }
    }

    private void OrderReportMenu() {
        String i;
        do {
            System.out.println("Order Report Page:");
            System.out.println("0. Return to previous page\n" + "1. Print Orders by Room ID\n"
                    + "2. Print Orders by Order Status\n");
            i = sc.nextLine();
            switch (i) {
                case "0":
                    break;
                case "1":
                    System.out.println("Enter Room ID:");
                    String roomId = sc.nextLine();
                    orderMrg.displayAllOrders(roomId);
                    break;
                case "2":
                    displayOrderByStatus();
                    break;
            }
        } while (!i.equalsIgnoreCase("0"));
    }

    private void displayOrderByStatus() {
        String i;
        do {
            System.out.println("0. Return to previous page\n" + "1. Print All Confirmed Orders\n"
                    + "2. Print All Preparing Orders \n" + "3. Print All Delivered Orders\n");
            i = sc.nextLine();
            switch (i) {
                case "0":
                    break;
                case "1":
                    orderMrg.printOrderByStatus(Order.OrderStatus.CONFIRMED);
                    break;
                case "2":
                    orderMrg.printOrderByStatus(Order.OrderStatus.PREPARING);
                    break;
                case "3":
                    orderMrg.printOrderByStatus(Order.OrderStatus.DELIVERED);
                    break;
            }
        } while (!i.equalsIgnoreCase("0"));
    }
@Override
    public void loadData() {
        // TODO Auto-generated method stub
        try {
            orderMrg.loadMenuData();
            orderMrg.loadOrderData();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        OrderMrg orderMrg = OrderMrg.getInstance();
        RoomMrg roomMrg = RoomMrg.getInstance();
        try {
            orderMrg.loadMenuData();
            orderMrg.loadOrderData();
            roomMrg.loadRoomData();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Order_Boundary oBoundary = new Order_Boundary();
        oBoundary.displayMain();
	}
}