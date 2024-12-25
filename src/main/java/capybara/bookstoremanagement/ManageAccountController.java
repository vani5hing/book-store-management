package capybara.bookstoremanagement;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ManageAccountController {

    private String previousView;

    public void setPreviousView(String previousView) {
        this.previousView = previousView;
    }

    @FXML
    private TableView<Account> tableView;
    @FXML
    private TableColumn<Account, Integer> colId;
    @FXML
    private TableColumn<Account, String> colUsername;
    @FXML
    private TableColumn<Account, String> colPassword;
    @FXML
    private TableColumn<Account, String> colRole;

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        colRole.setCellValueFactory(new PropertyValueFactory<>("role"));

        loadAccounts();
    }

    private void loadAccounts() {
        tableView.getItems().clear();
        try {
            ResultSet rs = DatabaseUtil.getAllAccounts();
            while (rs.next()) {
                tableView.getItems().add(new Account(rs.getInt("id"), rs.getString("username"), rs.getString("password"), rs.getString("role")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAddAccount(ActionEvent event) {
        Dialog<Account> dialog = new Dialog<>();
        dialog.setTitle("Add New Account");
        dialog.setHeaderText("Enter the details of the new account");

        // Set the button types
        ButtonType addButtonType = new ButtonType("Add", ButtonType.OK.getButtonData());
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

        // Create the name, position, and salary labels and fields
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        TextField passwordField = new TextField();
        passwordField.setPromptText("Password");
        TextField roleField = new TextField();
        roleField.setPromptText("Role");

        grid.add(new Label("Username:"), 0, 0);
        grid.add(usernameField, 1, 0);
        grid.add(new Label("Password:"), 0, 1);
        grid.add(passwordField, 1, 1);
        grid.add(new Label("Role:"), 0, 2);
        grid.add(roleField, 1, 2);

        dialog.getDialogPane().setContent(grid);

        // Convert the result to an Employee object when the Add button is clicked
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButtonType) {
                String username = usernameField.getText();
                String password = passwordField.getText();
                String role = roleField.getText();
                return new Account(0, username, password, role);
            }
            return null;
        });

        Optional<Account> result = dialog.showAndWait();
        result.ifPresent(account -> {
            try {
                DatabaseUtil.createAccount(account.getUsername(), account.getPassword(), account.getRole());
                loadAccounts();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    private void handleEditAccount(ActionEvent event) {
        Account selectedAccount = tableView.getSelectionModel().getSelectedItem();
        if (selectedAccount != null) {
            Dialog<Account> dialog = new Dialog<>();
            dialog.setTitle("Edit Account");
            dialog.setHeaderText("Edit the details of the account");

            // Set the button types
            ButtonType editButtonType = new ButtonType("Save", ButtonType.OK.getButtonData());
            dialog.getDialogPane().getButtonTypes().addAll(editButtonType, ButtonType.CANCEL);

            // Create the name, position, and salary labels and fields
            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);

            TextField usernameField = new TextField(selectedAccount.getUsername());
            TextField passwordField = new TextField(selectedAccount.getPassword());
            TextField roleField = new TextField(selectedAccount.getRole());

            grid.add(new Label("Username:"), 0, 0);
            grid.add(usernameField, 1, 0);
            grid.add(new Label("Password:"), 0, 1);
            grid.add(passwordField, 1, 1);
            grid.add(new Label("Role:"), 0, 2);
            grid.add(roleField, 1, 2);

            dialog.getDialogPane().setContent(grid);

            // Convert the result to an Employee object when the Save button is clicked
            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == editButtonType) {
                    String username = usernameField.getText();
                    String password = passwordField.getText();
                    String role = roleField.getText();
                    return new Account(selectedAccount.getId(), username, password, role);
                }
                return null;
            });

            Optional<Account> result = dialog.showAndWait();
            result.ifPresent(account -> {
                try {
                    DatabaseUtil.updateAccount(account.getId(), account.getUsername(), account.getPassword(), account.getRole());
                    loadAccounts();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    @FXML
    private void handleDeleteAccount(ActionEvent event) {
        Account selectedAccount = tableView.getSelectionModel().getSelectedItem();
        if (selectedAccount != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this account?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.YES) {
                    try {
                        DatabaseUtil.deleteAccount(selectedAccount.getId());
                        loadAccounts();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    @FXML
    private void handleReturnToMenu(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(previousView + ".fxml"));
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 1080, 640));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}