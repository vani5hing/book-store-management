package capybara.bookstoremanagement;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void handleLoginAction() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (isValidCredentials(username, password)) {
            String role = getUserRole(username);
            switch (role) {
                case "admin":
                    navigateToView("admin_view");
                    break;
                case "manager":
                    navigateToView("manager_view");
                    break;
                case "employee":
                    navigateToView("employee_view");
                    break;
                default:
                    showAlert(Alert.AlertType.ERROR, "Login Failed", "Unknown user role.");
                    break;
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid username or password.");
        }
    }

    @FXML
    private void handleNewUser() {
        try {
            App.setRoot("signup");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isValidCredentials(String username, String password) {
        return DatabaseUtil.validateUser(username, password);
    }

    private String getUserRole(String username) {
        return DatabaseUtil.getUserRole(username);
    }

    private void navigateToView(String viewName) {
        try {
            App.setRoot(viewName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
