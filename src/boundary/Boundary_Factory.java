package boundary;
public class Boundary_Factory {
    public Boundary createBoundary(String selection){
        switch(selection) {
			case "1":
				return new Guest_Boundary();
			case "2":
				return new Room_Boundary();
    		 case "3":
    			return new Reservation_Boundary();
             case "4":
               return new Order_Boundary();
             case "5":
             case "6":
             default: return null;
			}
    }
}