package capybara.bookstoremanagement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class FinancialReportController {

    @FXML
    private PieChart pieChart;

    @FXML
    private BarChart<String, Number> barChart;

    @FXML
    private Label revenueLabel;

    @FXML
    private Label grossProfitLabel;

    @FXML
    private Label netProfitLabel;

    private FinancialReport report;

    private String previousView;

    public void setPreviousView(String previousView) {
        this.previousView = previousView;
    }

    public void initialize() {
        List<Map<String, Object>> booksData = FinancialReport.loadTable("books");
        List<Map<String, Object>> customersData = FinancialReport.loadTable("customers");
        List<Map<String, Object>> ordersData = FinancialReport.loadTable("orders");
        List<Map<String, Object>> stationData = FinancialReport.loadTable("stationeries");
        List<Map<String, Object>> toysData = FinancialReport.loadTable("toys");

        report = new FinancialReport(booksData, customersData, ordersData, stationData, toysData);

        double revenue = report.calculateRevenue();
        double grossProfit = report.calculateGrossProfit();
        double netProfit = report.calculateNetProfit(5000, 2000, 1000, 500, 800, 1500, 200);

        revenueLabel.setText(String.format("Revenue: $%.2f", revenue));
        grossProfitLabel.setText(String.format("Gross Profit: $%.2f", grossProfit));
        netProfitLabel.setText(String.format("Net Profit: $%.2f", netProfit));

        pieChart.getData().add(new PieChart.Data("Revenue", revenue));
        pieChart.getData().add(new PieChart.Data("Gross Profit", grossProfit));
        pieChart.getData().add(new PieChart.Data("Net Profit", netProfit));

        XYChart.Series<String, Number> dataSeries = new XYChart.Series<>();
        dataSeries.setName("Financial Overview");
        dataSeries.getData().add(new XYChart.Data<>("Revenue", revenue));
        dataSeries.getData().add(new XYChart.Data<>("Gross Profit", grossProfit));
        dataSeries.getData().add(new XYChart.Data<>("Net Profit", netProfit));

        barChart.getData().add(dataSeries);
    }

    @FXML
    private void handleReturnToMenu(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(previousView + ".fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 1080, 640));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
