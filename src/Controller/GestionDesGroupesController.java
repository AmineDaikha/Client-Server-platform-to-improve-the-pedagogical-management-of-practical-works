package Controller;
//-fx-prompt-text-fill: ##d3d3d3;
import Controller.DAObjects.*;
import Model.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
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
 * Created by MedTaki on 28/03/2018.
 */
public class GestionDesGroupesController implements Initializable {
    GroupeDAO groupeDAO = new GroupeDAO();
    @FXML
    private AnchorPane gestionDesGroupesParent;

    @FXML
    private AnchorPane groups;

    @FXML
    private ScrollPane groupsPane;

    @FXML
    private VBox groupsBox;

    @FXML
    private JFXButton ajouterGroupe;

    @FXML
    private Pane formulaireGroupe;

    @FXML
    private JFXTextField numGroupe;

    @FXML
    private JFXComboBox<String> module;

    @FXML
    private JFXComboBox<String> spécialité;

    @FXML
    private JFXButton ajouter;


    @FXML
    private Label CantAdd;

    @FXML
    private Pane formulaireEditGroupe;

    @FXML
    private JFXTextField numGroupeEditer;

    @FXML
    private JFXComboBox<String> moduleEditer;

    @FXML
    private JFXComboBox<String> spécialitéEditer;

    @FXML
    private JFXButton modifier;

    @FXML
    private Label CantEdit;

    @FXML
    private ImageView closeButtonEditer;

    @FXML
    private ImageView closeButton;

    @FXML
    private Pane ConfirmPane;

    @FXML
    private JFXButton CancelDelete;

    @FXML
    private JFXButton yseDelete;


    @FXML
    private Pane alert;

    @FXML
    private Button alertHndelBtn;

