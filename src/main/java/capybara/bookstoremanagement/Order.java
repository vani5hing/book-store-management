package capybara.bookstoremanagement;

public class Order {
    private int id;
    private String customer;
    private String itemid;
    private int quantity;
    private double totalPrice;
    private String timeCreated;

    public Order(int id, String customer, String itemid, int quantity, double totalPrice, String timeCreated) {
        this.id = id;
        this.customer = customer;
        this.itemid = itemid;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.timeCreated = timeCreated;
    }

    public int getId() {
        return id;
    }

    public String getCustomer() {
        return customer;
    }

    public String getItemid() {
        return itemid;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public String getTimeCreated() {
        return timeCreated;
    }
}