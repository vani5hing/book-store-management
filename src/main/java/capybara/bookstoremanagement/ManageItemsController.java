package capybara.bookstoremanagement;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ManageItemsController {

    @FXML
    private void handleManageBooks(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("manage_books.fxml"));
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 640, 540));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
            Parent root = FXMLLoader.load(getClass().getResource("menu.fxml"));
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 640, 540));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}