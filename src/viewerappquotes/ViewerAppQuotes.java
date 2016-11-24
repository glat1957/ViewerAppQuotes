// Giorgio Latour
// Viewer App for Quotations
// IHRTLUHC
package viewerappquotes;

import data.ViewerDAO;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class ViewerAppQuotes extends Application {
    
    @FXML
    TextArea textArea;
    @FXML
    Button previous;
    @FXML
    Button next;
    @FXML
    Button Reccommend;
    @FXML
    Label submitter;
    @FXML
    ChoiceBox choiceBox;
    
    // Need to implement Reccommend counter in order to order quotes correctly, removal of users 
    // removes their quotes and Reccommends, and changes Reccommends to Reccommendations.
    
    
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLLoginWindow.fxml"));
        Parent root = (Parent) loader.load();

        FXMLLoginWindowController controller = (FXMLLoginWindowController) loader.getController();
        ViewerDAO dao = new ViewerDAO();
        controller.setModel(dao);

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Viewer Utility Login");
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
