package Controller;

import Model.Fichier;
import Model.Message;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * Created by MedTaki on 01/06/2018.
 */
public class ProjetPaneController {

    private Message messageProjet = new Message();

    public Message getMessageProjet() {
        return messageProjet;
    }

    public void setMessageProjet(Message messageProjet) {
        this.messageProjet = messageProjet;
    }

    @FXML
    private Label nomPrenomUsernameEtd;

    @FXML
    private VBox Projet;

    @FXML
    private Label TPname;

    @FXML
    private Label date_time;

    @FXML
    private Label nomProjet;


    @FXML
    private JFXButton download;

    @FXML
    private JFXButton supprimerProjet;

    @FXML
    void afficherSupprimerConfirmation(ActionEvent event) {

    }

    public Label getNomPrenomUsernameEtd() {
        return nomPrenomUsernameEtd;
    }

    public void setNomPrenomUsernameEtd(String nomPrenomUsernameEtd) {
        this.nomPrenomUsernameEtd.setText(nomPrenomUsernameEtd);
    }

    public VBox getProjet() {
        return Projet;
    }

    public void setProjet(VBox projet) {
        Projet = projet;
    }

    public Label getTPname() {
        return TPname;
    }

    public void setTPname(String TPname) {
        this.TPname.setText(TPname);
    }

    public Label getDate_time() {
        return date_time;
    }

    public void setDate_time(String  date_time) {
        this.date_time.setText(date_time);
    }

    public Label getNomProjet() {
        return nomProjet;
    }

    public void setNomProjet(String  nomProjet) {
        this.nomProjet.setText(nomProjet);
    }

    public JFXButton getSupprimerProjet() {
        return supprimerProjet;
    }

    public void setSupprimerProjet(JFXButton supprimerProjet) {
        this.supprimerProjet = supprimerProjet;
    }

    public JFXButton getDownload() {
        return download;
    }

    public void setDownload(JFXButton download) {
        this.download = download;
    }
}
