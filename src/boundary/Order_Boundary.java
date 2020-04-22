package boundary;

import controller.OrderMrg;
import controller.RoomMrg;
import entity.Order;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Order_Boundary extends Boundary {
    // get the instance of Order Mrg
    public static OrderMrg orderMrg = OrderMrg.getInstance();
    String userInput;
    Character confirm;
    public void displayMain(){
        do {
            System.out.println("Service System:\n" + "0. Return to previous page\n" + "1.Order Page\n"
                    + "2. Menu Page\n");
            userInput = readInputString("Enter userInput : ");
            switch(userInput){
                case "0":
				break;
			case "1":
                OrderPage();
				break;
			case "2":
                MenuPage();
                break;
            }
        } while (!userInput.equalsIgnoreCase("0"));
    }
    private void OrderPage() {
        do {
            userInput = readInputString("Order Page:\n" + "0. Return to previous page\n" + "1. Create Order\n"
            + "2. Update Order\n" + "3. Search Order\n" + "Enter userInput : ");
            switch (userInput) {
                case "0":
                    break;
                case "1":
                    createOrderMenu();
                    break;
                case "2":
                    updateOrderMenu();
                    break;
                case "3":
                    OrderReportMenu();
                    break;
            }
        }  while (!userInput.equalsIgnoreCase("0"));
    }
    private void MenuPage(){
        String userInput;
        do {
            userInput = readInputString("Menu Page:\n" + "0. Return to previous page\n" + "1. Display Menu\n"
               + "2. Add menu item\n" + "3.Delete menu item");
            switch (userInput) {
                case "0":
                if(orderMrg.updateMenu()){
                    System.out.println("Update Menu successfully!");
                }
                    break;
                case "1":
                    orderMrg.showMenu();
                    break;
                case "2":
                enterMenuItem();
                break;
                case "3":
                deleteMenuItem();
                break;
            }
        }  while (!userInput.equalsIgnoreCase("0"));

    }



    private void enterMenuItem() {
        do{
        String name, description, price;
        name = readInputString("Enter item name:");
        description = readInputString("Enter item description:");
        price = readInputString("Enter item price:");
        userInput = readInputString("Press Y to confirm," + "N to discard and " + "C to continue").toUpperCase();
        if(!userInput.equals("N"))
        orderMrg.addMenuItem(name, description, price);
        }while(userInput.equalsIgnoreCase("C"));
    }

    private void deleteMenuItem(){
        orderMrg.showMenu();
        do {
            userInput = readInputString("Press Y to confirm," + "N to discard and" + "(No.) to delete a menu item.");
            if(!userInput.equals("N") && !userInput.equals("Y"))
            orderMrg.deleteItem(Integer.parseInt(userInput));
        } while (!userInput.equalsIgnoreCase("Y") && !userInput.equalsIgnoreCase("N"));
    }



    private void createOrderMenu() {
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
            userInput = readInputString("Press Y to confirm," + "N to discard and " + "(No.) to edit a field.").toUpperCase();
            switch (userInput) {  
                case "Y":
                    // Confirm order then set the order time and set the order status
                    if (orderMrg.createOrders()) {
                        System.out.println("Order is created successfully!");
                    }
                    break;
                case "N":
                    break;
                case "1":
                    enterRoomNum();
                    break;
                case "2":
                    enterOrderItem();
                    break;
                case "3":
                    enterRemarks();
                    break;
                case "4":
                    updateOrderStatus();
                    break;
                case "5":
                    updateOrderItem();
                    break;
                default:
                    break;
            }
        } while (!(userInput.equals("Y") || userInput.equals("N")));
    }

    // use RoomMrg.checkRoomExsit here
    private String enterRoomNum() {
        String roomNum;
        do{
        roomNum = readInputString("Enter room number of the Order: ");
        }while(!orderMrg.setAndVerifyRoomNum(roomNum));
        return roomNum;
    }


    private void enterOrderItem() {
        int selection;
        System.out.println("Hotel Menu:");
        List<Integer> selections = new ArrayList<>();
        int numOfItems = orderMrg.showMenu();
        selection = Integer.parseInt(readInputString("Press 0 to confirm," + "(No.) to add an item to order."));
        while (selection != 0 || selection > numOfItems || selection < 0) {
            selections.add(selection);
            selection = Integer.parseInt(readInputString("Press 0 to confirm," + "(No.) to add an item to order."));
        }
         // collect Item from menu and add to OrderLists
        orderMrg.setOrderLists(selections);
    }

    private void enterRemarks() {
        String remarks = "";
        remarks = readInputString("Remarks to put for the order:");
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
        userInput = enterRoomNum();
        do {
            orderMrg.printUndeliveredOrderInfo(userInput);
            userInput = readInputString("Press Y to confirm," + "N to discard and " + "(No.) to edit a field.").toUpperCase();
            switch (userInput) {
                case "Y":
                    boolean success = orderMrg.updateOrderDetail();
                    if (success) {
                        System.out.println("Sucessfully update order");
                    } else {
                        System.out.println("Unable to update order");
                    }
                    break;
                case "N":
                    break;
                case "1":
                    enterRoomNum();
                    break;
                case "2":
                    enterOrderTime();
                    break;
                case "3":
                    enterRemarks();
                    break;
                case "4":
                    updateOrderStatus();
                    break;
                case "5":
                    updateOrderItem();
                    break;
                default:
                    break;
            }
        } while (!(userInput.equals("Y") || userInput.equals("N")));
    }

    private void updateOrderItem() {
        userInput = readInputString("Update order items:\n" + "0. Return to previous page\n" + "1. Add new order items\n" + "2. Delete order items\n");
        switch (userInput) {
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
        do {
            userInput = readInputString("Press 0 to previous page " + "(No.) to delete a order item.");
            orderMrg.deleteItem(Integer.parseInt(userInput));
        } while (!userInput.equalsIgnoreCase("0"));
    }


    private void updateOrderStatus() {
        userInput = readInputString("Update order status:\n" + "0. Return to previous page\n" + "1. Mark order as Preparing\n" + "2. Mark Order as Delivered\n");
        switch (userInput) {
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
       
        do {
            userInput = readInputString("Order Report Page:\n" + "0. Return to previous page\n" + "1. Print Orders by Room ID\n"
            + "2. Print Orders by Order Status\n");
            switch (userInput) {
                case "0":
                    break;
                case "1":
                    userInput = readInputString("Enter Room ID:");
                    orderMrg.displayAllOrders(userInput);
                    break;
                case "2":
                    displayOrderByStatus();
                    break;
            }
        } while (!userInput.equalsIgnoreCase("0"));
    }

    private void displayOrderByStatus() {
        do {
            userInput = readInputString("0. Return to previous page\n" + "1. Print All Confirmed Orders\n"
            + "2. Print All Preparing Orders \n" + "3. Print All Delivered Orders\n");
            switch (userInput) {
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
        } while (!userInput.equalsIgnoreCase("0"));
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
}