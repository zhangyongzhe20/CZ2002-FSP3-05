package boundary;
import java.util.Scanner;
public class HotelApp {
	public static void main(String[] args) {
		 Scanner sc = new Scanner(System.in);
		 int choice;
		 do {
			Boundary main = new Boundary();
			main.displayMain();
			choice = Integer.parseInt(sc.nextLine());
			 switch(choice) {
				 case 1:
				 break;
				 case 2:
				 break;
				 case 3:
				 break;
				 case 4:
				 main = new order_Boundary();
				 main.displayMain();
				 break;
				 case 5:
				 break;
			 		}
			 }while(choice!=6);
	}
}
