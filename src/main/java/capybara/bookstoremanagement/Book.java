package capybara.bookstoremanagement;

public class Book {
    private int id;
    private String bookId;
    private String title;
    private String author;
    private double price;

    public Book(int id, String bookId, String title, String author, double price) {
        this.id = id;
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getBookId() {
        return bookId;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public double getPrice() {
        return price;
    }
}
