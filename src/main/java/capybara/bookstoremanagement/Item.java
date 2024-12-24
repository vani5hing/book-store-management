package capybara.bookstoremanagement;

public class Item {
    public String name;
    public String origin;
    public double price;
    public int quantity;

    public Item() {}

    public Item(String name, String origin, double price, int quantity) {
        this.name = name;
        this.origin = origin;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public String getOrigin() {
        return origin;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }
}
