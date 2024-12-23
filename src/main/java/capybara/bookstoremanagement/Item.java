package capybara.bookstoremanagement;

public class Item {
    public String name;
    public String origin;
    public double price;
    public int quantity;
    public String ISBN;

    public Item() {}

    public Item(String name, String origin, double price, int quantity, String ISBN) {
        this.name = name;
        this.origin = origin;
        this.price = price;
        this.quantity = quantity;
        this.ISBN = ISBN;
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

    public String getISBN() {
        return ISBN;
    }
}
