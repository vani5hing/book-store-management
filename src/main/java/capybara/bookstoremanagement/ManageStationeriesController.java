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

public class ManageStationeriesController {
    @FXML
    private TableView<Stationery> tableView;
    @FXML
    private TableColumn<Stationery, Integer> colId;
    @FXML
    private TableColumn<Stationery, String> colBrand;
    @FXML
    private TableColumn<Stationery, String> colName;
    @FXML
    private TableColumn<Stationery, String> colOrigin;
    @FXML
    private TableColumn<Stationery, Double> colPrice;

    private String previousView;
    private String previousViewOfManageItems;

    public void setPreviousView(String previousView) {
        this.previousView = previousView;
    }

    public void setPreviousViewOfManageItems(String previousViewOfManageItems) {
        this.previousViewOfManageItems = previousViewOfManageItems;
    }

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colBrand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colOrigin.setCellValueFactory(new PropertyValueFactory<>("origin"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        loadStationeries();
    }

    private void loadStationeries() {
        tableView.getItems().clear();
        try {
            ResultSet rs = DatabaseUtil.getAllStationeries();
            while (rs.next()) {
                tableView.getItems().add(new Stationery(rs.getString("id"), rs.getString("brand"), rs.getString("name"), rs.getString("origin"), rs.getDouble("price")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAddStationery(ActionEvent event) {
        Dialog<Stationery> dialog = new Dialog<>();
        dialog.setTitle("Add New Stationery");
        dialog.setHeaderText("Enter the details of the new stationery");

        ButtonType addButtonType = new ButtonType("Add", ButtonType.OK.getButtonData());
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField brandField = new TextField();
        brandField.setPromptText("Brand");
        TextField nameField = new TextField();
        nameField.setPromptText("Name");
        TextField originField = new TextField();
        originField.setPromptText("Origin");
        TextField priceField = new TextField();
        priceField.setPromptText("Price");

        grid.add(new Label("Brand:"), 0, 0);
        grid.add(brandField, 1, 0);
        grid.add(new Label("Name:"), 0, 1);
        grid.add(nameField, 1, 1);
        grid.add(new Label("Origin:"), 0, 2);
        grid.add(originField, 1, 2);
        grid.add(new Label("Price:"), 0, 3);
        grid.add(priceField, 1, 3);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButtonType) {
                try {
                    String id = DatabaseUtil.generateUniqueStationeryId();
                    String brand = brandField.getText();
                    String name = nameField.getText();
                    String origin = originField.getText();
                    double price = Double.parseDouble(priceField.getText());
                    return new Stationery(id, brand, name, origin, price);
                } catch (SQLException e) {
                        e.printStackTrace();
                        return null;
                }
            }
            return null;
        });

        Optional<Stationery> result = dialog.showAndWait();
        result.ifPresent(stationery -> {
            try {
                DatabaseUtil.createStationery(stationery.getId(), stationery.getBrand(), stationery.getName(), stationery.getOrigin(), stationery.getPrice());
                loadStationeries();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    private void handleDeleteStationery(ActionEvent event) {
        Stationery selectedStationery = tableView.getSelectionModel().getSelectedItem();
        if (selectedStationery != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this stationery?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.YES) {
                    try {
                        DatabaseUtil.deleteStationery(selectedStationery.getId());
                        loadStationeries();
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource(previousView + ".fxml"));
            Parent root = loader.load();
            ManageItemsController controller = loader.getController();
            controller.setPreviousView(previousViewOfManageItems);
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 640, 540));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}