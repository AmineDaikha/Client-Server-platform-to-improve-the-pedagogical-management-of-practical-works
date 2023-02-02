package Controller;

import Controller.DAObjects.EnseignantDAO;
import Controller.DAObjects.UtilisateurDAO;
import Model.Enseignant;
import Model.Etudiant;
import Model.Utilisateur;
import Model.Values;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.sun.javafx.runtime.async.BackgroundExecutor;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by MedTaki on 05/03/2018.
 */
public class LoginController extends Application implements Initializable{


    @FXML
    private AnchorPane LoginForm;


    @FXML
    private TextField connectionPort;

//    @FXML
//    private JFXTextField usernamefield;
//
//    @FXML
//    private JFXPasswordField passwordfield;
//
//    @FXML
//    private JFXButton login;

//    @FXML
//    private JFXButton createaccount;
    private UtilisateurDAO utilisateurDAO = new UtilisateurDAO();
    private EnseignantDAO enseignantDAO = new EnseignantDAO();
    @FXML
    private TextField usernamefield;

    @FXML
    private PasswordField passwordfield;

    @FXML
    private Button login;

    @FXML
    private Button forgetPassword;

    @FXML
    private Button createaccount;


    @FXML
    private Label dataError;


    @FXML
    private Pane rpass;

    @FXML
    private JFXTextField numCarteField;

    @FXML
    private JFXPasswordField newpasswordfield;

    @FXML
    private JFXButton Sauvgarder;



    @FXML
    private JFXButton Back;

    @FXML
    void BacktoLogin(ActionEvent event) {
        rpass.setLayoutX(560);
        LoginForm.setDisable(false);
        LoginForm.setOpacity(1);
    }



    @FXML
    void changerMotDePasse(ActionEvent event) {
        Enseignant e = new Enseignant();
        e.setPassword(newpasswordfield.getText());
        e.setIdEnseignant(Integer.parseInt(numCarteField.getText()));
        Enseignant e2 = enseignantDAO.readEnseignantByIdEnseignant(e);
        e.setId(e2.getId());
        UtilisateurDAO utilisateurDAO = new UtilisateurDAO();
        utilisateurDAO.updatePassword(e);
        rpass.setLayoutX(560);
        LoginForm.setDisable(false);
        LoginForm.setOpacity(1);
    }

    @FXML
    void creecompte(ActionEvent event) {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        try {//TODO load the Create account Stage
            Parent root = FXMLLoader.load(getClass().getResource("../View/CreateAccountEnseignant.fxml"));
            Scene scene = new Scene(root, 450, 490);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("CMS-Create Account (Enseignant)");
        }catch (Exception e){
            System.out.println(e.toString());
            e.printStackTrace();
        }
    }


//    TODO verification utillisateur avec la base des donnée

    @FXML
    void ovrireSession(ActionEvent event) {
        if (connectionPort.getText().length()<= 5) {
            Utilisateur actif = new Utilisateur();
            actif.setUsername(usernamefield.getText().toString());
            actif.setPassword(passwordfield.getText().toString());
            Utilisateur utilisateur = utilisateurDAO.readUser(actif);
            if (utilisateur != null) {
                Enseignant isEnseignant =  enseignantDAO.readEnseignantById(utilisateur);
                if(isEnseignant.getIdEnseignant() != -1){
                    if (actif.getPassword().toLowerCase().equals(utilisateur.getPassword().toLowerCase())) {
                        Node node = (Node) event.getSource();
                        try {
                            Values.port = Integer.parseInt(connectionPort.getText());
                            ServerSocket serverSocket = null;
                            try {
                                serverSocket = new ServerSocket(Values.port);
                                ServerSocket fileServerSocket1 = new ServerSocket(2018);
                                Values.setServerSocket(serverSocket);
                                Values.setFileServerSocket(fileServerSocket1);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Stage stage = (Stage) node.getScene().getWindow();
                            Values.getEnseignant().setUsername(utilisateur.getUsername());
                            Values.getEnseignant().setPassword(utilisateur.getPassword());
                            Values.setEnseignant(enseignantDAO.readEnseignant(Values.getEnseignant()));
                            System.out.println(Values.getEnseignant());
                            stage.close();
                            try {
                                start(stage);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            try {
                                Values.getServerTCPConnectionThread().start();
                            } catch (Exception e) {
                                //TODO Hundel the login
                            }

                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                            dataError.setText("input invalide");
                        }

                    }else {
                        dataError.setText("Nom utilisateur ou mot de passe incorrect");
                    }
                }else {
                    dataError.setText("Nom utilisateur ou mot de passe incorrect");
                }
            } else {
                dataError.setText("compte n'existe pas");
            }
        } else {
            dataError.setText("input invalid!!");
        }
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../View/MainView.fxml"));
        primaryStage.setTitle("CMS-Enseignant");
        primaryStage.setScene(new Scene(root, 709, 480));
        primaryStage.setResizable(false);
        //TODO shut down the thread
        primaryStage.setOnCloseRequest(e->{
            Values.killApp();
            try {
                try {
                    Values.getServerTCPConnectionThread().sleep(500);
                    Values.getServerTCPConnectionThread().shutdown();
                    Values.getServerSocket().close();
                    Values.getTimeThread().shutdown();
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
           } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        primaryStage.close();
        primaryStage.show();
    }

    @FXML
    void reinitialiserMotDePasse(ActionEvent event) {
        rpass.setLayoutX(120);
        LoginForm.setDisable(true);
        LoginForm.setOpacity(0.5);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
