package capybara.bookstoremanagement;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FinancialReport {

    private List<Map<String, Object>> books;
    private List<Map<String, Object>> customers;
    private List<Map<String, Object>> orders;
    private List<Map<String, Object>> station;
    private List<Map<String, Object>> toys;

    public FinancialReport(List<Map<String, Object>> books, List<Map<String, Object>> customers, List<Map<String, Object>> orders, List<Map<String, Object>> station, List<Map<String, Object>> toys) {
        this.books = books;
        this.customers = customers;
        this.orders = orders;
        this.station = station;
        this.toys = toys;
    }

    public double calculateRevenue() {
        return orders.stream()
                .mapToDouble(order -> (double) order.get("totalPrice"))
                .sum();
    }

    public double calculateCostOfGoodsSold() {
        double booksCost = orders.stream()
                .filter(order -> isBook((String) order.get("itemid")))
                .mapToDouble(order -> findItemCost(books, (String) order.get("itemid")) * (int) order.get("quantity"))
                .sum();

        double toysCost = orders.stream()
                .filter(order -> isToy((String) order.get("itemid")))
                .mapToDouble(order -> findItemCost(toys, (String) order.get("itemid")) * (int) order.get("quantity"))
                .sum();

        double stationCost = orders.stream()
                .filter(order -> isStation((String) order.get("itemid")))
                .mapToDouble(order -> findItemCost(station, (String) order.get("itemid")) * (int) order.get("quantity"))
                .sum();

        return booksCost + toysCost + stationCost;
    }

    public double calculateGrossProfit() {
        return calculateRevenue() - calculateCostOfGoodsSold();
    }

    public double calculateNetProfit(double salary, double rent, double utilities, double packaging, double delivery, double taxes, double interest) {
        double operationalCosts = salary + rent + utilities + packaging + delivery;
        return calculateGrossProfit() - operationalCosts - taxes - interest;
    }

    public double calculateProfitMargin() {
        double revenue = calculateRevenue();
        return revenue > 0 ? (calculateNetProfit(0, 0, 0, 0, 0, 0, 0) / revenue) * 100 : 0;
    }

    public double calculateTotalOrderValue() {
        return orders.stream()
                .mapToDouble(order -> (double) order.get("totalPrice"))
                .sum();
    }

    public static List<Map<String, Object>> loadTable(String tableName) {
        List<Map<String, Object>> data = new ArrayList<>();
        String url = "jdbc:sqlite:your_database_file_path_here";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM " + tableName)) {

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    row.put(metaData.getColumnName(i), rs.getObject(i));
                }
                data.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    private boolean isBook(String itemId) {
        return books.stream().anyMatch(book -> book.get("id").equals(itemId));
    }

    private boolean isToy(String itemId) {
        return toys.stream().anyMatch(toy -> toy.get("id").equals(itemId));
    }

    private boolean isStation(String itemId) {
        return station.stream().anyMatch(st -> st.get("id").equals(itemId));
    }

    private double findItemCost(List<Map<String, Object>> items, String itemId) {
        return items.stream()
                .filter(item -> item.get("id").equals(itemId))
                .mapToDouble(item -> (double) item.get("cost"))
                .findFirst()
                .orElse(0);
    }
}
