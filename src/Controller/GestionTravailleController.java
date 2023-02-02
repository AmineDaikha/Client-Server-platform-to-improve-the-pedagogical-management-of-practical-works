package Controller;


import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Amine Daikha on 10/05/2018.
 */
public class GestionTravailleController implements Initializable{

    @FXML
    private AnchorPane body;

    @FXML
    private JFXButton Projets;

    @FXML
    private JFXButton TPs;

    @FXML
    private AnchorPane AnchorPaneOf;


    @FXML
    void gestionProjets(ActionEvent event) {

        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("../View/GestionProjets.fxml"));
            AnchorPaneOf.getChildren().setAll(root);
            Projets.setDisable(true);
            TPs.setDisable(false);
//            body.getChildren().add(root);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @FXML
    void gestionTPs(ActionEvent event) {

        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("../View/GestionTPs.fxml"));
            AnchorPaneOf.getChildren().setAll(root);
            Projets.setDisable(false);
            TPs.setDisable(true);
//            body.getChildren().add(root);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("../View/GestionTPs.fxml"));
            AnchorPaneOf.getChildren().setAll(root);
            TPs.setDisable(true);
//            body.getChildren().add(root);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
