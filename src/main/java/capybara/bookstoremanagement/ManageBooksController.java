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

public class ManageBooksController extends ManageController {

    @FXML
    private TableView<Book> tableView;
    @FXML
    private TableColumn<Book, String> colId;
    @FXML
    private TableColumn<Book, String> colTitle;
    @FXML
    private TableColumn<Book, String> colAuthor;
    @FXML
    private TableColumn<Book, Integer> colYear;
    @FXML
    private TableColumn<Book, String> colGenre;
    @FXML
    private TableColumn<Book, String> colPublisher;
    @FXML
    private TableColumn<Book, Double> colPrice;
    @FXML
    private TableColumn<Book, Integer> colStock;
    @FXML
    private TextField searchField;

    private FilteredList<Book> filteredBooks;

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        colYear.setCellValueFactory(new PropertyValueFactory<>("year"));
        colGenre.setCellValueFactory(new PropertyValueFactory<>("genre"));
        colPublisher.setCellValueFactory(new PropertyValueFactory<>("publisher"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colStock.setCellValueFactory(new PropertyValueFactory<>("stock"));

        load();
    }

    public void load() {
        try {
            ResultSet rs = DatabaseUtil.getAllBooks();
            ObservableList<Book> bookList = FXCollections.observableArrayList();
            while (rs.next()) {
                bookList.add(new Book(rs.getString("id"), rs.getString("title"), rs.getString("author"), rs.getInt("year"), rs.getString("genre"), rs.getString("publisher"), rs.getDouble("price"), rs.getInt("stock"), rs.getDouble("cost")));
            }
            filteredBooks = new FilteredList<>(bookList, p -> true);
            SortedList<Book> sortedBooks = new SortedList<>(filteredBooks);
            sortedBooks.comparatorProperty().bind(tableView.comparatorProperty());
            tableView.setItems(sortedBooks);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleSearch() {
        String searchText = searchField.getText().toLowerCase();
        filteredBooks.setPredicate(book -> {
            if (searchText == null || searchText.isEmpty()) {
                return true;
            }
            return book.getTitle().toLowerCase().contains(searchText) ||
                   book.getAuthor().toLowerCase().contains(searchText) ||
                   book.getId().toLowerCase().contains(searchText) ||
                   book.getGenre().toLowerCase().contains(searchText) ||
                   book.getPublisher().toLowerCase().contains(searchText);
        });
    }

    @FXML
    public void handleAdd(ActionEvent event) {
        Dialog<Book> dialog = new Dialog<>();
        dialog.setTitle("Add New Book");
        dialog.setHeaderText("Enter the details of the new book");

        ButtonType addButtonType = new ButtonType("Add", ButtonType.OK.getButtonData());
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField titleField = new TextField();
        titleField.setPromptText("Title");
        TextField authorField = new TextField();
        authorField.setPromptText("Author");
        TextField yearField = new TextField();
        yearField.setPromptText("Year");
        TextField genreField = new TextField();
        genreField.setPromptText("Genre");
        TextField publisherField = new TextField();
        publisherField.setPromptText("Publisher");
        TextField priceField = new TextField();
        priceField.setPromptText("Price");
        TextField stockField = new TextField();
        stockField.setPromptText("Stock");
        TextField costField = new TextField();
        costField.setPromptText("Cost");

        grid.add(new Label("Title:"), 0, 0);
        grid.add(titleField, 1, 0);
        grid.add(new Label("Author:"), 0, 1);
        grid.add(authorField, 1, 1);
        grid.add(new Label("Year:"), 0, 2);
        grid.add(yearField, 1, 2);
        grid.add(new Label("Genre:"), 0, 3);
        grid.add(genreField, 1, 3);
        grid.add(new Label("Publisher:"), 0, 4);
        grid.add(publisherField, 1, 4);
        grid.add(new Label("Price:"), 0, 5);
        grid.add(priceField, 1, 5);
        grid.add(new Label("Stock:"), 0, 6);
        grid.add(stockField, 1, 6);
        grid.add(new Label("Cost:"), 0, 7);
        grid.add(costField, 1, 7);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButtonType) {
                try {
                    String id = DatabaseUtil.generateUniqueBookId();
                    String title = titleField.getText();
                    String author = authorField.getText();
                    int year = Integer.parseInt(yearField.getText());
                    String genre = genreField.getText();
                    String publisher = publisherField.getText();
                    double price = Double.parseDouble(priceField.getText());
                    int stock = Integer.parseInt(stockField.getText());
                    double cost = Double.parseDouble(costField.getText());
                    return new Book(id, title, author, year, genre, publisher, price, stock, cost);
                } catch (SQLException e) {
                    e.printStackTrace();
                    return null;
                }
            }
            return null;
        });

        Optional<Book> result = dialog.showAndWait();
        result.ifPresent(book -> {
            try {
                DatabaseUtil.createBook(book.getId(), book.getTitle(), book.getAuthor(), book.getYear(), book.getGenre(), book.getPublisher(), book.getPrice(), book.getStock(), book.getCost());
                load();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    public void handleEditBook(ActionEvent event) {
        Book selectedBook = tableView.getSelectionModel().getSelectedItem();
        if (selectedBook != null) {
            Dialog<Book> dialog = new Dialog<>();
            dialog.setTitle("Edit Book");
            dialog.setHeaderText("Edit the details of the book");

            ButtonType editButtonType = new ButtonType("Save", ButtonType.OK.getButtonData());
            dialog.getDialogPane().getButtonTypes().addAll(editButtonType, ButtonType.CANCEL);

            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);

            TextField titleField = new TextField(selectedBook.getTitle());
            TextField authorField = new TextField(selectedBook.getAuthor());
            TextField yearField = new TextField(String.valueOf(selectedBook.getYear()));
            TextField genreField = new TextField(selectedBook.getGenre());
            TextField publisherField = new TextField(selectedBook.getPublisher());
            TextField priceField = new TextField(String.valueOf(selectedBook.getPrice()));
            TextField stockField = new TextField(String.valueOf(selectedBook.getStock()));
            TextField costField = new TextField(String.valueOf(selectedBook.getCost()));

            grid.add(new Label("Title:"), 0, 0);
            grid.add(titleField, 1, 0);
            grid.add(new Label("Author:"), 0, 1);
            grid.add(authorField, 1, 1);
            grid.add(new Label("Year:"), 0, 2);
            grid.add(yearField, 1, 2);
            grid.add(new Label("Genre:"), 0, 3);
            grid.add(genreField, 1, 3);
            grid.add(new Label("Publisher:"), 0, 4);
            grid.add(publisherField, 1, 4);
            grid.add(new Label("Price:"), 0, 5);
            grid.add(priceField, 1, 5);
            grid.add(new Label("Stock:"), 0, 6);
            grid.add(stockField, 1, 6);
            grid.add(new Label("Cost:"), 0, 7);
            grid.add(costField, 1, 7);

            dialog.getDialogPane().setContent(grid);

            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == editButtonType) {
                    String title = titleField.getText();
                    String author = authorField.getText();
                    int year = Integer.parseInt(yearField.getText());
                    String genre = genreField.getText();
                    String publisher = publisherField.getText();
                    double price = Double.parseDouble(priceField.getText());
                    int stock = Integer.parseInt(stockField.getText());
                    double cost = Double.parseDouble(costField.getText());
                    return new Book(selectedBook.getId(), title, author, year, genre, publisher, price, stock, cost);
                }
                return null;
            });

            Optional<Book> result = dialog.showAndWait();
            result.ifPresent(book -> {
                try {
                    DatabaseUtil.updateBook(book.getId(), book.getTitle(), book.getAuthor(), book.getYear(), book.getGenre(), book.getPublisher(), book.getPrice(), book.getStock(), book.getCost());
                    load();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    @FXML
    public void handleDelete(ActionEvent event) {
        Book selectedBook = tableView.getSelectionModel().getSelectedItem();
        if (selectedBook != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this book?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.YES) {
                    try {
                        DatabaseUtil.deleteBook(selectedBook.getId());
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
}