package Controller;

import Model.Values;
import com.jfoenix.controls.JFXButton;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by MedTaki on 27/03/2018.
 */
public class MainViewController extends Application implements Initializable{
    @FXML
    private AnchorPane mainView;

    @FXML
    private JFXButton Notification;

    @FXML
    private VBox LeftMenue;

    @FXML

    private AnchorPane body;

    @FXML
    private JFXButton gestionDesEtudiant;

    @FXML
    private JFXButton messagrie;

    @FXML
    private JFXButton deconnect;

    @FXML
    private JFXButton gestionTravaille;


    @FXML
    private Pane ConfirmPane;

    @FXML
    private JFXButton annluer;

    @FXML
    private JFXButton yseDelete;

    @FXML
    private Label usernameMainView;

    public Label getUsernameMainView() {
        return usernameMainView;
    }

    public void setUsernameMainView(String usernameMainView) {
        this.usernameMainView.setText(usernameMainView);
    }

    @FXML
    void gestionDeTravaille(ActionEvent event) {
        Values.messagerieController = null;
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("../View/GestionTravaille.fxml"));
            body.getChildren().setAll(root);
            gestionTravaille.setStyle("-fx-background-color : #3fafbd");
            gestionDesEtudiant.setStyle("-fx-background-color : #028090");
            messagrie.setStyle("-fx-background-color : #028090");
//            body.getChildren().add(root);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void getstionMessagerie(ActionEvent event) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("../View/Messagerie.fxml"));
            body.getChildren().setAll(root);
            gestionTravaille.setStyle("-fx-background-color : #028090");
            gestionDesEtudiant.setStyle("-fx-background-color : #028090");
            messagrie.setStyle("-fx-background-color : #3fafbd");
//            body.getChildren().add(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void uploadGestionDesEtudiant(ActionEvent event) {
        Values.messagerieController = null;
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("../View/Gestion de Sceance.fxml"));
            body.getChildren().setAll(root);
            gestionTravaille.setStyle("-fx-background-color : #028090");
            gestionDesEtudiant.setStyle("-fx-background-color : #028090");
            messagrie.setStyle("-fx-background-color : #3fafbd");
//            body.getChildren().add(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void logout(ActionEvent event) {
        ConfirmPane.setLayoutX(200);
        mainView.setDisable(true);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../View/Loginlaunch.fxml"));
        primaryStage.setTitle("CMS-Enseignant");
        primaryStage.setScene(new Scene(root, 515, 607));
        primaryStage.setResizable(false);
        primaryStage.show();
    }



    @FXML
    void AnnulerDéconnecté(ActionEvent event) {
        ConfirmPane.setLayoutX(900);
        mainView.setDisable(false);
    }

    @FXML
    void Déconnectez(ActionEvent event) {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
        try {
            start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        usernameMainView.setText(Values.getEnseignant().getUsername());
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("../View/Gestion des groupes.fxml"));
            body.getChildren().setAll(root);
            gestionTravaille.setStyle("-fx-background-color : #028090");
            gestionDesEtudiant.setStyle("-fx-background-color : #3fafbd");
            messagrie.setStyle("-fx-background-color : #028090");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Values.setMainViewController(this);
        LeftMenue.setDisable(true);
    }

    public VBox getLeftMenue() {
        return LeftMenue;
    }

    public void setLeftMenue(VBox leftMenue) {
        LeftMenue = leftMenue;
    }

    @FXML
    void LoadNotification(ActionEvent event) {
        Pane root = null;
        FXMLLoader messagerieLoader = new FXMLLoader();
        try {
            root = messagerieLoader.load(getClass().getResource("/View/NotificationView.fxml").openStream());
            body.getChildren().setAll(root);
//            body.getChildren().add(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
