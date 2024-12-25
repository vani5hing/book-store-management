package capybara.bookstoremanagement;

public class Toy extends Item {
    private String ageLimit;

    public Toy(String id, String name, String origin, String ageLimit, double price, int stock, double cost) {
        super(id, name, origin, price, stock, cost, 1);
        this.ageLimit = ageLimit;
    }

    public String getAgeLimit() {
        return ageLimit;
    }
}