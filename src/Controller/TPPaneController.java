package Controller;

import Model.Fichier;
import Model.Message;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.event.ActionEvent;
import javafx.scene.layout.HBox;


/**
 * Created by Amine Daikha on 10/05/2018.
 */
public class TPPaneController {

    @FXML
    private HBox TP;

    @FXML
    private JFXButton deleteTP;


    @FXML
    private Label FichierType;

    @FXML
    private Label TPLabel;

    private Message fichierMSG = new Message();

    public Message getFichierMSG() {
        return fichierMSG;
    }

    public void setFichierMSG(Message fichierMSG) {
        this.fichierMSG = fichierMSG;
    }

    public Label getFichierType() {
        return FichierType;
    }

    public void setFichierType(String fichierType) {
        FichierType.setText(fichierType);
    }

    public Label getTPLabel() {
        return TPLabel;
    }

    public void setTPLabel(String TPLabel) {
        this.TPLabel.setText(TPLabel);
    }

    @FXML
    void deleteTP(ActionEvent event) {

    }

    public JFXButton getDeleteTP() {
        return deleteTP;
    }

}
