package capybara.bookstoremanagement;

public class Item {
    public String id;
    public String name;
    public String origin;
    public double price;
    public int stock;
    public double cost;
    public int quantity;

    public Item() {}

    public Item(String id, String name, String origin, double price, int stock, double cost, int quantity) {
        this.id = id;
        this.name = name;
        this.origin = origin;
        this.price = price;
        this.stock = stock;
        this.cost = cost;
        this.quantity = quantity;
    }

    public String getId() {
        return id;
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

    public int getStock() {
        return stock;
    }

    public double getCost() {
        return cost;
    }

    public int getQuantity() {
        return quantity;
    }
}
