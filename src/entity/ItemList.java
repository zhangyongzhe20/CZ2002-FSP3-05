package entity;
import java.util.ArrayList;
import java.util.List;

public class ItemList{
    List<MenuItem> menuItem;
    public ItemList(){
        menuItem = new ArrayList<>();
    }

    public List<MenuItem> getItemList(){
        return menuItem;
    }

    public void addItem(MenuItem Item){
        menuItem.add(Item);
    }

    public void deleteItem(int num){
        menuItem.remove(num);
    }

    public void updateItem(int index, MenuItem Item){
        menuItem.set(index, Item);
    }

    public int getNumOfItems(){
        return menuItem.size();
    }


    public void displayItems(){
        int i =1;
        for(MenuItem menuitem_ : menuItem){
            System.out.println(i +":" + menuitem_.toString());
        }
    }
}