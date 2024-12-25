package capybara.bookstoremanagement;

import java.util.Map;

public class Bill {
    private String customer;
    private Map<String, Integer> books;
    private Map<String, String> bookTitles;
    private double totalPrice;

    public Bill(String customer, Map<String, Integer> books, Map<String, String> bookTitles, double totalPrice) {
        this.customer = customer;
        this.books = books;
        this.bookTitles = bookTitles;
        this.totalPrice = totalPrice;
    }

    public String getCustomer() {
        return customer;
    }

    public Map<String, Integer> getBooks() {
        return books;
    }

    public Map<String, String> getBookTitles() {
        return bookTitles;
    }

    public double getTotalPrice() {
        return totalPrice;
    }
}
