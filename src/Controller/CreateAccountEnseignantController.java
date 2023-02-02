package Controller;


import Controller.DAObjects.EnseignantDAO;
import Model.Enseignant;
import Model.Values;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.NumberValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

/**
 * Created by MedTaki on 09/03/2018.
 */
public class CreateAccountEnseignantController extends Application implements Initializable{
    private EnseignantDAO enseignantDAO = new EnseignantDAO();
    private Enseignant enseignant;
    @FXML
    private JFXTextField Name;

    @FXML
    private RequiredFieldValidator validator;

    @FXML
    private JFXTextField LastName;

    @FXML
    private RequiredFieldValidator validator2;

    @FXML
    private JFXTextField IDEnseignant;

    @FXML
    private NumberValidator NumValidator;

    @FXML
    private RequiredFieldValidator validator3;

    @FXML
    private JFXButton previews;

    @FXML
    private JFXButton Next;


    @FXML
    private Label errorReporting;

    //SecondView
    @FXML
    private JFXTextField username;

    @FXML
    private RequiredFieldValidator validator4;

    @FXML
    private JFXTextField email;

    @FXML
    private RequiredFieldValidator validator5;

    @FXML
    private JFXPasswordField password;

    @FXML
    private RequiredFieldValidator validator6;

    @FXML
    private JFXButton previews2;

    @FXML
    private JFXButton OK;

    private void initFirstScene() {
        Name.setText(Values.getEnseignant().getPrenom());
        LastName.setText(Values.getEnseignant().getNom());

    }

    private void initSecondScene() {
        username.setText(Values.getEnseignant().getUsername());
        email.setText(Values.getEnseignant().getEmail());
    }

    private void validateF_Scene() {
        Name.getValidators().add(validator);
        Name.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(!newValue){
                    Name.validate();
                }
            }
        });

        LastName.getValidators().add(validator2);
        LastName.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(!newValue){
                    LastName.validate();
                }
            }
        });

        IDEnseignant.getValidators().addAll(validator3, NumValidator);
        IDEnseignant.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(!newValue){
                    IDEnseignant.validate();
                }
            }
        });
    }

    private void validateS_Scene(){
        username.getValidators().add(validator4);
        username.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(!newValue){
                    username.validate();
                }
            }
        });

        email.getValidators().add(validator5);
        email.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(!newValue){
                    email.validate();
                }
            }
        });
        password.getValidators().add(validator6);
        password.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(!newValue){
                    password.validate();
                }
            }
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            initFirstScene();
        }catch (Exception e){

        }
        try {
            initSecondScene();
        }catch (Exception e){

        }

        try{
            validateF_Scene();
        }catch (Exception e){

        }
        try{
            validateS_Scene();
        }catch (Exception e){

        }
    }

    @FXML
    public void backToLogin(ActionEvent event){
        Node node = (Node) event.getSource();
        Scene scene = node.getScene();
        Stage stage = (Stage) scene.getWindow();
        try{
            Parent root = FXMLLoader.load(getClass().getResource("../View/Loginlaunch.fxml"));
            stage.setScene(new Scene(root, 515, 607));
            stage.setResizable(false);
            stage.setTitle("CMS-Login (Enseignant)");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void uploadEnseignantF_Scene() {
        Values.getEnseignant().setNom(LastName.getText().toString());
        Values.getEnseignant().setPrenom(Name.getText().toString());
        Values.getEnseignant().setIdEnseignant(Integer.parseInt(IDEnseignant.getText()));
        System.out.println(Values.getEnseignant());
    }
    private void uploadS_Scene(ActionEvent event) {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../View/CreateAccountEnsegiantSecondView.fxml"));
            stage.setScene(new Scene(root, 450, 490));
            stage.setTitle("CMS-Create Account - Enseignant");
            stage.setResizable(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void ChangeScene(ActionEvent event){
        Pattern nummbers = Pattern.compile("[0-9]{1,}");
        Pattern str = Pattern.compile("[a-zA-Z\\s]{1,}");
        try{
            if(!(Name.getText().toString().equals("")||LastName.getText().toString().equals("")||
                    IDEnseignant.getText().toString().equals(""))
                    && Pattern.matches(String.valueOf(nummbers), IDEnseignant.getText())
                    && Pattern.matches(String.valueOf(str), LastName.getText())
                    && Pattern.matches(String.valueOf(str), Name.getText())
                    ){
                uploadEnseignantF_Scene();
                uploadS_Scene(event);
            }
        }catch (Exception e){

        }
    }

    @FXML
    void backview(ActionEvent event) {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../View/CreateAccountEnseignant.fxml"));
            stage.setScene(new Scene(root, 450, 490));
            stage.setTitle("Create Account - Enseignant");
            stage.setResizable(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void uploadEnseignantS_Scene() {
        Values.getEnseignant().setUsername(username.getText().toString());
        Values.getEnseignant().setEmail(email.getText().toString());
        Values.getEnseignant().setPassword(password.getText().toString());
        System.out.println(Values.getEnseignant());
    }
    @FXML
    void createAccount(ActionEvent event) {
        if (!(username.getText().toString().equals("")
                || email.getText().toString().equals("")
                ||password.getText().toString().equals(""))) {
            if (email.getText().contains("@")) {
                uploadEnseignantS_Scene();
                enseignant = new Enseignant();
                enseignant.setIdEnseignant(Values.getEnseignant().getIdEnseignant());
                enseignant.setNom(Values.getEnseignant().getNom());
                enseignant.setPrenom(Values.getEnseignant().getPrenom());
                enseignant.setUsername(Values.getEnseignant().getUsername());
                enseignant.setEmail(Values.getEnseignant().getEmail());
                enseignant.setPassword(Values.getEnseignant().getPassword());
                enseignantDAO.insertEnseignant(enseignant);
                Node node = (Node) event.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                stage.close();
                try {
                    start(stage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                errorReporting.setText("vérifier votre saiser de email");
            }
        }else {
            errorReporting.setText("vérifier votre saiser");
        }
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../View/MainView.fxml"));
        primaryStage.setTitle("CMS-Enseignant");
        primaryStage.setScene(new Scene(root, 709, 480));
        primaryStage.setResizable(false);
        primaryStage.close();
        primaryStage.show();

    }
}
