package capybara.bookstoremanagement;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class BillViewController {

    @FXML
    private Label customerLabel;
    @FXML
    private TextArea booksTextArea;
    @FXML
    private Label totalPriceLabel;
    @FXML
    private VBox billVBox;

    private Bill bill;

    public void setBill(Bill bill) {
        this.bill = bill;
        customerLabel.setText("Customer: " + bill.getCustomer());
        StringBuilder booksText = new StringBuilder();
        for (Map.Entry<String, Integer> entry : bill.getBooks().entrySet()) {
            String bookTitle = bill.getBookTitles().get(entry.getKey());
            booksText.append("itemid: ").append(entry.getKey()).append(", Title: ").append(bookTitle).append(", Quantity: ").append(entry.getValue()).append("\n");
        }
        booksTextArea.setText(booksText.toString());
        totalPriceLabel.setText("Total Price: $" + String.format("%.2f", bill.getTotalPrice()).replace('.', ','));
    }

    @FXML
    private void handleSaveAsPDF() {
        File billsDir = new File("bills");
        if (!billsDir.exists()) {
            billsDir.mkdir();
        }
        String sanitizedTimeCreated = bill.getTimeCreated().replace(":", "-").replace(" ", "_");
        String fileNameTxt = "bills/" + bill.getCustomer() + "_" + sanitizedTimeCreated + "_bill.txt";
        try (FileWriter writer = new FileWriter(fileNameTxt)) {
            writer.write("Bill for customer: " + bill.getCustomer() + "\n" +
                    "Time created: " + bill.getTimeCreated() + "\n" +
                    "Books:\n");
            for (Map.Entry<String, Integer> entry : bill.getBooks().entrySet()) {
                String bookTitle = bill.getBookTitles().get(entry.getKey());
                writer.write("ID: " + entry.getKey() + ", Title: " + bookTitle + ", Quantity: " + entry.getValue() + "\n");
            }
            writer.write("Total Price: $" + String.format("%.2f", bill.getTotalPrice()).replace('.', ',') + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
