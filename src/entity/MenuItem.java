package entity;

public class MenuItem {

    private String name;
    private String description;
    private double price;

    public MenuItem(String name, String description, double price) {
        this.name = name;
        this.description = description;
        this.price = price;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "[name: " + name + ", description: " + description + ",  price: " + price + "]";
    }

    // If the item is cooking food, it shouldn't change the status once its
    // preparing
    // public enum ItemCategory {
    // COOKING, NONCOOKING
    // }

    // public void setItemCategory(ItemCategory itemCategory){
    // this.itemCategory = itemCategory;

    // }

    // public ItemCategory getItemCategory(){
    // return this.itemCategory;
    // }

}
