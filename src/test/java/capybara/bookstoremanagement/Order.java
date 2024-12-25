import java.util.Map;

package capybara.bookstoremanagement;


public class Order {
    private int id;
    private String customer;
    private Map<String, Integer> books; // Map of bookId to quantity
    private double totalPrice;

    public Order(int id, String customer, Map<String, Integer> books, double totalPrice) {
        this.id = id;
        this.customer = customer;
        this.books = books;
        this.totalPrice = totalPrice;
    }

    public int getId() {
        return id;
    }

    public String getCustomer() {
        return customer;
    }

    public Map<String, Integer> getBooks() {
        return books;
    }

    public int getQuantity(String bookId) {
        return books.getOrDefault(bookId, 0);
    }

    public double getTotalPrice() {
        return totalPrice;
    }
}