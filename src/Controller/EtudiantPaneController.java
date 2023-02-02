package Controller;

import Model.Etudiant;
import Model.Values;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Created by MedTaki on 06/05/2018.
 */
public class EtudiantPaneController {

    private Etudiant etudiant = new Etudiant();

    private String etatEtd;

    private String ip;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getEtatEtd() {
        return etatEtd;
    }

    public void setEtatEtd(String etatEtd) {
        this.etatEtd = etatEtd;
    }

    @FXML
    private Circle etatEtudiant;


    @FXML
    private Label statEtudiant;


    @FXML
    private Label IPaddress;



    @FXML
    private Label nomPrenom;

    @FXML
    private JFXButton modifier;

    @FXML
    private JFXButton afficherEspaceDeTravaile;


    @FXML
    private JFXButton viewEtd;


    @FXML
    private JFXButton déconnecter;


    public JFXButton getAfficherEspaceDeTravaile() {
        return afficherEspaceDeTravaile;
    }

    public void setAfficherEspaceDeTravaile(JFXButton afficherEspaceDeTravaile) {
        this.afficherEspaceDeTravaile = afficherEspaceDeTravaile;
    }



    @FXML
    private JFXButton supprimerEtudiant;



    public Etudiant getEtudiant() {
        return etudiant;
    }

    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }

    public Circle getEtatEtudiant() {
        return etatEtudiant;
    }

    public void setEtatEtudiant(String etatEtudiant) {
        this.etatEtudiant.setStroke(Color.valueOf(etatEtudiant));
        this.etatEtudiant.setFill(Color.valueOf(etatEtudiant));
    }

    public Label getNomPrenom() {
        return nomPrenom;
    }

    public void setNomPrenom(String nomPrenom) {
        this.nomPrenom.setText(nomPrenom);
    }

    public JFXButton getModifier() {
        return modifier;
    }

    public JFXButton getSupprimerEtudiant() {
        return supprimerEtudiant;
    }

    public JFXButton getDéconnecter() {
        return déconnecter;
    }

    public void setDéconnecter(JFXButton déconnecter) {
        this.déconnecter = déconnecter;
    }

    public void setSupprimerEtudiant(JFXButton supprimerEtudiant) {
        this.supprimerEtudiant = supprimerEtudiant;
    }

    public Label getStatEtudiant() {
        return statEtudiant;
    }

    public void setStatEtudiant(String statEtudiant) {
        this.statEtudiant.setText(statEtudiant);
    }

    public Label getIPaddress() {
        return IPaddress;
    }

    public void setIPaddress(String  IPaddress) {
        this.IPaddress .setText(IPaddress);
    }

    public JFXButton getViewEtd() {
        return viewEtd;
    }

    public void setViewEtd(JFXButton viewEtd) {
        this.viewEtd = viewEtd;
    }

    @FXML
    void afficherWS(ActionEvent event) {

    }

    @FXML
    void deleteEtudiant(ActionEvent event) {

    }

    @FXML
    void modifierEtudiant(ActionEvent event) {

    }




    @FXML
    void showEtudBureau(ActionEvent event) {

    }


    @FXML
    void disconnect(ActionEvent event) {
    }

}
