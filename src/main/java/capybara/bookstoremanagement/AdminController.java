package capybara.bookstoremanagement;


import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class AdminController {

    @FXML
    private void handleManageEmployees(ActionEvent event) {
        navigateToView(event, "manage_employees");
    }

    @FXML
    private void handleManageItems(ActionEvent event) {
        navigateToView(event, "manage_items");
    }

    @FXML
    private void handleManageCustomers(ActionEvent event) {
        navigateToView(event, "manage_customers");
    }

    @FXML
    private void handleManageOrders(ActionEvent event) {
        navigateToView(event, "manage_orders");
    }

    private void navigateToView(ActionEvent event, String viewName) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(viewName + ".fxml"));
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 640, 540));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}