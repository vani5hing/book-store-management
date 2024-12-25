package capybara.bookstoremanagement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

public class ManageOrdersController {
    private String previousView;

    public void setPreviousView(String previousView) {
        this.previousView = previousView;
    }

    @FXML
    private TableView<Order> tableView;
    @FXML
    private TableColumn<Order, Integer> colId;
    @FXML
    private TableColumn<Order, String> colCustomer;
    @FXML
    private TableColumn<Order, String> colItemid;
    @FXML
    private TableColumn<Order, Integer> colQuantity;
    @FXML
    private TableColumn<Order, Double> colTotalPrice;
    @FXML
    private TableColumn<Order, String> colTimeCreated;


    private void loadOrders() {
        tableView.getItems().clear();
        try {
            ResultSet rs = DatabaseUtil.getAllOrders();
            while (rs.next()) {
                int id = rs.getInt("id");
                String customer = rs.getString("customer");
                String itemid = rs.getString("itemid");
                int quantity = rs.getInt("quantity");
                double totalPrice = Math.round(rs.getDouble("totalPrice") * 10.0) / 10.0;
                String timeCreated = rs.getString("timeCreated");

                Order order = new Order(id, customer, itemid, quantity, totalPrice, timeCreated);
                tableView.getItems().add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCustomer.setCellValueFactory(new PropertyValueFactory<>("customer"));
        colItemid.setCellValueFactory(new PropertyValueFactory<>("itemid"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colTotalPrice.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        colTimeCreated.setCellValueFactory(new PropertyValueFactory<>("timeCreated"));

        loadOrders();
    }


    @FXML
    private void handleEditOrder(ActionEvent event) {
        Order selectedOrder = tableView.getSelectionModel().getSelectedItem();
        if (selectedOrder != null) {
            Dialog<Order> dialog = new Dialog<>();
            dialog.setTitle("Edit Order");
            dialog.setHeaderText("Edit the details of the order");

            ButtonType editButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(editButtonType, ButtonType.CANCEL);

            VBox vbox = new VBox(10);

            TextField customerField = new TextField(selectedOrder.getCustomer());
            vbox.getChildren().add(new Label("Customer:"));
            vbox.getChildren().add(customerField);

            TextField itemidField = new TextField(selectedOrder.getItemid());
            TextField quantityField = new TextField(String.valueOf(selectedOrder.getQuantity()));

            Label totalPriceLabel = new Label(String.format("Total Price: €%.2f", selectedOrder.getTotalPrice()).replace('.', ','));
            vbox.getChildren().add(totalPriceLabel);

            itemidField.textProperty().addListener((observable, oldValue, newValue) -> calculateTotalPrice(itemidField, quantityField, totalPriceLabel));
            quantityField.textProperty().addListener((observable, oldValue, newValue) -> calculateTotalPrice(itemidField, quantityField, totalPriceLabel));

            GridPane itemGrid = new GridPane();
            itemGrid.setHgap(10);
            itemGrid.setVgap(10);
            itemGrid.add(new Label("Item ID:"), 0, 1);
            itemGrid.add(itemidField, 1, 1);
            itemGrid.add(new Label("Quantity:"), 2, 1);
            itemGrid.add(quantityField, 3, 1);

            vbox.getChildren().add(itemGrid);

            ScrollPane scrollPane = new ScrollPane(vbox);
            scrollPane.setFitToWidth(true);
            dialog.getDialogPane().setContent(scrollPane);

            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == editButtonType) {
                    String customer = customerField.getText();
                    String itemid = itemidField.getText();
                    int quantity = Integer.parseInt(quantityField.getText());
                    double totalPrice = calculateTotalPrice(itemid, quantity);
                    return new Order(selectedOrder.getId(), customer, itemid, quantity, totalPrice, selectedOrder.getTimeCreated());
                }
                return null;
            });

            Optional<Order> result = dialog.showAndWait();
            result.ifPresent(order -> {
                try {
                    DatabaseUtil.updateOrder(order.getId(), order.getCustomer(), order.getItemid(), order.getQuantity(), order.getTotalPrice());
                    loadOrders();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    @FXML
    private void handleDeleteOrder(ActionEvent event) {
        Order selectedOrder = tableView.getSelectionModel().getSelectedItem();
        if (selectedOrder != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this order?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.YES) {
                    try {
                        DatabaseUtil.deleteOrder(selectedOrder.getId());
                        loadOrders();
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

    @FXML
    private void handleGenerateBill(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Generate Bill");
        dialog.setHeaderText("Enter the customer name and time created to generate the bill");
        dialog.setContentText("Customer:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(customer -> {
            TextInputDialog timeDialog = new TextInputDialog();
            timeDialog.setTitle("Generate Bill");
            timeDialog.setHeaderText("Enter the time created to generate the bill");
            timeDialog.setContentText("Time Created:");

            Optional<String> timeResult = timeDialog.showAndWait();
            timeResult.ifPresent(timeCreated -> {
                try {
                    Bill bill = generateBillForCustomer(customer, timeCreated);
                    displayBill(bill);
                } catch (SQLException | IOException e) {
                    e.printStackTrace();
                }
            });
        });
    }

    private Bill generateBillForCustomer(String customer, String timeCreated) throws SQLException {
        ResultSet rs = DatabaseUtil.getOrdersByCustomerAndTime(customer, timeCreated);
        Map<String, Integer> items = new HashMap<>();
        Map<String, String> itemTitles = new HashMap<>();
        double totalPrice = 0.0;

        while (rs.next()) {
            String itemid = rs.getString("itemid");
            int quantity = rs.getInt("quantity");
            items.put(itemid, items.getOrDefault(itemid, 0) + quantity);
            itemTitles.put(itemid, DatabaseUtil.getItemTitleById(itemid));
            totalPrice += DatabaseUtil.getItemPriceById(itemid) * quantity;
        }

        return new Bill(customer, items, itemTitles, totalPrice, timeCreated);
    }

    private void displayBill(Bill bill) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("bill_view.fxml"));
        Parent root = loader.load();

        BillViewController controller = loader.getController();
        controller.setBill(bill);

        Stage stage = new Stage();
        stage.setTitle("Bill for " + bill.getCustomer());
        stage.setScene(new Scene(root));
        stage.show();
    }

    private double calculateTotalPrice(Map<String, Integer> items) {
        double totalPrice = 0.0;
        for (Map.Entry<String, Integer> entry : items.entrySet()) {
            try {
                double price = DatabaseUtil.getItemPriceById(entry.getKey());
                totalPrice += price * entry.getValue();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return totalPrice;
    }

    private double calculateTotalPrice(String itemid, int quantity) {
        double totalPrice = 0.0;
        try {
            double price = DatabaseUtil.getItemPriceById(itemid);
            totalPrice = price * quantity;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalPrice;
    }

    private void calculateTotalPrice(TextField itemidField, TextField quantityField, Label totalPriceLabel) {
        double totalPrice = 0.0;
        try {
            String itemid = itemidField.getText();
            int quantity = Integer.parseInt(quantityField.getText());
            double price = DatabaseUtil.getItemPriceById(itemid);
            totalPrice = price * quantity;
        } catch (NumberFormatException | SQLException e) {
            // Handle exception
        }
        totalPriceLabel.setText(String.format("Total Price: €%.2f", totalPrice).replace('.', ','));
    }

    private double parsePrice(String priceString) {
        try {
            NumberFormat format = NumberFormat.getInstance(Locale.GERMANY);
            Number number = format.parse(priceString);
            return number.doubleValue();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0.0;
        }
    }

    
private void calculateTotalPrice(Map<TextField, TextField> itemFields, Label totalPriceLabel) {
    double totalPrice = 0.0;
    for (Map.Entry<TextField, TextField> entry : itemFields.entrySet()) {
        try {
            String itemid = entry.getKey().getText();
            int quantity = Integer.parseInt(entry.getValue().getText());
            double price = DatabaseUtil.getItemPriceById(itemid);
            totalPrice += price * quantity;
        } catch (NumberFormatException | SQLException e) {
            // Handle exception
        }
    }
    totalPriceLabel.setText(String.format("Total Price: $%.2f", totalPrice).replace('.', ','));
}

@FXML
private void handleAddOrder(ActionEvent event) {
    TextInputDialog phoneDialog = new TextInputDialog();
    phoneDialog.setTitle("Enter Customer Phone");
    phoneDialog.setHeaderText("Enter the customer's phone number:");
    phoneDialog.setContentText("Phone:");

    Optional<String> phoneResult = phoneDialog.showAndWait();
    phoneResult.ifPresent(phone -> {
        try {
            if (!DatabaseUtil.isCustomerExistsByPhone(phone)) {
                // Show error message if customer does not exist
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Customer Not Found");
                alert.setHeaderText(null);
                alert.setContentText("The customer with the provided phone number does not exist.");
                alert.showAndWait();
            } else {
                // Continue processing the order
                openAddOrderDialog(phone);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    });
}

private void openAddOrderDialog(String phone) {
    Dialog<Order> dialog = new Dialog<>();
    dialog.setTitle("Add New Order");
    dialog.setHeaderText("Enter the details of the new order");

    ButtonType addButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
    dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

    VBox vbox = new VBox(10);

    TextField customerPhoneField = new TextField(phone);
    customerPhoneField.setEditable(false);
    vbox.getChildren().add(new Label("Customer Phone:"));
    vbox.getChildren().add(customerPhoneField);

    // Create a map to store item fields
    Map<TextField, TextField> itemFields = new HashMap<>();
    final int[] row = {1};

    TextField itemidField = new TextField();
    itemidField.setPromptText("Item ID");
    TextField quantityField = new TextField();
    quantityField.setPromptText("Quantity");

    Label totalPriceLabel = new Label("Total Price: €0,00");
    vbox.getChildren().add(totalPriceLabel);

    itemidField.textProperty().addListener((observable, oldValue, newValue) -> calculateTotalPrice(itemFields, totalPriceLabel));
    quantityField.textProperty().addListener((observable, oldValue, newValue) -> calculateTotalPrice(itemFields, totalPriceLabel));

    GridPane itemGrid = new GridPane();
    itemGrid.setHgap(10);
    itemGrid.setVgap(10);
    itemGrid.add(new Label("Item ID:"), 0, row[0]);
    itemGrid.add(itemidField, 1, row[0]);
    itemGrid.add(new Label("Quantity:"), 2, row[0]);
    itemGrid.add(quantityField, 3, row[0]);

    itemFields.put(itemidField, quantityField);

    Button addItemButton = new Button("Add Item");
    vbox.getChildren().add(itemGrid);
    vbox.getChildren().add(addItemButton);

    addItemButton.setOnAction(e -> {
        row[0]++;
        TextField newItemidField = new TextField();
        newItemidField.setPromptText("Item ID");
        TextField newQuantityField = new TextField();
        newQuantityField.setPromptText("Quantity");

        newItemidField.textProperty().addListener((observable, oldValue, newValue) -> calculateTotalPrice(itemFields, totalPriceLabel));
        newQuantityField.textProperty().addListener((observable, oldValue, newValue) -> calculateTotalPrice(itemFields, totalPriceLabel));

        itemGrid.add(new Label("Item ID:"), 0, row[0]);
        itemGrid.add(newItemidField, 1, row[0]);
        itemGrid.add(new Label("Quantity:"), 2, row[0]);
        itemGrid.add(newQuantityField, 3, row[0]);

        itemFields.put(newItemidField, newQuantityField);
    });

    ScrollPane scrollPane = new ScrollPane(vbox);
    scrollPane.setFitToWidth(true);
    dialog.getDialogPane().setContent(scrollPane);

    dialog.setResultConverter(dialogButton -> {
        if (dialogButton == addButtonType) {
            Map<String, Integer> items = new HashMap<>();
            for (Map.Entry<TextField, TextField> entry : itemFields.entrySet()) {
                String itemid = entry.getKey().getText();
                int quantity = Integer.parseInt(entry.getValue().getText());
                items.put(itemid, quantity);
            }
            return new Order(0, phone, null, 0, 0.0, java.time.LocalDateTime.now().toString());
        }
        return null;
    });

    Optional<Order> result = dialog.showAndWait();
    result.ifPresent(order -> {
        try {
            for (Map.Entry<TextField, TextField> entry : itemFields.entrySet()) {
                String itemid = entry.getKey().getText();
                int quantity = Integer.parseInt(entry.getValue().getText());
                double itemTotalPrice = DatabaseUtil.getItemPriceById(itemid) * quantity;
                DatabaseUtil.addOrder(order.getCustomer(), itemid, quantity, itemTotalPrice);
            }
            loadOrders();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    });
}
}