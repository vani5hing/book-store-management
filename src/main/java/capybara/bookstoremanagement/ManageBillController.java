package capybara.bookstoremanagement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


public class ManageBillController {

    @FXML
    private TableView<Bill> tableView;
    @FXML
    private TableColumn<Bill, String> colCustomer;
    @FXML
    private TableColumn<Bill, String> colBooks;
    @FXML
    private TableColumn<Bill, Double> colTotalPrice;
    @FXML
    private TableColumn<Bill, String> colTimeCreated;

    @FXML
    public void initialize() {
        colCustomer.setCellValueFactory(new PropertyValueFactory<>("customer"));
        colBooks.setCellValueFactory(new PropertyValueFactory<>("booksFormatted"));
        colTotalPrice.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        colTimeCreated.setCellValueFactory(new PropertyValueFactory<>("timeCreated"));

        loadBills();
    }

    private void loadBills() {
        tableView.getItems().clear();
        try {
            ResultSet rs = DatabaseUtil.getAllBills();
            while (rs.next()) {
                Map<String, Integer> books = new HashMap<>();
                Map<String, String> bookTitles = new HashMap<>();
                String customer = rs.getString("customer");
                String timeCreated = rs.getString("timeCreated");
                double totalPrice = rs.getDouble("totalPrice");

                // Populate books and bookTitles maps
                ResultSet orderItems = DatabaseUtil.getOrdersByCustomerAndTime(customer, timeCreated);
                while (orderItems.next()) {
                    String itemid = orderItems.getString("itemid");
                    int quantity = orderItems.getInt("quantity");
                    books.put(itemid, quantity);
                    bookTitles.put(itemid, DatabaseUtil.getBookTitleById(itemid));
                }

                Bill bill = new Bill(customer, books, bookTitles, totalPrice, timeCreated);
                tableView.getItems().add(bill);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleGenerateBill() throws SQLException {
        // try {
            {String customer = getSelectedCustomer();
            Bill bill = generateBillForCustomer(customer);
            saveBillToFile(bill);
            displayBill(bill);}
        // } catch (SQLException | IOException e) {
            // e.printStackTrace();
        // }
    }

    private Bill generateBillForCustomer(String customer) throws SQLException {
        ResultSet rs = DatabaseUtil.getMostRecentOrdersByCustomer(customer);
        Map<String, Integer> books = new HashMap<>();
        Map<String, String> bookTitles = new HashMap<>();
        double totalPrice = 0.0;
        String timeCreated = null;

        while (rs.next()) {
            String itemid = rs.getString("itemid");
            int quantity = rs.getInt("quantity");
            books.put(itemid, books.getOrDefault(itemid, 0) + quantity);
            bookTitles.put(itemid, DatabaseUtil.getBookTitleById(itemid));
            totalPrice += DatabaseUtil.getBookPriceById(itemid) * quantity;
            if (timeCreated == null) {
                timeCreated = rs.getString("timeCreated");
            }
        }

        return new Bill(customer, books, bookTitles, totalPrice, timeCreated);
    }

    private void saveBillToFile(Bill bill) {
        // Implementation for saving bill to file
    }

    private void displayBill(Bill bill) {
        // Implementation for displaying bill
    }

    private String getSelectedCustomer() {
        // Implementation for getting selected customer
        return "selectedCustomer";
    }
}