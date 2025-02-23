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

public class ManageToysController extends ManageController {
    @FXML
    private TableView<Toy> tableView;
    @FXML
    private TableColumn<Toy, Integer> colId;
    @FXML
    private TableColumn<Toy, String> colName;
    @FXML
    private TableColumn<Toy, String> colOrigin;
    @FXML
    private TableColumn<Toy, String> colAgeLimit;
    @FXML
    private TableColumn<Toy, Double> colPrice;
    @FXML
    private TableColumn<Toy, Integer> colStock;
    @FXML
    private TextField searchField;

    private FilteredList<Toy> filteredToys;

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colOrigin.setCellValueFactory(new PropertyValueFactory<>("origin"));
        colAgeLimit.setCellValueFactory(new PropertyValueFactory<>("ageLimit"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colStock.setCellValueFactory(new PropertyValueFactory<>("stock"));

        load();
    }

    public void load() {
        try {
            ResultSet rs = DatabaseUtil.getAllToys();
            ObservableList<Toy> toyList = FXCollections.observableArrayList();
            while (rs.next()) {
                toyList.add(new Toy(rs.getString("id"), rs.getString("name"), rs.getString("origin"), rs.getString("age_limit"), rs.getDouble("price"), rs.getInt("stock"), rs.getDouble("cost")));
            }
            filteredToys = new FilteredList<>(toyList, p -> true);
            SortedList<Toy> sortedToys = new SortedList<>(filteredToys);
            sortedToys.comparatorProperty().bind(tableView.comparatorProperty());
            tableView.setItems(sortedToys);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleSearch() {
        String searchText = searchField.getText().toLowerCase();
        filteredToys.setPredicate(toy -> {
            if (searchText == null || searchText.isEmpty()) {
                return true;
            }
            return toy.getName().toLowerCase().contains(searchText) ||
                   toy.getOrigin().toLowerCase().contains(searchText) ||
                   toy.getId().toLowerCase().contains(searchText);
        });
    }

    @FXML
    public void handleAdd(ActionEvent event) {
        Dialog<Toy> dialog = new Dialog<>();
        dialog.setTitle("Add New Toy");
        dialog.setHeaderText("Enter the details of the new toy");

        ButtonType addButtonType = new ButtonType("Add", ButtonType.OK.getButtonData());
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField nameField = new TextField();
        nameField.setPromptText("Name");
        TextField originField = new TextField();
        originField.setPromptText("Origin");
        TextField ageLimitField = new TextField();
        ageLimitField.setPromptText("Age Limit");
        TextField priceField = new TextField();
        priceField.setPromptText("Price");
        TextField stockField = new TextField();
        stockField.setPromptText("Stock");
        TextField costField = new TextField();
        costField.setPromptText("Cost");

        grid.add(new Label("Name:"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(new Label("Origin:"), 0, 1);
        grid.add(originField, 1, 1);
        grid.add(new Label("Age Limit:"), 0, 2);
        grid.add(ageLimitField, 1, 2);
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
                    String id = DatabaseUtil.generateUniqueToyId();
                    String name = nameField.getText();
                    String origin = originField.getText();
                    String ageLimit = ageLimitField.getText();
                    double price = Double.parseDouble(priceField.getText());
                    int stock = Integer.parseInt(stockField.getText());
                    double cost = Double.parseDouble(costField.getText());
                    return new Toy(id, name, origin, ageLimit, price, stock, cost);
                } catch (SQLException e) {
                    e.printStackTrace();
                    return null;
                }
            }
            return null;
        });

        Optional<Toy> result = dialog.showAndWait();
        result.ifPresent(toy -> {
            try {
                DatabaseUtil.createToy(toy.getId(), toy.getName(), toy.getOrigin(),toy.getAgeLimit() ,toy.getPrice(), toy.getStock(), toy.getCost());
                load();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    public void handleDelete(ActionEvent event) {
        Toy selectedToy = tableView.getSelectionModel().getSelectedItem();
        if (selectedToy != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this toy?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.YES) {
                    try {
                        DatabaseUtil.deleteToy(selectedToy.getId());
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
            stage.setScene(new Scene(root, 1080, 640));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // @FXML
    // private void handleCalculateRevenue(ActionEvent event) {
    //     try {
    //         double toyRevenue = DatabaseUtil.getToyRevenue();
    //         Alert alert = new Alert(Alert.AlertType.INFORMATION, "Total Toy Revenue: $" + toyRevenue, ButtonType.OK);
    //         alert.showAndWait();
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //     }
    // }
}
