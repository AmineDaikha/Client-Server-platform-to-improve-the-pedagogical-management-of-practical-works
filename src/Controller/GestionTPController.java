package Controller;


import Controller.DAObjects.FichierDAO;
import Controller.DAObjects.MessageDAO;
import Controller.DAObjects.TypeFichierDAO;
import Model.Fichier;
import Model.Message;
import Model.TypeFichier;
import Model.Values;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;

/**
 * Created by Amine Daikha on 10/05/2018.
 */
public class GestionTPController extends Window implements Initializable{

    private FichierDAO fichierDAO = new FichierDAO();
    private TypeFichierDAO typeFichierDAO = new TypeFichierDAO();
    private MessageDAO messageDAO = new MessageDAO();

    @FXML
    private AnchorPane gestionDesTPPane;

    @FXML
    private ScrollPane TpsScroll;

    @FXML
    private VBox TpsBox;

    @FXML
    private JFXButton ajouterTP;

    @FXML
    private JFXTextField chemin;

    @FXML
    private JFXButton parcourir;

    @FXML
    private Pane ConfirmPane;

    @FXML
    private JFXButton CancelDelete;

    @FXML
    private JFXButton yseDelete;

    @FXML
    private JFXComboBox<String> typeFichierComboBox;

    File router;

    private Message message;

    @FXML
    void AjouterTP(ActionEvent event) {
        Fichier fichier = new Fichier();
        fichier.setChemain(chemin.getText());
        TypeFichier typeFichier = new TypeFichier();
        typeFichier.setType(typeFichierComboBox.getValue());
        fichier.setTypeFichier(typeFichierDAO.readTypeFichier(typeFichier));
        fichier.setNom(router.getName());
        fichier.setEnseigner(Values.getEnseigner());
        message.setFichier(fichier);
        message.setSource(Values.getEnseignant());
        System.out.println(message);
        fichierDAO.insertFichierEnseignant(message);
        message.getFichier().setId(fichierDAO.lastFileId());
        messageDAO.insertMessageFichier(message);
        chemin.setText("");
        typeFichierComboBox.setValue("");
        loadTPs();
        //send flag
        Values.getServerTCPConnectionThread().sendMessage("@@TPajouter");
    }

    @FXML
    void choisirFichier(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        router = fileChooser.showOpenDialog(this);
        chemin.setText(router.getAbsolutePath());
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        message = new Message();
        addChemainChangeListener();
        setDataToTypeFichierComboBox();
        loadTPs();
    }

    private void setDataToTypeFichierComboBox() {
        ArrayList<TypeFichier> allTypeFichiers = typeFichierDAO.readTypeFichiers();
        Iterator<TypeFichier> typeFichierIterator = allTypeFichiers.iterator();
        TypeFichier tFichier;
        while (typeFichierIterator.hasNext()){
            tFichier = typeFichierIterator.next();
            typeFichierComboBox.getItems().add(tFichier.getType());
        }
    }

    private void loadTPs() {
        TpsBox.getChildren().clear();
        ArrayList<Message> allFiles = fichierDAO.readAllFichier();
        Iterator<Message> allFilesIndex = allFiles.iterator();
        while (allFilesIndex.hasNext()){
            Message fichierMSG = allFilesIndex.next();
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                Pane tpPane = null;
                tpPane = fxmlLoader.load(getClass().getResource("/View/TPpane.fxml").openStream());
                TPPaneController tpPaneController = fxmlLoader.getController();
                tpPaneController.setTPLabel(fichierMSG.getFichier().getNom());
                tpPaneController.setFichierType(fichierMSG.getFichier().getTypeFichier().getType());
                tpPaneController.setFichierMSG(fichierMSG);
                TpsBox.getChildren().add(tpPane);
                deleteTPButtonEventHandler(tpPaneController);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void deleteTPButtonEventHandler(TPPaneController tpPaneController) {
        tpPaneController.getDeleteTP().addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                message = tpPaneController.getFichierMSG();
                getTPDataFromLables(tpPaneController);
                gestionDesTPPane.setDisable(true);
                //TODO show confirme the delete
                ConfirmPane.setLayoutX(120);
                loadTPs();
            }
        });
    }

    private void getTPDataFromLables(TPPaneController tpPaneController) {
        message.getFichier().setNom(tpPaneController.getTPLabel().getText());
    }

    @FXML
    void CheckEntry(ActionEvent event) {
        File f = new File(chemin.getText().toString());
        try {
            if(f.exists()
                    && !(typeFichierComboBox.getValue().toString().equals(""))||typeFichierComboBox.getValue().toString().equals(null)){
                ajouterTP.setDisable(false);
                router = f;
                System.out.println(router.getName());
            }else
                ajouterTP.setDisable(true);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void addChemainChangeListener() {
        chemin.textProperty().addListener(((observable, oldValue, newValue) -> {
            File f = new File(chemin.getText().toString());
            try {
                if(f.exists()
                        && !(typeFichierComboBox.getValue().toString().equals(""))||typeFichierComboBox.getValue().toString().equals(null)){
                    ajouterTP.setDisable(false);
                    router = f;
                    System.out.println(router.getName());
                }else
                    ajouterTP.setDisable(true);
            }catch (Exception e){
                e.printStackTrace();
            }

    }));

    }





    @FXML
    void suppressionConfirmer(ActionEvent event) {
        fichierDAO.deletFichier(message.getFichier());
        gestionDesTPPane.setDisable(false);
        loadTPs();
        ConfirmPane.setLayoutX(900);
    }

    @FXML
    void AnnulerSuppression(ActionEvent event) {
        gestionDesTPPane.setDisable(false);
        ConfirmPane.setLayoutX(900);
    }
}