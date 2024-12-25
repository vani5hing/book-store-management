package capybara.bookstoremanagement;

import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class FinancialReportController {

    @FXML
    private Label totalRevenueLabel;
    @FXML
    private Label totalCostLabel;
    @FXML
    private Label grossProfitLabel;
    @FXML
    private Label netProfitLabel;
    @FXML
    private Label profitMarginLabel;

    private String previousView;

    public void setPreviousView(String previousView) {
        this.previousView = previousView;
    }

    @FXML
    public void initialize() {
        try {
            double bookRevenue = DatabaseUtil.getBookRevenue();
            double toyRevenue = DatabaseUtil.getToyRevenue();
            double stationaryRevenue = DatabaseUtil.getStationaryRevenue();
            double totalRevenue = bookRevenue + toyRevenue + stationaryRevenue;

            double totalCost = DatabaseUtil.getTotalCost();
            double operationalCost = DatabaseUtil.getOperationalCost();

            FinancialReport report = new FinancialReport(totalRevenue, totalCost, operationalCost);

            totalRevenueLabel.setText(String.format("Total Revenue: $%.2f", report.getTotalRevenue()));
            totalCostLabel.setText(String.format("Total Cost: $%.2f", report.getTotalCost()));
            grossProfitLabel.setText(String.format("Gross Profit: $%.2f", report.getGrossProfit()));
            netProfitLabel.setText(String.format("Net Profit: $%.2f", report.getNetProfit()));
            profitMarginLabel.setText(String.format("Profit Margin: %.2f%%", report.getProfitMargin()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}