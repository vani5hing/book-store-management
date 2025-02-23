package capybara.bookstoremanagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.Random;

public class DatabaseUtil {

    private static final String URL = "jdbc:sqlite:books.db";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static void createTables() {
        String createBooksTable = "CREATE TABLE IF NOT EXISTS books ("
                                + "id TEXT PRIMARY KEY, "
                                + "title TEXT NOT NULL, "
                                + "author TEXT NOT NULL, "
                                + "year INTEGER NOT NULL, "
                                + "genre TEXT NOT NULL, "
                                + "publisher TEXT NOT NULL, "
                                + "price REAL NOT NULL,"
                                + "stock INTEGER NOT NULL"
                                + ");";

        String createAccountsTable = "CREATE TABLE IF NOT EXISTS accounts ("
                                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                                + "username TEXT NOT NULL UNIQUE, "
                                + "password TEXT NOT NULL, "
                                + "role TEXT NOT NULL"
                                + ");";

        String createCustomersTable = "CREATE TABLE IF NOT EXISTS customers ("
                                    + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                                    + "name TEXT NOT NULL, "
                                    + "email TEXT NOT NULL, "
                                    + "phone TEXT NOT NULL"
                                    + ");";

        String createEmployeesTable = "CREATE TABLE IF NOT EXISTS employees ("
                                    + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                                    + "name TEXT NOT NULL, "
                                    + "position TEXT NOT NULL, "
                                    + "salary REAL NOT NULL"
                                    + ");";

        String createOrdersTable = "CREATE TABLE IF NOT EXISTS orders ("
                                 + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                                 + "customer TEXT NOT NULL, "
                                 + "itemid TEXT NOT NULL, "
                                 + "quantity INTEGER NOT NULL, "
                                 + "totalPrice REAL NOT NULL, "
                                 + "FOREIGN KEY(id) REFERENCES books(id)"
                                 + ");";

        String createToysTable = "CREATE TABLE IF NOT EXISTS toys ("
                                 + "id TEXT PRIMARY KEY, "
                                 + "name TEXT NOT NULL, "
                                 + "origin TEXT NOT NULL, "
                                 + "age_limit TEXT NOT NULL, "
                                 + "price REAL NOT NULL,"
                                 + "stock INTEGER NOT NULL"
                                 + ");";
        
        String createStationeriesTable = "CREATE TABLE IF NOT EXISTS stationeries ("
                                 + "id TEXT PRIMARY KEY, "
                                 + "brand TEXT NOT NULL, "
                                 + "name TEXT NOT NULL, "
                                 + "origin TEXT NOT NULL, "
                                 + "price REAL NOT NULL,"
                                 + "stock INTEGER NOT NULL"
                                 + ");";

        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
            stmt.execute(createBooksTable);
            stmt.execute(createAccountsTable);
            stmt.execute(createCustomersTable);
            stmt.execute(createEmployeesTable);
            stmt.execute(createOrdersTable);
            stmt.execute(createToysTable);
            stmt.execute(createStationeriesTable);
            System.out.println("Tables 'books', 'accounts', 'customers', 'employees', 'toys', 'stationeries' and 'orders' created or already exist.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean validateAccount(String username, String password) {
        String sql = "SELECT * FROM accounts WHERE username = ? AND password = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            return rs.next();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static void createAccount(String username, String password, String role) throws SQLException {
        String sql = "INSERT INTO accounts(username, password, role) VALUES(?, ?, ?)";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, role);
            pstmt.executeUpdate();
        } 
    }

    public static void updateAccount(int id, String username, String password, String role) throws SQLException {
        String sql = "UPDATE accounts SET username = ?, password = ?, role = ? WHERE id = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, role);
            pstmt.setInt(4, id);
            pstmt.executeUpdate();
        }
    }

    public static void deleteAccount(int id) throws SQLException {
        String sql = "DELETE FROM accounts WHERE id = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    public static ResultSet getAllAccounts() throws SQLException {
        String sql = "SELECT * FROM accounts";
        Connection conn = connect();
        return conn.createStatement().executeQuery(sql);
    }

    public static void createBook(String id, String title, String author, int year, String genre, String publisher, double price, int stock, double cost) throws SQLException {
        String sql = "INSERT INTO books(id, title, author, year, genre, publisher, price, stock, cost) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.setString(2, title);
            pstmt.setString(3, author);
            pstmt.setInt(4, year);
            pstmt.setString(5, genre);
            pstmt.setString(6, publisher);
            pstmt.setDouble(7, price);
            pstmt.setInt(8, stock);
            pstmt.setDouble(9, cost);
            pstmt.executeUpdate();
        }
    }

    public static void updateBook(String id, String title, String author, int year, String genre, String publisher, double price, int stock, double cost) throws SQLException {
        String sql = "UPDATE books SET title = ?, author = ?, price = ?, year = ?, genre = ?, publisher = ?, stock = ?, cost = ? WHERE id = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, title);
            pstmt.setString(2, author);
            pstmt.setDouble(3, price);
            pstmt.setInt(4, year);
            pstmt.setString(5, genre);
            pstmt.setString(6, publisher);
            pstmt.setInt(7, stock);
            pstmt.setDouble(8, cost);
            pstmt.setString(9, id);
            pstmt.executeUpdate();
        }
    }

    public static void deleteBook(String id) throws SQLException {
        String sql = "DELETE FROM books WHERE id = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.executeUpdate();
        }
    }

    public static ResultSet getAllBooks() throws SQLException {
        String sql = "SELECT * FROM books";
        Connection conn = connect();
        return conn.createStatement().executeQuery(sql);
    }

    public static boolean isBookIdExists(String bookId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM books WHERE id = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, bookId);
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            return rs.getInt(1) > 0;
        }
    }

    public static String generateUniqueBookId() throws SQLException {
        Random random = new Random();
        String bookId;
        do {
            bookId = String.format("978-0-%05d-%03d-%d", 
                                   random.nextInt(100000), 
                                   random.nextInt(1000), 
                                   random.nextInt(10));
        } while (isBookIdExists(bookId));
        return bookId;
    }

    public static void createCustomer(String name, String email, String phone) throws SQLException {
        String sql = "INSERT INTO customers(name, email, phone) VALUES(?, ?, ?)";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.setString(3, phone);
            pstmt.executeUpdate();
        }
    }

    public static void updateCustomer(int id, String name, String email, String phone) throws SQLException {
        String sql = "UPDATE customers SET name = ?, email = ?, phone = ? WHERE id = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.setString(3, phone);
            pstmt.setInt(4, id);
            pstmt.executeUpdate();
        }
    }

    public static void deleteCustomer(int id) throws SQLException {
        String sql = "DELETE FROM customers WHERE id = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    public static ResultSet getAllCustomers() throws SQLException {
        String sql = "SELECT * FROM customers";
        Connection conn = connect();
        return conn.createStatement().executeQuery(sql);
    }
    
    public static void createEmployee(String name, String position, double salary) throws SQLException {
        String sql = "INSERT INTO employees(name, position, salary) VALUES(?, ?, ?)";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, position);
            pstmt.setDouble(3, salary);
            pstmt.executeUpdate();
        }
    }

    public static void updateEmployee(int id, String name, String position, double salary) throws SQLException {
        String sql = "UPDATE employees SET name = ?, position = ?, salary = ? WHERE id = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, position);
            pstmt.setDouble(3, salary);
            pstmt.setInt(4, id);
            pstmt.executeUpdate();
        }
    }

    public static void deleteEmployee(int id) throws SQLException {
        String sql = "DELETE FROM employees WHERE id = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    public static ResultSet getAllEmployees() throws SQLException {
        String sql = "SELECT * FROM employees";
        Connection conn = connect();
        return conn.createStatement().executeQuery(sql);
    }

    public static void updateOrder(int id, String customer, String itemid, int quantity, double totalPrice) throws SQLException {
        String deleteSql = "DELETE FROM orders WHERE id = ?";
        String insertSql = "INSERT INTO orders(id, customer, itemid, quantity, totalPrice, timeCreated) VALUES(?, ?, ?, ?, ?, datetime('now'))";
        try (Connection conn = connect(); 
            PreparedStatement deletePstmt = conn.prepareStatement(deleteSql);
            PreparedStatement insertPstmt = conn.prepareStatement(insertSql)) {
            deletePstmt.setInt(1, id);
            deletePstmt.executeUpdate();

            insertPstmt.setInt(1, id);
            insertPstmt.setString(2, customer);
            insertPstmt.setString(3, itemid);
            insertPstmt.setInt(4, quantity);
            insertPstmt.setDouble(5, totalPrice);
            insertPstmt.executeUpdate();
        }
    }

    public static void deleteOrder(int id) throws SQLException {
        String sql = "DELETE FROM orders WHERE id = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    public static ResultSet getAllOrders() throws SQLException {
        String sql = "SELECT * FROM orders";
        Connection conn = connect();
        return conn.createStatement().executeQuery(sql);
    }

    public static double getBookPrice(String bookTitle) throws SQLException {
        String sql = "SELECT price FROM books WHERE title = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, bookTitle);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("price");
            } else {
                throw new SQLException("Book not found");
            }
        }
    }
    
    public static double getBookPriceById(String itemid) throws SQLException {
        String sql = "SELECT price FROM books WHERE id = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, itemid);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("price");
            } else {
                throw new SQLException("Book not found");
            }
        }
    }

    public static String getUserRole(String username) {
        String sql = "SELECT role FROM accounts WHERE username = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("role");
            } else {
                throw new SQLException("User not found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void createToy(String id, String name, String origin, String ageLimit, double price, int stock, double cost) throws SQLException {
        String sql = "INSERT INTO toys(id, name, origin, age_limit, price, stock, cost) VALUES(?, ?, ?, ?, ? ,?, ?)";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.setString(2, name);
            pstmt.setString(3, origin);
            pstmt.setString(4, ageLimit);
            pstmt.setDouble(5, price);
            pstmt.setInt(6, stock);
            pstmt.setDouble(7, cost);
            pstmt.executeUpdate();
        }
    }

    public static void deleteToy(String id) throws SQLException {
        String sql = "DELETE FROM toys WHERE id = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.executeUpdate();
        }
    }

    public static String generateUniqueToyId() throws SQLException {
        Random random = new Random();
        String toyId;
        do {
            toyId = String.format("TOY-%04d-%c", random.nextInt(10000), (char) (random.nextInt(26) + 'A'));
        } while (isToyIdExists(toyId));
        return toyId;
    }
    
    private static boolean isToyIdExists(String toyId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM toys WHERE id = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, toyId);
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            return rs.getInt(1) > 0;
        }
    }

    public static String generateUniqueStationeryId() throws SQLException {
        Random random = new Random();
        String stationeryId;
        do {
            stationeryId = String.format("ST-%04d-%c", random.nextInt(10000), (char) (random.nextInt(26) + 'A'));
        } while (isStationeryIdExists(stationeryId));
        return stationeryId;
    }

    private static boolean isStationeryIdExists(String stationeryId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM stationeries WHERE id = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, stationeryId);
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            return rs.getInt(1) > 0;
        }
    }

    public static ResultSet getAllToys() throws SQLException {
        String sql = "SELECT * FROM toys";
        Connection conn = connect();
        return conn.createStatement().executeQuery(sql);
    }

    public static ResultSet getAllBills() throws SQLException {

        Connection connection = DriverManager.getConnection("jdbc:your_database_url", "username", "password");

        Statement statement = connection.createStatement();

        return statement.executeQuery("SELECT * FROM bills");

    }

    public static String getBookTitleById(String itemid) throws SQLException {
        String sql = "SELECT title FROM books WHERE id = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, itemid);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("title");
            } else {
                throw new SQLException("Book not found");
            }
        }
    }

    public static ResultSet getOrdersByCustomer(String customer) throws SQLException {
        Connection conn = connect();
        String query = "SELECT * FROM orders WHERE customer = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, customer);
        return stmt.executeQuery();
    }

    public static double getTotalRevenue() throws SQLException {
        String sql = "SELECT SUM(totalPrice) FROM orders";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble(1);
            }
            return 0;
        }
    }

    public static double getTotalCost() throws SQLException {
        String sql = "SELECT SUM(price * quantity) FROM books";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble(1);
            }
            return 0;
        }
    }

    public static double getOperationalCost() throws SQLException {
        String sql = "SELECT SUM(salary) FROM employees";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble(1);
            }
            return 0;
        }
    }  

    public static double getBookRevenue() throws SQLException {
        String sql = "SELECT SUM(price * 1.20) FROM books";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble(1);
            }
            return 0;
        }
    }

    public static double getToyRevenue() throws SQLException {
        String sql = "SELECT SUM(price * 1.35) FROM toys";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble(1);
            }
            return 0;
        }
    }

    public static double getStationaryRevenue() throws SQLException {
        String sql = "SELECT SUM(price * 1.10) FROM stationeries";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble(1);
            }
            return 0;
        }
    }

    public static ResultSet getOrdersByCustomerAndTime(String customer, String timeCreated) throws SQLException {
        String sql = "SELECT * FROM orders WHERE customer = ? AND timeCreated = ?";
        Connection conn = connect();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, customer);
        pstmt.setString(2, timeCreated);
        return pstmt.executeQuery();
    }


    public static ResultSet getMostRecentOrdersByCustomer(String customer) throws SQLException {
        String sql = "SELECT * FROM orders WHERE customer = ? ORDER BY timeCreated DESC";
        Connection conn = connect();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, customer);
        return pstmt.executeQuery();
    }

    public static void createStationery(String id, String brand, String name, String origin, double price, int stock, double cost) throws SQLException {
        String sql = "INSERT INTO stationeries(id, brand, name, origin, price, stock, cost) VALUES(?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.setString(2, brand);
            pstmt.setString(3, name);
            pstmt.setString(4, origin);
            pstmt.setDouble(5, price);
            pstmt.setInt(6, stock);
            pstmt.setDouble(7, cost);
            pstmt.executeUpdate();
        }
    }

    public static void deleteStationery(String id) throws SQLException {
        String sql = "DELETE FROM stationeries WHERE id = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.executeUpdate();
        }
    }

    public static ResultSet getAllStationeries() throws SQLException {
        String sql = "SELECT * FROM stationeries";
        Connection conn = connect();
        return conn.createStatement().executeQuery(sql);
    }

    
    public static double getItemPriceById(String itemid) throws SQLException {
        String sql = "SELECT price FROM books WHERE id = ? UNION SELECT price FROM stationeries WHERE id = ? UNION SELECT price FROM toys WHERE id = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, itemid);
            pstmt.setString(2, itemid);
            pstmt.setString(3, itemid);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("price");
            }
        }
        return 0.0;
    }


    public static void addOrder(String customer, String itemid, int quantity, double totalPrice) throws SQLException {
        String sql = "INSERT INTO orders(customer, itemid, quantity, totalPrice, timeCreated) VALUES(?, ?, ?, ?, datetime('now'))";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, customer);
            pstmt.setString(2, itemid);
            pstmt.setInt(3, quantity);
            pstmt.setDouble(4, totalPrice);
            pstmt.executeUpdate();
        }
    }

    public static String getItemTitleById(String itemid) throws SQLException {
        String sql = "SELECT title FROM books WHERE id = ? UNION SELECT name FROM stationeries WHERE id = ? UNION SELECT name FROM toys WHERE id = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, itemid);
            pstmt.setString(2, itemid);
            pstmt.setString(3, itemid);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("title");
            }
        }
        return null;
    }

    public static boolean isCustomerExistsByPhone(String phone) throws SQLException {
        String sql = "SELECT COUNT(*) FROM customers WHERE phone = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, phone);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    public static ResultSet getLatestOrderTimeByCustomer(String customer) throws SQLException {
        String query = "SELECT MAX(timeCreated) AS latest_time FROM orders WHERE customer = ?";
        Connection connection = connect();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, customer);
        return preparedStatement.executeQuery();
    }
}
