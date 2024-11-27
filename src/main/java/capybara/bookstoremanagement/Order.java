package capybara.bookstoremanagement;

public class Order {
    private int id;
    private String customer;
    private String bookId;
    private int quantity;
    private double totalPrice;

    public Order(int id, String customer, String bookId, int quantity, double totalPrice) {
        this.id = id;
        this.customer = customer;
        this.bookId = bookId;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    public int getId() {
        return id;
    }

    public String getCustomer() {
        return customer;
    }

    public String getBookId() {
        return bookId;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }
}