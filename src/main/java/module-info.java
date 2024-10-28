module capybara.bookstoremanagement {
    requires javafx.controls;
    requires javafx.fxml;

    opens capybara.bookstoremanagement to javafx.fxml;
    exports capybara.bookstoremanagement;
}
