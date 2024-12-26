package capybara.bookstoremanagement;

public class Employee extends User{
    private double salary;

    public Employee(int id, String name, String position, double salary) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.salary = salary;
    }

    public String getPosition() { return position; }
    public double getSalary() { return salary; }
}
