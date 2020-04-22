package boundary;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Boundary_Factory {
    List<Boundary> allBoundary;

    public Boundary_Factory() {
        allBoundary = new ArrayList<>();
        allBoundary.add(new Guest_Boundary());
        allBoundary.add(new Room_Boundary());
        allBoundary.add(new Reservation_Boundary());
        allBoundary.add(new Order_Boundary());
        allBoundary.add(new Payment_Boundary());
    }

    public Boundary createBoundary(String selection) {
        return allBoundary.get(Integer.parseInt(selection) - 1);
    }

    public void loadAllData() throws FileNotFoundException {
        for(Boundary boundary : allBoundary){
            boundary.loadData();
        }
    }
}