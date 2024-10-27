package org.bookstoremanagement;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class PrimaryController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void handleLoginAction() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (DatabaseUtil.validateUser(username, password)) {
            showAlert(AlertType.INFORMATION, "Login Successful", "Welcome, " + username + "!");
        } else {
            showAlert(AlertType.ERROR, "Login Failed", "Invalid username or password.");
        }
    }

    @FXML
    private void handleSignUpAction() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (DatabaseUtil.addUser(username, password)) {
            showAlert(AlertType.INFORMATION, "Sign Up Successful", "User " + username + " has been registered.");
        } else {
            showAlert(AlertType.ERROR, "Sign Up Failed", "Could not register user.");
        }
    }

    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}