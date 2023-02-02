package Controller;

import Controller.DAObjects.FichierDAO;
import Controller.Threads.ThreadReception;
import Model.Fichier;
import Model.Message;
import Model.Utilisateur;
import Model.Values;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;

/**
 * Created by Amine Daikha on 10/05/2018.
 */
public class GestionProjetsController implements Initializable{

    private Message messageProject;

    @FXML
    private AnchorPane mesProjets;

    @FXML
    private VBox ProjetsBox;

    @FXML
    private JFXButton downloadAll;

    @FXML
    private Pane ConfirmPane;

    @FXML
    private JFXButton CancelDelete;

    @FXML
    private JFXButton yseDelete;



    private FichierDAO fichierDAO = new FichierDAO();


    File enseignerFile;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        messageProject = new Message();
        loadAllprojet();
        enseignerFile = new File("groupe"+Values.getEnseigner().getGroupe().getNumGroupe()+
                Values.getEnseigner().getModule().getSpécialité().getAbv()
                +File.separator+Values.getEnseigner().getModule().getNom());
        Values.setGestionProjetsController(this);
    }

    public void loadAllprojet() {
        ArrayList<Message> myProjects = fichierDAO.readProjects();
        ProjetsBox.getChildren().clear();
        Iterator<Message> myProjectsIndex= myProjects.iterator();
        while (myProjectsIndex.hasNext()){
            Message projet = myProjectsIndex.next();
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                Pane tpPane = null;
                tpPane = fxmlLoader.load(getClass().getResource("/View/ProjetPane.fxml").openStream());
                ProjetPaneController projetPaneController = fxmlLoader.getController();
                setDataToProjectPane(projet, projetPaneController);
                addDownloadButtonEventHundler(projetPaneController);
                addDeleteButtonEventHundler(projetPaneController);
                ProjetsBox.getChildren().add(tpPane);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void addDownloadButtonEventHundler(ProjetPaneController projetPaneController) {
        messageProject = projetPaneController.getMessageProjet();
        projetPaneController.getDownload().addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //Choose directory and start the download
                //TODO choisire le repertoire destination en utilise la variable chemain dans le thread reception
                Values.getServerTCPConnectionThread().sendMessage("@Chemain&"+messageProject.getFichier().getChemain());
                Utilisateur utilisateur = projetPaneController.getMessageProjet().getSource();
                System.out.println(messageProject.getFichier().getChemain());
                System.out.println(utilisateur);
                new ThreadReception(Values.getFileSockets().get(utilisateur.getUsername()),
                        messageProject.getFichier().getChemain(),
                        utilisateur).start();
            }
        });
    }

    private void setDataToProjectPane(Message fichier, ProjetPaneController projetPaneController) {
        projetPaneController.setMessageProjet(fichier);
        System.out.println(fichier);
        projetPaneController.setTPname(fichier.getFichier().getFichier().getNom());
        projetPaneController.setDate_time(fichier.getCurrentTime().toString());
        projetPaneController.setNomPrenomUsernameEtd(fichier.getSource().getNom()
                        + " " +fichier.getSource().getPrenom()
                        +" @"+fichier.getSource().getUsername());
        projetPaneController.setNomProjet(fichier.getFichier().getNom());
    }

    private void addDeleteButtonEventHundler(ProjetPaneController projetPaneController) {
        messageProject = projetPaneController.getMessageProjet();
        projetPaneController.getSupprimerProjet().addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                showConffirmePane();
            }
        });
    }

    private void showConffirmePane() {
        mesProjets.setDisable(true);
        System.out.println(messageProject);
        //TODO show confirme the delete
        ConfirmPane.setLayoutX(120);
    }


    @FXML
    void AnnulerSuppression(ActionEvent event) {
        mesProjets.setDisable(false);
        ConfirmPane.setLayoutX(910);
    }

    @FXML
    void suppressionConfirmer(ActionEvent event) {
        fichierDAO.deletFichier(messageProject.getFichier());
        mesProjets.setDisable(false);
        ConfirmPane.setLayoutX(910);
        loadAllprojet();
    }

    @FXML
    void telechargerTout(ActionEvent event) {

    }

}
