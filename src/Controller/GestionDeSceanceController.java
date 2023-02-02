package Controller;



/**
 * Created by MedTaki on 06/05/2018.
 */

import Controller.DAObjects.EtudiantDAO;
import Controller.DAObjects.UtilisateurDAO;
import Controller.Threads.ThreadReception;
import Model.Etudiant;
import Model.Utilisateur;
import Model.Values;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.*;

public class GestionDeSceanceController implements Initializable {

    int i = 0;

    private UtilisateurDAO utilisateurDAO = new UtilisateurDAO();
    private EtudiantDAO etudiantDAO = new EtudiantDAO();

    private Etudiant etudiant = new Etudiant();

    @FXML
    private AnchorPane gestionDeSceanceParent;

    @FXML
    private AnchorPane Etudiants;

    @FXML
    private ScrollPane etudiantScroll;

    @FXML
    private VBox etudiantsBox;

    @FXML
    private JFXButton ajouterEtudiant;


    @FXML
    private JFXButton ListeDesAbsence;

    @FXML
    private Text Time;

    @FXML
    private Pane formulaireAjouterEtudiant;

    @FXML
    private JFXTextField NumCarteAjouterField;

    @FXML
    private JFXTextField nomEtudiantAjouterField;

    @FXML
    private JFXTextField PrenomEtudiantAjouterField;

    @FXML
    private JFXButton Ajouter;

    @FXML
    private ImageView closeButtonAjouter;

    @FXML
    private Pane formulaireEditEtudiant;

    @FXML
    private JFXTextField NumCarte;

    @FXML
    private JFXTextField nomEtudiantField;

    @FXML
    private JFXTextField PrenomEtudiantField;

    @FXML
    private JFXButton modifier;

    @FXML
    private ImageView closeButtonEditer;

    @FXML
    private Pane ConfirmPane;

    @FXML
    private JFXButton CancelDelete;

    @FXML
    private JFXButton yseDelete;


    @FXML
    private Label labelGroupe;

    //WS
    @FXML
    private Pane wsPane;

    @FXML
    private VBox wsBox;

    @FXML
    private Label nomEtdws;

    @FXML
    private ImageView closeButtonWS;


    @FXML
    private Label etatedit;

    @FXML
    private Label etatAjout;

    @FXML
    private Pane notConnectedPane;

    @FXML
    private JFXButton getIt;



    @FXML
    private JFXButton downloadAll;


    @FXML
    private Pane alert;

    @FXML
    private Button alertHndelBtn;

