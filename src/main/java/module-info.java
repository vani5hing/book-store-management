module capybara.bookstoremanagement {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens capybara.bookstoremanagement to javafx.fxml;
    exports capybara.bookstoremanagement;
}
