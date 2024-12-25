package capybara.bookstoremanagement;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ManageItemsController {

    private String previousView;

    public void setPreviousView(String previousView) {
        this.previousView = previousView;
    }

    @FXML
    private void handleManageBooks(ActionEvent event) {
        navigateToViewWithPrevious(event, "manage_books", previousView);
    }

    @FXML
    private void handleManageToys(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("manage_toys.fxml"));
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 640, 540));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleReturnToMenu(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(previousView + ".fxml"));
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 640, 540));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void navigateToViewWithPrevious(ActionEvent event, String viewName, String previousView) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(viewName + ".fxml"));
            Parent root = loader.load();
            ManageBooksController controller = loader.getController();
            controller.setPreviousView("manage_items");
            controller.setPreviousViewOfManageItems(previousView);
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 640, 540));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}