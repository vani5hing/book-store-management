module org.bookstoremanagement {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.bookstoremanagement to javafx.fxml;
    exports org.bookstoremanagement;
}
