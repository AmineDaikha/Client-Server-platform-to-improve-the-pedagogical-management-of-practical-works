package Controller;

import Model.Enseigner;
import Model.Groupe;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by MedTaki on 28/03/2018.
 */
public class GroupePaneController implements Initializable{


    private Enseigner enseigner;

    @FXML
    private AnchorPane groupe;

    @FXML
    private Label groupeLabel;

    @FXML
    private Label moduleLabel;

    @FXML
    private Label spécialitéLabel;

    @FXML
    private JFXButton delete;

    @FXML
    private JFXButton edit;


    public void setGroupeLabel(String groupeLabel) {
        this.groupeLabel .setText(groupeLabel);
    }

    public void setModuleLabel(String moduleLabel) {
        this.moduleLabel.setText(moduleLabel);
    }

    public void setSpécialitéLabel(String spécialitéLabel) {
        this.spécialitéLabel.setText(spécialitéLabel);
    }

    public AnchorPane getGroupe() {
        return groupe;
    }

    public Label getGroupeLabel() {
        return groupeLabel;
    }

    public Label getModuleLabel() {
        return moduleLabel;
    }

    public Label getSpécialitéLabel() {
        return spécialitéLabel;
    }

    @FXML
    void modifierGroupe(ActionEvent event) {
        Groupe g = new Groupe();
        g.setNumGroupe(Integer.parseInt(getGroupeLabel().getText()));
//        g.setModule(getModuleLabel().getText());
//        g.setSpécialité(getSpécialitéLabel().getText());
//        Parent pane = null;TODO show the edit groupe stage
    }

    public JFXButton getDelete() {
        return delete;
    }

    public void setDelete(JFXButton delete) {
        this.delete = delete;
    }

    public JFXButton getEdit() {
        return edit;
    }

    public void setEdit(JFXButton edit) {
        this.edit = edit;
    }

    @FXML
    void supprimerGroupe(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public Enseigner getEnseigner() {
        return enseigner;
    }

    public void setEnseigner(Enseigner enseigner) {
        this.enseigner = enseigner;
    }
}
