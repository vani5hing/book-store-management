package capybara.bookstoremanagement;

public class Book extends Item {
    private int id;
    private String bookId;
    private String title;
    private String author;

    public Book(String name, String origin, double price, int quantity, String ISBN) {
        super(name, origin, price, quantity, ISBN);
    }

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
