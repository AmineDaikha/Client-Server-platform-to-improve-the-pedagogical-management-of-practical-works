package Controller;

import Model.Etudiant;
import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * Created by MedTaki on 31/05/2018.
 */
public class WsFileController {
    private String absolutPath;
    private Etudiant etudiant = new Etudiant();

    public Etudiant getEtudiant() {
        return etudiant;
    }

    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }

    public void setFileName(Label fileName) {
        this.fileName = fileName;
    }

    public String getAbsolutPath() {
        return absolutPath;
    }

    public void setAbsolutPath(String absolutPath) {
        this.absolutPath = absolutPath;
    }

    @FXML
    private HBox wsFile;

    @FXML
    private Label fileName;

    @FXML
    private JFXButton downloadFile;

    public HBox getWsFile() {
        return wsFile;
    }

    public void setWsFile(HBox wsFile) {
        this.wsFile = wsFile;
    }

    public Label getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName.setText(fileName);
    }

    public JFXButton getDownloadFile() {
        return downloadFile;
    }

    public void setDownloadFile(JFXButton downloadFile) {
        this.downloadFile = downloadFile;
    }
}
