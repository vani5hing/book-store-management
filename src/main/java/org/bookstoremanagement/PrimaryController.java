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

        if (isValidCredentials(username, password)) {
            showAlert(AlertType.INFORMATION, "Login Successful", "Welcome, " + username + "!");
        } else {
            showAlert(AlertType.ERROR, "Login Failed", "Invalid username or password.");
        }
    }

    @FXML
    private void handleSignUpAction() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Implement sign-up logic here
        showAlert(AlertType.INFORMATION, "Sign Up", "Sign up successful for user: " + username);
    }

    private boolean isValidCredentials(String username, String password) {
        // Replace with actual validation logic
        return "user".equals(username) && "pass".equals(password);
    }

    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}