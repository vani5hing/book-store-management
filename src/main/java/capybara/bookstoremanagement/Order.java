package capybara.bookstoremanagement;

import java.util.Map;
import java.util.stream.Collectors;

public class Order {
    private int id;
    private String customer;
    private Map<String, Integer> books; // Map of bookId to quantity
    private double totalPrice;
    private String timeCreated;

    public Order(int id, String customer, Map<String, Integer> books, double totalPrice, String timeCreated) {
        this.id = id;
        this.customer = customer;
        this.books = books;
        this.totalPrice = totalPrice;
        this.timeCreated = timeCreated;
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

    public String getTimeCreated() {
        return timeCreated;
    }

    public String getBooksFormatted() {
        return books.keySet().stream().collect(Collectors.joining(", "));
    }

    public String getQuantitiesFormatted() {
        return books.values().stream().map(String::valueOf).collect(Collectors.joining(", "));
    }
}