    public Etudiant getEtudiant() {
        return etudiant;
    }

    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }

    public Text getTime() {
        return Time;
    }

    public void setTime(String time) {
        this.Time.setText(time);
    }

    File enseignerFile;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addColseFormulaireHundler();
        startTimeThread();
        loadAllEtudiant();
        Values.setGestionDeSceanceController(this);
        enseignerFile = new File("groupe"+Values.getEnseigner().getGroupe().getNumGroupe()+
                Values.getEnseigner().getModule().getSpécialité().getAbv()
                +File.separator+Values.getEnseigner().getModule().getNom());

        labelGroupe.setText("Liste Des Etudiant De Groupe "+Values.getEnseigner().getGroupe().getNumGroupe()+" "
                + Values.getEnseigner().getModule().getSpécialité().getAbv()+" Module "+Values.getEnseigner().getModule().getNom());
        //Liste Des Etudiant

    }



    @FXML
    void RecuperePresence(ActionEvent event) {
        System.out.println("Student Liste");
        String nameFile = "List de presence "+realTimeMsg()+" groupe"+Values.getEnseigner().getGroupe().getNumGroupe()
                +" module"+Values.getEnseigner().getModule().getNom();
        Node node = (Node)event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();

        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Enregistrer la liste de presence");
        File selectedDirectory =
                directoryChooser.showDialog(stage);
        String path = selectedDirectory.getAbsolutePath();
        createPresenceListe(nameFile, path);
    }

    private void createPresenceListe(String nameFile, String path) {
        try {
            FileOutputStream presenceFile = new FileOutputStream(path+"\\"+nameFile+".xls", true);
            presenceFile.write(("\n\t"+nameFile+"\n").getBytes());
            presenceFile.write(("Nom\t Prenom\n").getBytes());
            for (HashMap.Entry<String, Utilisateur> indexPresent : Values.getConnectedUsers().entrySet()){
                Utilisateur utilisateur = indexPresent.getValue();
                presenceFile.write((utilisateur.getNom()+"\t"+utilisateur.getPrenom()+"\n").getBytes());
            }
            presenceFile.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String realTimeMsg() {
        Calendar cal = new GregorianCalendar();
        int hour = cal.get(Calendar.HOUR);
        int minute = cal.get(Calendar.MINUTE);
        int seconds = cal.get(Calendar.SECOND);
        int date = cal.get(Calendar.DATE);
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);
        return year+"-"+month +"-" + date +" "+String.format("%02d", hour) + "_" + String.format("%02d", minute) + "_" + String.format("%02d", seconds);
    }

    public void loadAllEtudiant() {
        etudiantsBox.getChildren().clear();
        ArrayList<Etudiant> allEtudiant = etudiantDAO.readetudiantGroupe();
        Iterator<Etudiant> etudiantIterator = allEtudiant.iterator();
        while (etudiantIterator.hasNext()){
            Etudiant etudiant = etudiantIterator.next();
            try {
                Parent pane = null;
                FXMLLoader etudiantPane = new FXMLLoader();
                pane = etudiantPane.load(getClass().getResource("/View/Etudiant_Pane.fxml").openStream());
                EtudiantPaneController etudiantPaneController = etudiantPane.getController();
                etudiantPaneController.setEtudiant(etudiant);
                setDataToEtudiantPane(etudiant, etudiantPaneController);
                editEtudiantButtonEventHandler(etudiantPaneController);
                deleteEtudiantButtonEventHandler(etudiantPaneController);
                setShowWSButtonEventHundler(etudiantPaneController);
                etudiantPaneController.getViewEtd().addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        try {
                            Process process = new ProcessBuilder("C:\\Users\\Amine Daikha\\Downloads\\projet\\L3ProjectEnseignant\\uvnc bvba\\UltraVNC\\vncviewer.exe").start();
                            InputStream inputStream = process.getInputStream();
                            InputStreamReader isr = new InputStreamReader(inputStream);
                            BufferedReader br = new BufferedReader(isr);
                            String line;
                            System.out.println("output of running %s is : ");
                            while ((line = br.readLine())!= null){
                                System.out.println(line);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                disconnect(etudiantPaneController);
                if(Values.getMap().get(etudiant.getUsername())!=null){
                    etudiantPaneController.setEtatEtudiant("#6fda67");
                    String etat = Values.getEtatEtudiants().get(etudiant.getUsername());
                    if(etat.toString().equals("Bloquée")){
                        etudiantPaneController.getStatEtudiant().setTextFill(Color.valueOf("e55949"));
                    }else if (etat.toString().equals("Terminée")){
                        etudiantPaneController.getStatEtudiant().setTextFill(Color.valueOf("#65b162"));
                    }else {
                        etudiantPaneController.getStatEtudiant().setTextFill(Color.valueOf("#3086b9"));
                    }
                    etudiantPaneController.setStatEtudiant(etat);
                    etudiantPaneController.setIPaddress(Values.getMap().get(etudiant.getUsername()).getInetAddress().getHostAddress());
                }
                etudiantsBox.getChildren().add(pane);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void disconnect(EtudiantPaneController etudiantPaneController) {
        etudiantPaneController.getDéconnecter().addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Values.getServerTCPConnectionThread().sendMessage("@Deconnect");
                Values.getFileSockets().remove(etudiantPaneController.getEtudiant().getUsername());
                Values.getMap().remove(etudiantPaneController.getEtudiant().getUsername());
                System.out.println(etudiantPaneController.getEtudiant());
                Utilisateur utilisateur = utilisateurDAO.readUser(etudiantPaneController.getEtudiant());
                Values.getConnectedUsers().remove(utilisateur.getUsername());
                loadAllEtudiant();
            }
        });
    }

    private void setShowWSButtonEventHundler(EtudiantPaneController etudiantPaneController) {
        etudiantPaneController.getAfficherEspaceDeTravaile().addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setEtudiant(etudiantPaneController.getEtudiant());
                requestWS(etudiantPaneController);
                if(Values.getSocket()!=null){
                    wsPane.setLayoutX(124);
                    wsPane.setLayoutY(40);
                    nomEtdws.setText(etudiantPaneController.getEtudiant().getNom()+" "
                            + etudiantPaneController.getEtudiant().getPrenom());
                    Etudiants.setOpacity(0.2);
                }else {
                    notConnectedPane.setLayoutY(124);
                    notConnectedPane.setLayoutX(151);
                    Etudiants.setDisable(true);
                }
            }
        });
    }

    private void requestWS(EtudiantPaneController etudiantPaneController) {
        Socket socket = Values.getSocket();
        Values.setSocket(Values.getMap().get(etudiantPaneController.getEtudiant().getUsername()));
        if(Values.getSocket() != null){
            try {
                Values.setIn(new BufferedReader(new InputStreamReader(Values.getSocket().getInputStream())));
                Values.setOut(new PrintWriter(new BufferedWriter(new OutputStreamWriter(Values.getSocket().getOutputStream())), true));
            }catch (IOException e){
                e.printStackTrace();
            }
            Values.getServerTCPConnectionThread().sendMessage("@showWS");
            try {
                Values.setIn(new BufferedReader(new InputStreamReader(Values.getSocket().getInputStream())));
                Values.setOut(new PrintWriter(new BufferedWriter(new OutputStreamWriter(Values.getSocket().getOutputStream())), true));
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    ArrayList<WsFileController> allWsFiles;

    public void loadWs(String[]files){
        allWsFiles = new ArrayList<>();
        wsBox.getChildren().clear();
        for (String file : files){
            if(file.contains(".")){
                Parent pane = null;
                FXMLLoader wsFile = new FXMLLoader();
                try {
                    pane = wsFile.load(getClass().getResource("/View/Ws_File.fxml").openStream());
                    WsFileController wsFileController = wsFile.getController();
                    wsFileController.setFileName(file);
                    wsFileController.setEtudiant(etudiant);
                    wsFileController.setAbsolutPath(etudiant.getEspaceDeTravail()+File.separator+file);
                    System.out.println(wsFileController.getAbsolutPath());
                    addDownloadFromWsEventHndler(wsFileController);
                    allWsFiles.add(wsFileController);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                wsBox.getChildren().add(pane);
            }
        }
    }

    private void addDownloadFromWsEventHndler(WsFileController wsFileController) {
        wsFileController.getDownloadFile().addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println(wsFileController.getAbsolutPath());
                //TODO Download the selected file
                Values.getServerTCPConnectionThread().sendMessage("@Chemain&"+wsFileController.getAbsolutPath());
                new ThreadReception(Values.getFileSockets().get(wsFileController.getEtudiant().getUsername()),
                        wsFileController.getAbsolutPath(),
                        wsFileController.getEtudiant()).start();
            }
        });
    }
    File deletedDir;
    private void deleteEtudiantButtonEventHandler(EtudiantPaneController etudiantPaneController) {

        etudiantPaneController.getSupprimerEtudiant().addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                i = 3;
                afficherBoiteSupprimer();
                System.out.println(etudiantPaneController.getEtudiant());
                setEtudiant(etudiantPaneController.getEtudiant());
                deletedDir = new File(enseignerFile.getAbsolutePath()+File.separator+etudiant.getNom()+etudiant.getPrenom());
            }
        });
    }

    private void afficherBoiteSupprimer() {
        //TODO show the confirmation Box then delet the groupe check and cancel button event
        ConfirmPane.setLayoutX(151);
        Etudiants.setDisable(true);
    }

    File renamedFile;
    private void editEtudiantButtonEventHandler(EtudiantPaneController etudiantPaneController) {
        etudiantPaneController.getModifier().addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setEtudiant(etudiantPaneController.getEtudiant());
                Etudiants.setOpacity(0.2);
                Etudiants.setDisable(true);
                formulaireEditEtudiant.setLayoutY(40);
                NumCarte.setText(etudiant.getNumCarte()+"");
                nomEtudiantField.setText(etudiant.getNom());
                PrenomEtudiantField.setText(etudiant.getPrenom());
                renamedFile = new File(enseignerFile.getAbsolutePath()+File.separator+etudiant.getNom()+etudiant.getPrenom());
            }
        });
    }

    private void setDataToEtudiantPane(Etudiant etudiant, EtudiantPaneController etudiantPaneController) {
        etudiantPaneController.setNomPrenom(etudiant.getNom()+" "+etudiant.getPrenom());
        //TODO : Add etudiant pane controller toValues.etudiantsControllerpanes
        Values.getEtudiantPaneControllerHashMap().put(etudiant.getUsername(), etudiantPaneController);
    }

    private void startTimeThread() {
        Values.getTimeThread().setGestionDeSceanceController(this);
        try {
            Values.getTimeThread().start();
        }catch (Exception e){
        }
    }

    @FXML
    void AjouterEtu(ActionEvent event) {
        i = 1;
        Etudiant etudiant = new Etudiant();
        etudiant.setNumCarte(Integer.parseInt(NumCarteAjouterField.getText()));
        etudiant.setNom(nomEtudiantAjouterField.getText());
        etudiant.setPrenom(PrenomEtudiantAjouterField.getText());
        if(etudiantDAO.readEtudiantByNumCarte(etudiant)==null){
            etudiantDAO.insertEtudiant(etudiant);
            File f = new File(enseignerFile.getAbsolutePath()+File.separator+etudiant.getNom()+etudiant.getPrenom());
            if(!f.exists()){
                f.mkdir();
            }
            alert.setLayoutX(144);
            formulaireAjouterEtudiant.setDisable(true);
            formulaireAjouterEtudiant.setLayoutY(544);
        }else{
            etatAjout.setText("Cette etudiant existe déja");
        }

        loadAllEtudiant();
    }

    @FXML
    void afficherFormulaireEtudiant(ActionEvent event) {
        Etudiants.setOpacity(0.2);
        formulaireAjouterEtudiant.setLayoutY(40);
    }

    @FXML
    void modifierEtd(ActionEvent event) {
        i = 0;
        etudiant.setNumCarte(Integer.parseInt(NumCarte.getText()));
        etudiant.setNom(nomEtudiantField.getText());
        etudiant.setPrenom(PrenomEtudiantField.getText());
        if(etudiantDAO.readEtudiantByNumCarte(etudiant) == null){
            etudiantDAO.updateEtudiant(etudiant);
            File f = new File(enseignerFile.getAbsolutePath()+File.separator+etudiant.getNom()+etudiant.getPrenom());
            File rename = new File(renamedFile.getAbsolutePath());
            rename.renameTo(f);
            formulaireEditEtudiant.setDisable(true);
            formulaireEditEtudiant.setLayoutY(544);
            alert.setLayoutX(144);
            loadAllEtudiant();
        }else if (etudiantDAO.readEtudiantByNumCarte(etudiant).getId() == etudiant.getId()) {
            etudiantDAO.updateEtudiant(etudiant);
            File f = new File(enseignerFile.getAbsolutePath()+File.separator+etudiant.getNom()+etudiant.getPrenom());
            File rename = new File(renamedFile.getAbsolutePath());
            rename.renameTo(f);
            alert.setLayoutX(144);
            formulaireEditEtudiant.setDisable(true);
            formulaireEditEtudiant.setLayoutY(544);
            loadAllEtudiant();
        } else {
            etatedit.setText("cette etudiant existe déja");
        }
        loadAllEtudiant();
    }



    private void addColseFormulaireHundler() {
        closeButtonAjouter.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                hideFormulaireDAjout();
            }
        });
        closeButtonEditer.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                hideFormulaireDEdit();
            }
        });

        closeButtonWS.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                hideFormulaireDWs();
            }
        });
    }

    private void hideFormulaireDWs() {
        wsPane.setLayoutY(540);
        wsPane.setLayoutX(540);
        Etudiants.setOpacity(1);
    }

    private void hideFormulaireDEdit() {
        formulaireEditEtudiant.setLayoutY(540);
        Etudiants.setOpacity(1);
        Etudiants.setDisable(false);
    }

    private void hideFormulaireDAjout() {
        formulaireAjouterEtudiant.setLayoutY(540);
        Etudiants.setOpacity(1);
        Etudiants.setDisable(false);
    }

    @FXML
    void AnnulerSuppression(ActionEvent event) {
        ConfirmPane.setLayoutX(600);
        Etudiants.setDisable(false);
    }


    @FXML
    void suppressionConfirmer(ActionEvent event) {
        utilisateurDAO.deleteUserById(etudiant);
        for(File file : deletedDir.listFiles()){
            file.delete();
        }
        deletedDir.delete();
        loadAllEtudiant();
        ConfirmPane.setLayoutX(600);
        alert.setLayoutX(144);
    }


    @FXML
    void hideNotConnectedPane(ActionEvent event) {
        notConnectedPane.setLayoutX(610);
        notConnectedPane.setLayoutY(304);
        Etudiants.setDisable(false);
    }



    @FXML
    void telechargertout(ActionEvent event) {
        Iterator<WsFileController> allWsFilesIndex = allWsFiles.iterator();
        while (allWsFilesIndex.hasNext()){
            WsFileController wsFileController = allWsFilesIndex.next();
            System.out.println(wsFileController.getAbsolutPath());
            //TODO Download the selected file
            Values.getServerTCPConnectionThread().sendMessage("@Chemain&"+wsFileController.getAbsolutPath());
            System.out.println(wsFileController.getAbsolutPath());
            new ThreadReception(Values.getFileSockets().get(wsFileController.getEtudiant().getUsername()),
                    wsFileController.getAbsolutPath(),
                    wsFileController.getEtudiant()).start();
        }
    }

    @FXML
    void hndelAlert(ActionEvent event) {
        alert.setLayoutX(894);//144
        formulaireAjouterEtudiant.setDisable(false);
        formulaireEditEtudiant.setDisable(false);
        Etudiants.setDisable(false);
        if (i==1){
            formulaireAjouterEtudiant.setLayoutY(40);
            PrenomEtudiantAjouterField.setText("");
            NumCarteAjouterField.setText("");
            nomEtudiantAjouterField.setText("");
        }else if(i==2)
            formulaireEditEtudiant.setLayoutY(40);
    }
}
