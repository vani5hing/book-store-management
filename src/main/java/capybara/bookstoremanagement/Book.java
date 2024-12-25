package capybara.bookstoremanagement;

public class Book extends Item {
    private String title;
    private String author;
    private int year;
    private String genre;
    private String publisher;

    public Book(String id, String title, String author, int year, String genre, String publisher, double price, int stock, double cost) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
        this.genre = genre;
        this.publisher = publisher;
        this.price = price;
        this.stock = stock;
        this.cost = cost;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getYear() {
        return year;
    }

    public String getGenre() {
        return genre;
    }

    public String getPublisher() {
        return publisher;
    }
}