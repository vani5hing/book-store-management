package capybara.bookstoremanagement;

public class Book extends Item {
    private int id;
    private String itemid;
    private String title;
    private String author;

    public Book(String name, String origin, double price, int quantity) {
        super(name, origin, price, quantity);
    }

    public Book(int id, String itemid, String title, String author, double price) {
        this.id = id;
        this.itemid = itemid;
        this.title = title;
        this.author = author;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getitemid() {
        return itemid;
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
