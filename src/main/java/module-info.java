module capybara.bookstoremanagement {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.base;
    requires javafx.graphics;
  
    

    opens capybara.bookstoremanagement to javafx.fxml;
    exports capybara.bookstoremanagement;
}
