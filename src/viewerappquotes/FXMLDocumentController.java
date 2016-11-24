// Giorgio Latour
// Viewer App for Quotations
// IHRTLUHC
package viewerappquotes;

import data.ViewerDAO;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;

public class FXMLDocumentController implements Initializable {

    ViewerDAO model;
    ArrayList<String> quotesList;
    int quoteIndex;
    ResultSet temp;

    @FXML
    private Label viewerStatus;
    @FXML
    private ComboBox<String> comboBox;
    @FXML
    private TextArea textArea;
    @FXML
    private Button nextbutton;
    @FXML
    private Button previousbutton;
    @FXML
    private Button Likebutton;
    @FXML
    private Label submitter;
    
    public void setModel(ViewerDAO model) {
        this.model = model;
    }

    @FXML
    public void setComboBox() {
        comboBox.getItems().addAll(model.getCategories());
    }

    @FXML
    private void handleQuoteDisplay(ActionEvent event) {
        quotesList = model.getQuotesList(getChoice());
        quoteIndex = 0;
        textArea.setText(quotesList.get(quoteIndex));
        viewerStatus.setText("");
        setSubmitter();
    }

    @FXML
    private void handleNextQuote(ActionEvent event) {
        quotesList = model.getQuotesList(getChoice());
        if (quoteIndex == (model.getQuotesList(getChoice()).size() - 1)) {
            quoteIndex = -1;
        }
        quoteIndex++;

        textArea.setText(quotesList.get(quoteIndex));
        viewerStatus.setText("");
        setSubmitter();
    }

    @FXML
    private void handlePreviousQuote(ActionEvent event) {
        quotesList = model.getQuotesList(getChoice());

        if (quoteIndex == 0) {
            quoteIndex = model.getQuotesList(getChoice()).size() - 1;
        } else {
            quoteIndex--;
        }

        textArea.setText(quotesList.get(quoteIndex));
        viewerStatus.setText("");
        setSubmitter();
    }
    

    @FXML
    private void handleLikeQuote(ActionEvent event) {
        if (model.getCurrentUser() == null) {
            viewerStatus.setTextFill(Color.RED);
            viewerStatus.setText("Not logged in!");
        } else if (textArea.getText().equalsIgnoreCase("") || textArea.getText().equalsIgnoreCase("No quotes yet!")) {
            viewerStatus.setText("No quote to like!");
        } else {
            String status = model.establishLike(
                    model.getQuoteNum(quotesList.get(quoteIndex)), model.getCurrentUser());
            viewerStatus.setText(status);
            model.updateReccommendsCount(model.getQuoteNum(quotesList.get(quoteIndex)));

        }
    }

    public String getChoice() {
        int comboBoxIndex = comboBox.getSelectionModel().getSelectedIndex();
        String category = model.getCategories().get(comboBoxIndex);
        return category;
    }

    public void setSubmitter() {
        submitter.setText("Submitted by " + model.getQuoteSubmitter(model.getQuoteNum(textArea.getText())));
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