    UtilisateurDAO utilisateurDAO = new UtilisateurDAO();
    EnseignantDAO enseignantDAO = new EnseignantDAO();
    ModuleDAO moduleDAO = new ModuleDAO();
    SpécialitéDAO spécialitéDAO = new SpécialitéDAO();
    EnseignerDAO enseignerDAO = new EnseignerDAO();
    Enseigner enseigner;
    int i = 0;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        enseigner = new Enseigner();
        closeFormulaireButtonEventHandler();
        addComboBoxsValues();
        addParentEventHandler();
        loadAllGroupes();
        addComboBoxsFilter();
    }

    private void addComboBoxsFilter() {
        spécialitéEditer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Module mdl = new Module();
                Spécialité spl = new Spécialité();
                spl.setAbv(spécialitéEditer.getValue());
                mdl.setSpécialité(spécialitéDAO.readScpécialité(spl));
                addModuleBoxValues(moduleEditer, mdl);
            }
        });

        spécialité.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Module mdl = new Module();
                Spécialité spl = new Spécialité();
                spl.setAbv(spécialité.getValue());
                mdl.setSpécialité(spécialitéDAO.readScpécialité(spl));
                try {
                    addModuleBoxValues(module, mdl);
                } catch (Exception e) {
                    System.out.println("spécialité null");
                }
            }
        });
    }


    private void loadAllGroupes() {
        ArrayList<Enseigner> allEnseignersGroupes = enseignerDAO.allEnseigners();
       if(allEnseignersGroupes != null){
           Iterator<Enseigner> enseignerIterator = allEnseignersGroupes.iterator();
           while (enseignerIterator.hasNext()){
               //**************
               try {
//                TODO despatch user to the group management
                   Enseigner enseigner = enseignerIterator.next();
                   Parent pane = null;
                   FXMLLoader groupPane = new FXMLLoader();
                   pane = groupPane.load(getClass().getResource("/View/GroupePane.fxml").openStream());
                   GroupePaneController groupePaneController = groupPane.getController();
                   setDataToGroupePane(enseigner, groupePaneController);
                   addGroupeEventHandler(pane, groupePaneController);
                   editGroupeButtonEventHandler(groupePaneController);
                   deleteGroupeButtonEventHandler(groupePaneController);
                   groupsBox.getChildren().add(pane);

               } catch (IOException e) {
                   e.printStackTrace();
               }
           }
       }
    }

    private void deleteGroupeButtonEventHandler(GroupePaneController groupePaneController) {
        groupePaneController.getDelete().addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
//                getGroupeDataFromLables(groupePaneController);
                enseigner = groupePaneController.getEnseigner();
                afficherBoiteSupprimer();
                i = 3;
            }
        });
    }

    private void afficherBoiteSupprimer() {
        //TODO show the confirmation Box then delet the groupe check and cancel button event
        ConfirmPane.setLayoutX(151);
        groups.setDisable(true);
    }

    private void addComboBoxsValues() {
        ArrayList<Spécialité> allSpécialités = spécialitéDAO.readAllSpécialité();
        Iterator<Spécialité> spécialitéIterator = allSpécialités.iterator();
        while (spécialitéIterator.hasNext()){
            Spécialité s = spécialitéIterator.next();
            spécialité.getItems().add(s.getAbv());
            spécialitéEditer.getItems().add(s.getAbv());
        }
        ArrayList<Module> allModules = moduleDAO.readAllModules();
        Iterator<Module> moduleIterator = allModules.iterator();
        while (moduleIterator.hasNext()){
            Module m = moduleIterator.next();
            module.getItems().add(m.getNom());
            moduleEditer.getItems().add(m.getNom());
        }
    }

    private void addModuleBoxValues(JFXComboBox<String> moduleComboBox, Module mdl) {
        moduleComboBox.getItems().clear();
        ArrayList<Module> allModules = moduleDAO.readAllModules(mdl);
        Iterator<Module> moduleIterator = allModules.iterator();
        while (moduleIterator.hasNext()){
            Module m = moduleIterator.next();
            moduleComboBox.getItems().add(m.getNom());
        }
    }

    private void closeFormulaireButtonEventHandler() {
        closeButton.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                showGroups();
            }
        });
        closeButtonEditer.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                showGroups();
            }
        });
    }

    private void addParentEventHandler() {
        groups.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                showGroups();
            }
        });
    }


    File parentrenamedFile;
    File renamedFile;
    private void editGroupeButtonEventHandler(GroupePaneController groupePaneController) {
        groupePaneController.getEdit().addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
//                getGroupeDataFromLables(groupePaneController);
                enseigner = groupePaneController.getEnseigner();
                parentrenamedFile = new File("groupe"+enseigner.getGroupe().getNumGroupe()+enseigner.getModule().getSpécialité().getAbv());
                renamedFile = new File("groupe"+enseigner.getGroupe().getNumGroupe()+
                        enseigner.getModule().getSpécialité().getAbv()+File.separator+enseigner.getModule().getNom());
                afficherFormulairemodifier(enseigner);
            }
        });
    }



    private void setDataToGroupePane( Enseigner enseigner, GroupePaneController groupePaneController) {
        groupePaneController.setGroupeLabel(""+enseigner.getGroupe().getNumGroupe());
        groupePaneController.setSpécialitéLabel(enseigner.getModule().getSpécialité().getAbv());
        groupePaneController.setModuleLabel(enseigner.getModule().getNom());
        groupePaneController.setEnseigner(enseigner);
    }

    private void addGroupeEventHandler(Parent pane, GroupePaneController groupePaneController) {
        pane.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
//                getGroupeDataFromLables(groupePaneController);
                enseigner = groupePaneController.getEnseigner();
                Values.setEnseigner(enseigner);
                System.out.println(Values.getEnseigner());
                try {
                    Pane sceancePane = FXMLLoader.load(getClass().getResource("/View/Gestion de Sceance.fxml"));//CHANGER LE VIEW MESSAGERIE VERS SCEANCE
                    gestionDesGroupesParent.getChildren().setAll(sceancePane);
                    Values.getMainViewController().getLeftMenue().setDisable(false);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @FXML
    void afficherFormulaireDeGroupe(ActionEvent event) {
//        groups.setDisable(true);
        groups.setOpacity(0.2);
        formulaireGroupe.setLayoutY(40);
    }

    private void showGroups() {
        formulaireGroupe.setLayoutY(540);
        formulaireEditGroupe.setLayoutY(540);
//        groups.setDisable(false);
        groups.setOpacity(1);
    }

    @FXML
    void ajouterGroupe(ActionEvent event) {
        i = 1;
        if(ajoutDeGroupe()){
            numGroupe.setText("");
            spécialité.setValue("");
//            module.setValue("");
        }
    }

    private boolean ajoutDeGroupe() {
        Groupe g = new Groupe();
        Module m = new Module();
        Spécialité s = new Spécialité();
        s.setAbv(spécialité.getValue().toString());
        s = spécialitéDAO.readScpécialité(s);
        m.setNom(module.getValue().toString());
        m.setSpécialité(s);
        System.out.println(m.getSpécialité());
        m = moduleDAO.readModuleByNomSpécialité(m);
        System.out.println(m.getSpécialité());
        enseigner.setModule(m);
        g.setNumGroupe(Integer.parseInt(numGroupe.getText().toString()));
        g.setIdGroupe(groupeDAO.getIdGroupe(g, m));
        enseigner.setGroupe(g);
        enseigner.setEnseignant(enseignantDAO.readEnseignant(Values.getEnseignant()));
        enseigner.setId(enseignerDAO.getIdEnseigner(enseigner));
        if(enseigner.getId()== -1){
            if(enseigner.getGroupe().getIdGroupe() == -1){
                groupeDAO.insertGroupe(enseigner.getGroupe());
                enseigner.getGroupe().setIdGroupe(groupeDAO.getLastGroupe());
                try {
                    File groupeDir = new File("groupe"+enseigner.getGroupe().getNumGroupe()+enseigner.getModule().getSpécialité().getAbv());
                    if(!groupeDir.exists()){
                        groupeDir.mkdir();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                alert.setLayoutX(144);
                formulaireGroupe.setDisable(true);
                formulaireGroupe.setLayoutY(544);

            }else{
                enseigner.getGroupe().setIdGroupe(groupeDAO.getIdGroupe(enseigner.getGroupe(), enseigner.getModule()));
            }
            enseignerDAO.insertEnseigner(enseigner);
            enseigner.setId(enseignerDAO.getIdEnseigner(enseigner));
            addGroupePane();
            try {
                File groupeDir = new File("groupe"+enseigner.getGroupe().getNumGroupe()+enseigner.getModule().getSpécialité().getAbv()+File.separator+enseigner.getModule().getNom());
                if(!groupeDir.exists()){
                    groupeDir.mkdir();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }else {
            CantAdd.setText("Cette groupe existe déja");
        }
        return false;
    }

    private void addGroupePane() {
        FXMLLoader groupPaneLoader = new FXMLLoader();
        try {
            Pane pane = groupPaneLoader.load(getClass().getResource("/View/GroupePane.fxml").openStream());
            GroupePaneController groupePaneController = groupPaneLoader.getController();
            addGroupeEventHandler(pane, groupePaneController);
            groupePaneController.setEnseigner(enseigner);
            groupePaneController.setGroupeLabel(""+enseigner.getGroupe().getNumGroupe());
            groupePaneController.setSpécialitéLabel(enseigner.getModule().getSpécialité().getAbv());
            groupePaneController.setModuleLabel(enseigner.getModule().getNom());
            editGroupeButtonEventHandler(groupePaneController);
            deleteGroupeButtonEventHandler(groupePaneController);
            groupsBox.getChildren().add(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void modifierGroupe(ActionEvent event) {
        if(editGroupe()){
            System.out.println("groupe modifier");
        }
    }

    private boolean editGroupe() {
        i = 0;

        if(parentrenamedFile.listFiles().length <= 1){
            for (File file : parentrenamedFile.listFiles()) {
                file.delete();
            }
            parentrenamedFile.delete();
        }else {
            for(File file : renamedFile.listFiles()){
                file.delete();
            }
            renamedFile.delete();
        }
        Groupe g = new Groupe();

        Module m= new Module();
        Spécialité s = new Spécialité();
        s.setAbv(spécialitéEditer.getValue().toString());
        s= spécialitéDAO.readScpécialité(s);
        m.setNom(moduleEditer.getValue().toString());
        m.setSpécialité(s);
        m= moduleDAO.readModuleByNomSpécialité(m);
        enseigner.setModule(m);
        g.setNumGroupe(Integer.parseInt(numGroupeEditer.getText()));
        g.setIdGroupe(groupeDAO.getIdGroupe(g, m));
        enseigner.setGroupe(g);
        enseigner.setEnseignant(Values.getEnseignant());
        enseigner = enseignerDAO.getEnseigner(enseigner);
        System.out.println(enseigner);
        if(enseignerDAO.getIdEnseigner(enseigner)== -1){
            if(enseigner.getGroupe().getIdGroupe() == -1){
                groupeDAO.insertGroupe(enseigner.getGroupe());
                enseigner.getGroupe().setIdGroupe(groupeDAO.getLastGroupe());
                try {
                    File groupeDir = new File("groupe"+enseigner.getGroupe().getNumGroupe()+enseigner.getModule().getSpécialité().getAbv());
                    if(!groupeDir.exists()){
                        groupeDir.mkdir();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }else {
                enseigner.getGroupe().setIdGroupe(groupeDAO.getIdGroupe(enseigner.getGroupe(), enseigner.getModule()));
            }
            enseignerDAO.updateEnseigner(enseigner);
            groupsBox.getChildren().clear();
            loadAllGroupes();
            try {
                File groupeDir = new File("groupe"+enseigner.getGroupe().getNumGroupe()+enseigner.getModule().getSpécialité().getAbv()+File.separator+enseigner.getModule().getNom());
                if(!groupeDir.exists()){
                    groupeDir.mkdir();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            alert.setLayoutX(144);
            formulaireEditGroupe.setDisable(true);
            formulaireEditGroupe.setLayoutY(544);
            return true;
        }else {
         CantEdit.setText("Cette groupe exicte déja");
        }
        return false;
    }

    public void afficherFormulairemodifier(Enseigner e){
//        Transition t = new ScaleTransition(new Duration(5000),formulaireEditGroupe);
        formulaireEditGroupe.setLayoutY(40);
        groups.setOpacity(0.4);
        moduleEditer.setValue(e.getModule().getNom().toString());
        numGroupeEditer.setText(e.getGroupe().getNumGroupe()+"");
        spécialitéEditer.setValue(e.getModule().getSpécialité().getAbv());
    }

    @FXML
    void AnnulerSuppression(ActionEvent event) {
        ConfirmPane.setLayoutX(600);
        groups.setDisable(false);
    }

    @FXML
    void suppressionConfirmer(ActionEvent event) {
        enseignerDAO.deleteEnseigner(enseigner);
        ConfirmPane.setLayoutX(600);
        groupsBox.getChildren().clear();
        groupeDAO.refreshGroupes();
        loadAllGroupes();
        File groupeDir = new File("groupe"+enseigner.getGroupe().getNumGroupe()+enseigner.getModule().getSpécialité().getAbv()+File.separator+enseigner.getModule().getNom());
        groupeDir.delete();
        groupeDir = new File("groupe"+enseigner.getGroupe().getNumGroupe()+enseigner.getModule().getSpécialité().getAbv());
        if(groupeDir.list().length==0){
            groupeDir.delete();
        }
        alert.setLayoutX(144);
    }

    @FXML
    void hndelAlert(ActionEvent event) {
        alert.setLayoutX(894);//144
        formulaireGroupe.setDisable(false);
        formulaireEditGroupe.setDisable(false);
        groups.setDisable(false);
        System.out.println(i);
        if (i==1){
            formulaireGroupe.setLayoutY(40);
            numGroupe.setText("");
            spécialité.setValue("");
            module.setValue("");
        }else if(i==0)
            formulaireEditGroupe.setLayoutY(40);
    }
}
