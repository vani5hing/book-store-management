package capybara.bookstoremanagement;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

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
                    navigateToAdminView(username);
                    break;
                case "manager":
                    navigateToManagerView(username);
                    break;
                case "employee":
                    navigateToEmployeeView(username);
                    break;
                default:
                    showAlert(Alert.AlertType.ERROR, "Login Failed", "Unknown user role.");
                    break;
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid username or password.");
        }
    }

    private void navigateToAdminView(String username) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("admin_view.fxml"));
            Parent root = loader.load();
            // AdminController controller = loader.getController();
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(new Scene(root, 1080, 640));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void navigateToManagerView(String username) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("manager_view.fxml"));
            Parent root = loader.load();
            // ManagerController controller = loader.getController();
            // controller.setUsername(username);
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(new Scene(root, 1080, 640));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void navigateToEmployeeView(String username) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("employee_view.fxml"));
            Parent root = loader.load();
            // EmployeeController controller = loader.getController();
            // controller.setUsername(username);
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(new Scene(root, 1080, 640));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
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

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
