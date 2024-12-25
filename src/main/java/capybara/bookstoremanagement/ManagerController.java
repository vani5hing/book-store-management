package capybara.bookstoremanagement;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

public class ManagerController {

    @FXML
    private VBox vbox;

    @FXML
    private void handleManageEmployees(ActionEvent event) {
        navigateToViewWithPrevious(event, "manage_employees", "manager_view");
    }

    @FXML
    private void handleManageItems(ActionEvent event) {
        navigateToViewWithPrevious(event, "manage_items", "manager_view");
    }

    @FXML
    private void handleManageOrders(ActionEvent event) {
        navigateToViewWithPrevious(event, "manage_orders", "manager_view");
    }

    @FXML
    private void handleFinancialAnalysis(ActionEvent event) {
        navigateToViewWithPrevious(event, "financial_report", "manager_view");
    }

    @FXML
    private void handleLogOut(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/capybara/bookstoremanagement/login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 1080, 640));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void navigateToViewWithPrevious(ActionEvent event, String viewName, String previousView) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/capybara/bookstoremanagement/" + viewName + ".fxml"));
            Parent root = loader.load();
            Object controller = loader.getController();
            if (controller instanceof ManageEmployeesController) {
                ((ManageEmployeesController) controller).setPreviousView(previousView);
            } else if (controller instanceof ManageItemsController) {
                ((ManageItemsController) controller).setPreviousView(previousView);
            } else if (controller instanceof ManageOrdersController) {
                ((ManageOrdersController) controller).setPreviousView(previousView);
            } else if (controller instanceof FinancialReportController) {
                ((FinancialReportController) controller).setPreviousView(previousView);
            }
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 1080, 640));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}