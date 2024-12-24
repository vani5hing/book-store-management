package capybara.bookstoremanagement;

public class Toy extends Item {
    private int id;
    private String brand;
    private int suitableAge;

    public Toy(String name, String origin, double price, int quantity) {
        super(name, origin, price, quantity);
    }

    public Toy(int id, String brand, int suitableAge, String name, String origin, double price, int quantity) {
        super(name, origin, price, quantity);
        this.id = id;
        this.brand = brand;
        this.suitableAge = suitableAge;
    }

    public int getId() {
        return id;
    }

    public String getBrand() {
        return brand;
    }

    public int getSuitableAge() {
        return suitableAge;
    }
}