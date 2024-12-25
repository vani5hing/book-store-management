package capybara.bookstoremanagement;

public class User {
    private int id;
    private String name;
    private String position;

    public User() {}

    public User(int id, String name, String position) {
        this.id = id;
        this.name = name;
        this.position = position;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getPosition() { return position; }
}