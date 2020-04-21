package entity;
import java.util.ArrayList;
import java.util.List;

public class ItemList{
    List<MenuItem> items;
    
    public ItemList(){
        items = new ArrayList<>();
    }

    public List<MenuItem> getItemList(){
        return items;
    }

    public void addItem(MenuItem Item){
        items.add(Item);
    }

    public void deleteItem(int num){
        items.remove(num);
    }

    public void updateItem(int index, MenuItem Item){
        items.set(index, Item);
    }

    public int getNumOfItems(){
        return items.size();
    }


    public void displayItems(){
        int i =1;
        for(MenuItem menuitem_ : items){
            System.out.println(" " + i +"." + menuitem_.toString());
            i++;
        }
    }
}