// Giorgio Latour
// Publisher App for Quotations
// IHRTLUHC
package viewerappquotes;

import data.ViewerDAO;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FXMLLoginWindowController implements Initializable {

    ViewerDAO model;

    @FXML
    private PasswordField password;
    @FXML
    private TextField username;
    @FXML
    private Button loginbutton;
    @FXML
    private Button skipbutton;
    @FXML
    private Label couldNotLogIn;

    public void setModel(ViewerDAO model) {
        this.model = model;
    }

    @FXML
    private void handleLogin(ActionEvent event) {
        // Code to handle login event.
        if (model.logIn(username.getText(), password.getText())) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLDocument.fxml"));
                Parent root = (Parent) loader.load();

                FXMLDocumentController controller = (FXMLDocumentController) loader.getController();
                controller.setModel(model);
                controller.setComboBox();
                
                Scene scene = new Scene(root);
                Stage stage = new Stage();

                stage.setScene(scene);
                stage.setResizable(false);
                stage.setTitle("Quotations Viewer Utility");
                stage.show();
                loginbutton.getScene().getWindow().hide();
            } catch (Exception ex) {
                System.out.println("Could not log in to utility.");
                ex.printStackTrace();
            }
        } else {
            couldNotLogIn.setText("Error: Could not log in.");
        }
    }
    
    @FXML
    private void skipLogin(ActionEvent event){
         try {
                model.connectDB();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLDocument.fxml"));
                Parent root = (Parent) loader.load();

                FXMLDocumentController controller = (FXMLDocumentController) loader.getController();
                controller.setModel(model);
                controller.setComboBox();
                
                Scene scene = new Scene(root);
                Stage stage = new Stage();

                stage.setScene(scene);
                stage.setResizable(false);
                stage.setTitle("Quotations Viewer Utility");
                stage.show();
                loginbutton.getScene().getWindow().hide();
            } catch (Exception ex) {
                System.out.println("Could not log in to utility.");
                ex.printStackTrace();
            }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
