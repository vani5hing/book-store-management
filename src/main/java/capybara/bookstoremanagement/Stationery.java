package capybara.bookstoremanagement;

public class Stationery extends Item {
    private String brand;

    public Stationery(String id, String brand, String name, String origin, double price, int stock, double cost) {
        super(id, name, origin, price, stock, cost, 1); 
        this.brand = brand;
    }

    public String getBrand() {
        return brand;
    }
}