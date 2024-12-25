package capybara.bookstoremanagement;

import javafx.event.ActionEvent;

abstract class ManageController {
    public String previousView;
    public String previousViewOfManageItems;

    public void setPreviousView(String previousView) {
        this.previousView = previousView;
    }

    public void setPreviousViewOfManageItems(String previousViewOfManageItems) {
        this.previousViewOfManageItems = previousViewOfManageItems;
    }

    public abstract void initialize();
    public abstract void load();
    public abstract void handleAdd(ActionEvent event);
    public abstract void handleDelete(ActionEvent event);
    public abstract void handleReturnToMenu(ActionEvent event);
}
