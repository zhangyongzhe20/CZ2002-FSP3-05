package boundary;
import java.util.Scanner;

public class HotelApp {
	public static void main(String[] args) {
		Scanner sc  = new Scanner(System.in);
		String selection = "1";
		Boundary_Factory bf = new Boundary_Factory();
		bf.loadAllData();
		do{
			displayMain();
			selection = sc.nextLine();
			if(Integer.parseInt(selection)>0 && Integer.parseInt(selection)<6){
			Boundary nextpage = bf.createBoundary(selection);
			nextpage.displayMain();
		}while(!selection.equalsIgnoreCase("6"));
	}
}

	public static void displayMain(){
		System.out.println("Hotel Reservation and Payment System (HRPS)");
		System.out.println("===========================================");
		System.out.println("1. About Guest");
		System.out.println("2. About Room");
		System.out.println("3. About Reservation");
        System.out.println("4. About Room Service");
        System.out.println("5. About Payment");
		System.out.println("6. Quit");
        System.out.println("============================================");
	}
}