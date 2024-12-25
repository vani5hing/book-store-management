package capybara.bookstoremanagement;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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

public class ManageStationeriesController extends ManageController {
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
    @FXML
    private TableColumn<Stationery, Integer> colStock;
    @FXML
    private TextField searchField;

    private FilteredList<Stationery> filteredStationeries;

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colBrand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colOrigin.setCellValueFactory(new PropertyValueFactory<>("origin"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colStock.setCellValueFactory(new PropertyValueFactory<>("stock"));

        load();
    }

    public void load() {
        try {
            ResultSet rs = DatabaseUtil.getAllStationeries();
            ObservableList<Stationery> stationeryList = FXCollections.observableArrayList();
            while (rs.next()) {
                stationeryList.add(new Stationery(rs.getString("id"), rs.getString("brand"), rs.getString("name"), rs.getString("origin"), rs.getDouble("price"), rs.getInt("stock"), rs.getDouble("cost")));
            }
            filteredStationeries = new FilteredList<>(stationeryList, p -> true);
            SortedList<Stationery> sortedStationeries = new SortedList<>(filteredStationeries);
            sortedStationeries.comparatorProperty().bind(tableView.comparatorProperty());
            tableView.setItems(sortedStationeries);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleSearch() {
        String searchText = searchField.getText().toLowerCase();
        filteredStationeries.setPredicate(stationery -> {
            if (searchText == null || searchText.isEmpty()) {
                return true;
            }
            return stationery.getName().toLowerCase().contains(searchText) ||
                   stationery.getBrand().toLowerCase().contains(searchText) ||
                   stationery.getId().toLowerCase().contains(searchText);
        });
    }

    @FXML
    public void handleAdd(ActionEvent event) {
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
        TextField stockField = new TextField();
        stockField.setPromptText("Stock");
        TextField costField = new TextField();
        costField.setPromptText("Cost");

        grid.add(new Label("Brand:"), 0, 0);
        grid.add(brandField, 1, 0);
        grid.add(new Label("Name:"), 0, 1);
        grid.add(nameField, 1, 1);
        grid.add(new Label("Origin:"), 0, 2);
        grid.add(originField, 1, 2);
        grid.add(new Label("Price:"), 0, 3);
        grid.add(priceField, 1, 3);
        grid.add(new Label("Stock:"), 0, 4);
        grid.add(stockField, 1, 4);
        grid.add(new Label("Cost:"), 0, 5);
        grid.add(costField, 1, 5);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButtonType) {
                try {
                    String id = DatabaseUtil.generateUniqueStationeryId();
                    String brand = brandField.getText();
                    String name = nameField.getText();
                    String origin = originField.getText();
                    double price = Double.parseDouble(priceField.getText());
                    int stock = Integer.parseInt(stockField.getText());
                    double cost = Double.parseDouble(costField.getText());
                    return new Stationery(id, brand, name, origin, price, stock, cost);
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
                DatabaseUtil.createStationery(stationery.getId(), stationery.getBrand(), stationery.getName(), stationery.getOrigin(), stationery.getPrice(), stationery.getStock(), stationery.getCost());
                load();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    public void handleDelete(ActionEvent event) {
        Stationery selectedStationery = tableView.getSelectionModel().getSelectedItem();
        if (selectedStationery != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this stationery?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.YES) {
                    try {
                        DatabaseUtil.deleteStationery(selectedStationery.getId());
                        load();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    @FXML
    public void handleReturnToMenu(ActionEvent event) {
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