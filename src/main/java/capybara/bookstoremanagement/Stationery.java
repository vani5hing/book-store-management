package capybara.bookstoremanagement;

public class Stationery extends Item {
    private String id;
    private String brand;

    public Stationery(String name, String origin, double price, int quantity) {
        super(name, origin, price, quantity);
    }

    public Stationery(String id, String brand, String name, String origin, double price) {
        super(name, origin, price, 0);
        this.id = id;
        this.brand = brand;
    }

    public String getId() {
        return id;
    }

    public String getBrand() {
        return brand;
    }
}