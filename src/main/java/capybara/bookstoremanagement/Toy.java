package capybara.bookstoremanagement;

public class Toy extends Item {
    private String id;
    private String ageLimit;

    public Toy(String name, String origin, double price, int quantity) {
        super(name, origin, price, quantity);
    }

    public Toy(String id, String name, String origin, String ageLimit, double price) {
        super(name, origin, price, 0);
        this.id = id;
        this.ageLimit = ageLimit;
    }

    public String getId() {
        return id;
    }

    public String getAgeLimit() {
        return ageLimit;
    }
}