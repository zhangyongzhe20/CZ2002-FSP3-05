package boundary;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import controller.OrderMrg;
import controller.RoomMrg;
import entity.*;

public class Order_Boundary {
    static Scanner sc = new Scanner(System.in);
    // get the instance of Order Mrg
    static OrderMrg orderMrg = OrderMrg.getInstance();
    static Order order = OrderMrg.createNewOrder();
    static RoomMrg roomMrg = new RoomMrg();

    public void displayMain() {
        int choice = 0;
        do {
            System.out.println("Order System:\n" + "0. Return to Main Menu\n" + "1. Create Order\n" + "2. Update Order\n"
                     + "3. Search Order\n");
            choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 0:
                    break;
                case 1:
                    createOrderMenu();
                    break;
                case 2:
                   updateOrderBydetailsMenu();
                    break;
                case 3:
                    OrderReportMenu();
                    break;
            }
        } while (choice != 0);
        sc.close();
    }


    private void createOrderMenu() {

        Character confirm;
        System.out.println("Create Order:");
        // get user input
        order.setRoomId(enterAndVerifyRoomNum());
        enterOrderItem();
        enterRemarks();
        do {
            order.printOrderInfo();
            System.out.println("Press Y to confirm," + "N to discard and " + "(No.) to edit a field.");
            confirm = sc.nextLine().toUpperCase().charAt(0);
            switch (confirm) {
                case 'Y':
                    // Confirm order then set the order time and set the order status
                    order.setOrderId(UUID.randomUUID().toString());
                    enterOrderTime();
                    order.setOrderStatus(Order.OrderStatus.CONFIRMED);
                    orderMrg.createOrders(order);
                    break;
                case 'N':
                    break;
                case '1':
                order.setRoomId(enterAndVerifyRoomNum());
                   break;
                case '2':
                    enterOrderItem();
                    break;
                case '3':
                    enterRemarks();
                    break;
                default:
                    break;
            }
        } while (!(confirm.equals('Y') || confirm.equals('N')));
    }

    private String enterAndVerifyRoomNum() {
        String roomNum = new String();
        do {
            System.out.println("Enter room number of the Order: ");
            roomNum = sc.nextLine();
            if (roomNum.matches("^[0-9]*$")) {
                Room r = roomMrg.getRoomByRoomNum(roomNum);
                if (r == null) {
                    System.out.println("Room does not exist");
                } else {
                    break;
                }
            } else {
                System.out.println("Please enter room number in digits");
            }
        } while (true);
        return roomNum;
    }
    

    private static void enterOrderItem(){
        int selection;
        List<Integer> selections = new ArrayList<>();
        ItemList menu = OrderMrg.getMenu();
        menu.displayItems();
        int numOfItems = menu.getNumOfItems();
        System.out.println("Press 0 to confirm," + "(No.) to add an item to order.");
        selection = sc.nextInt();
        while (selection!=0 || selection > numOfItems|| selection < 1){
            selections.add(selection); 
            selection = sc.nextInt();
        }
        // collect Item from menu and add to OrderLists
        // OrderLists is under the room_id
        ItemList orderLists = order.getOrderLists();
        for (int selection_ : selections) {
            orderLists.addItem(menu.getItemList().get(selection_));
        }
        order.setOrderLists(orderLists);
    }

    private static void enterRemarks() {
        String remarks = "";
        System.out.println("Remarks to put for the order:");
            remarks = sc.nextLine();
        order.setRemarks(remarks);
    }


    private static void enterOrderTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyy HH:mm");
        LocalDateTime orderTime = LocalDateTime.now();
        orderTime = LocalDateTime.parse(orderTime.format(formatter), formatter);
        order.setOrderTime(orderTime);
    }



    

    private void updateOrderBydetailsMenu() {
        System.out.println("update Order");
        String roomNum = enterAndVerifyRoomNum();
            Character confirm;
            do {
                order.printOrderInfo();
                System.out.println("Press Y to confirm," + "N to discard and "
                        + "(No.) to edit a field.");
                confirm = sc.nextLine().charAt(0);
                switch (confirm) {
                    case 'Y':
                        boolean success = orderMrg.updateOrderDetail(order);
                        if (success) {
                            System.out.println("Sucessfully update order");
                        } else {
                            System.out.println("Unable to update order");
                        }
                        break;
                    case 'N':
                        break;
                    case '1':
                        order.setRoomId(enterAndVerifyRoomNum());
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
            }while (!(confirm.equals('Y') || confirm.equals('N')));
    }

    private static void updateOrderItem(){
        String i;
        do{
        System.out.println("Update order items:");
        System.out.println("0. Return to Previous Page\n" + "1. Add new order items:\n"
        + "2. Delelte order items\n");
        i = sc.nextLine();
        sc.nextLine();
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
    } while (!i.equalsIgnoreCase("0"));


    }
    private static void deleteOrderItem(){
        order.getOrderLists().displayItems();
        int selection=1;
        do{
        System.out.println("Press 0 to previous page "
        + "(No.) to delete a order item.");        
        selection = sc.nextInt();
        order.getOrderLists().deleteItem(selection);
        }while (selection!=0);
    }

    

    private static void updateOrderStatus() {
		String i;
		do {
            System.out.println("Update order status:");
            System.out.println("0. Return to Previous Page\n" + "1. Mark order as Preparing:\n"
            + "2. Mark Order as Delivered\n");
			i = sc.nextLine();
			sc.nextLine();
			switch (i) {
			case "0":
				break;
			case "1":
                order.setOrderStatus(Order.OrderStatus.PREPARING);
				break;
            case "2":
                order.setOrderStatus(Order.OrderStatus.DELIVERED);
                break;
			}
		} while (!i.equalsIgnoreCase("0"));
    }



    private void OrderReportMenu() {
        String i;
        do{
        System.out.println("Order Report Page:");
        System.out.println("0. Return to Previous Page\n" + "1. Print Orders by Room ID:\n"
        + "2. Print Orders by Order Status\n");
        i = sc.nextLine();
        switch(i){
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
        }while (!i.equalsIgnoreCase("0"));
    }


    private void displayOrderByStatus() {
        String i;
        do{
        System.out.println("0. Return to Previous Page\n" + "1. Print All Confirmed Orders\n"
        + "2. Print All Preparing Orders \n" + "3. Print All Delivered Orders\n");    
    i = sc.nextLine();
    switch(i){
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
        }while (!i.equalsIgnoreCase("0"));
    }

}