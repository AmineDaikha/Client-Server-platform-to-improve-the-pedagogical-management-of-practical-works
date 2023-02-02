package Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * Created by MedTaki on 22/05/2018.
 */
public class UserController {

    @FXML
    private Label UsernameLablel;

    @FXML
    private Label nomEtdLabel;

    @FXML
    private Label prenomEtdLabel;

    public Label getUsernameLablel() {
        return UsernameLablel;
    }

    public void setUsernameLablel(String username) {
        UsernameLablel.setText(username);
    }

    public Label getNomEtdLabel() {
        return nomEtdLabel;
    }

    public void setNomEtdLabel(String nomEtd) {
        this.nomEtdLabel.setText(nomEtd);
    }

    public Label getPrenomEtdLabel() {
        return prenomEtdLabel;
    }

    public void setPrenomEtdLabel(String prenomEtd) {
        this.prenomEtdLabel.setText(prenomEtd);
    }
}
