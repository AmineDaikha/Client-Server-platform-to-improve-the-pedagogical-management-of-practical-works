package Controller;

import Controller.DAObjects.EtudiantDAO;
import Controller.DAObjects.MessageDAO;
import Controller.DAObjects.UtilisateurDAO;
import Controller.Threads.ServerTCPConnectionThread;
import Model.Message;
import Model.Utilisateur;
import Model.Values;
import com.jfoenix.controls.JFXButton;
import javafx.animation.Animation;
import javafx.animation.SequentialTransition;
import javafx.animation.Transition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import com.gluonhq.charm.glisten.control.ProgressIndicator;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by MedTaki on 09/04/2018.
 */
public class MessagerieController implements Initializable {

    private UtilisateurDAO utilisateurDAO = new UtilisateurDAO();
    private Utilisateur utilisateurActif;

    @FXML
    private AnchorPane messageriePane;

    @FXML
    private AnchorPane messagesMainView;

    @FXML
    private ScrollPane messagesScrool;

    @FXML
    private VBox messagesBox;

    @FXML
    private TextArea messageArea;

    @FXML
    private JFXButton send;


    @FXML
    private JFXButton ShowUsers;

    @FXML
    private ScrollPane scrollUsersListe;

    @FXML
    private VBox UsersListe;



    @FXML
    private JFXButton exporterMsgs;

    Animation usersScrollAnimation = new SequentialTransition(scrollUsersListe);
    private MessageDAO mesageDao = new MessageDAO();
    private EtudiantDAO etudiantDAO;

    public VBox getMessagesBox() {
        return messagesBox;
    }

    public void setMessagesBox(VBox messagesBox) {
        this.messagesBox = messagesBox;
    }

    @FXML
    void sendMsg(ActionEvent event) {
//        test t = new test(Values.getEnseignant().getUsername(), messageArea.getText());
//        MessageDAO.addMsg(t);
//        messageArea.setText("");
//        messagesBox.getChildren().clear();
//        loadAllMessages();//TODO Insert the Message to the data base
        System.out.println("send button clicked...");
        try {
            Values.getServerTCPConnectionThread().sendMessage(messageArea.getText());
            addMessageTOmessageBox(messageArea.getText());
            messageArea.setText("");
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public void showMessage(String message) {
        FXMLLoader messagePaneLoader = new FXMLLoader();
        Pane pane = null;
        String[] messageFormat = message.split(":");
                    Utilisateur utilisateur = new Utilisateur();
                    utilisateur.setUsername(messageFormat[0].split("@")[1]);
                    System.out.println(messageFormat[0].split("@")[1]);
                    utilisateur = utilisateurDAO.readUser(utilisateur);
        System.out.println(utilisateur);
        System.out.println(utilisateurActif);
                    if (utilisateurActif.getUsername().equals(utilisateur.getUsername())) {
                        try {
                            pane = messagePaneLoader.load(getClass().getResource("/View/MessagePane.fxml").openStream());
                            MessagePaneController messagePaneController = messagePaneLoader.getController();
                            messagePaneController.setUsername(utilisateurActif.getNom()+utilisateurActif.getPrenom()+" @"+utilisateurActif.getUsername());
                            messagePaneController.setmessageText(messageFormat[1]);
                            String realTime = realTimeMsg();
                            messagePaneController.setDateTime(realTime);
                        } catch (IOException e) {
                            e.printStackTrace();
            }catch (Exception e2){
                e2.printStackTrace();
            }
            messagesBox.getChildren().add(pane);
        }else{
                        Image img = new Image("View/Styles/AppLogo2.2.png");
                        Notifications n = Notifications.create()
                                .title("CSM")
                                .text(utilisateur.getNom()+ " " + utilisateur.getPrenom() +"a envoyer un nouveau Message")
                                .graphic(new ImageView(img))/*new ImageView(img)*/
                                .hideAfter((Duration.seconds(2)))
                                .position(Pos.BOTTOM_RIGHT)
                                .onAction(new EventHandler<ActionEvent>() {
                                    @Override
                                    public void handle(ActionEvent event) {
                                        //TODO load the msgs between Etd et Enseignant
                                    }
                                });
                        // pour fenaitre noir, la fenaitre est blanche par defaut
                        n.show();
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
        return year+"-"+month +"-" + date +" "+String.format("%02d", hour) + ":" + String.format("%02d", minute) + ":" + String.format("%02d", seconds);
    }

    private void addMessageTOmessageBox(String message) {
        FXMLLoader messagePaneLoader = new FXMLLoader();
        Parent pane = null;
        try {
            pane = messagePaneLoader.load(getClass().getResource("/View/MessagePane.fxml").openStream());
            MessagePaneController messagePaneController = messagePaneLoader.getController();
            messagePaneController.setUsername(Values.getEnseignant().getNom()+Values.getEnseignant().getPrenom()
                    +"@"+Values.getEnseignant().getUsername());
            messagePaneController.setmessageText(message);
            String realTime = realTimeMsg();
            messagePaneController.setDateTime(realTime);
            messagesBox.getChildren().add(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        utilisateurActif = new Utilisateur(0, "", "", "", "", "");
        usersScrollAnimation.setDelay(new Duration(500));
//        loadAllMessages();
        send.setDisable(true);
        addMessageAreaListener();
        loadConnectedUsers();
        //set the Mesagerie Controller
//        Values.getServerTCPConnectionThread().setMessagerieController(this);
        Values.messagerieController = this;
    }

    public void loadConnectedUsers() {
        int size = Values.getConnectedUsers().size();
        if(size>0){
            UsersListe.getChildren().clear();
            //loading ....
            for (HashMap.Entry<String, Utilisateur>connectedUsersIndex : Values.getConnectedUsers().entrySet()) {
                Utilisateur utilisateur = connectedUsersIndex.getValue();
                System.out.println(utilisateur);
                //load the userview
//                if(etudiantDAO.readEtudiant(utilisateur).getGroupe().getIdGroupe() == Values.getEnseigner().getGroupe().getIdGroupe()){
                    FXMLLoader userViewloader = new FXMLLoader();
                    Parent pane = null;
                    try {
                        pane = userViewloader.load(getClass().getResource("/View/user_messagerie.fxml").openStream());
                        UserController userController = userViewloader.getController();
                        userController.setUsernameLablel("@"+utilisateur.getUsername());
                        userController.setNomEtdLabel(utilisateur.getNom());
                        userController.setPrenomEtdLabel(utilisateur.getPrenom());
                        addConnectedUserEventHandler(pane, userController);
                        //add the event click
                        UsersListe.getChildren().add(pane);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
//        }
        //TODO Delete the in connected users from the connected users liste
    }

    private void addConnectedUserEventHandler(Parent pane, UserController userController) {
        pane.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            Utilisateur utilisateur = new Utilisateur();
            UtilisateurDAO utilisateurDAO = new UtilisateurDAO();
            @Override
            public void handle(MouseEvent event) {
                //TODO select the user messages
                String [] user = userController.getUsernameLablel().getText().toString().split("@");
                utilisateur.setUsername(user[1]);
                utilisateur =  utilisateurDAO.readUser(utilisateur);
                utilisateurActif = utilisateur;
                System.out.println(utilisateurActif);
                ArrayList<Message> userMessage = mesageDao.readAllMessages(utilisateurActif);
                loadAllMessages(userMessage);
                try {
                    Values.setSocket(Values.getMap().get(utilisateur.getUsername()));
                    Values.setIn(new BufferedReader(new InputStreamReader(Values.getSocket().getInputStream())));
                    Values.setOut(new PrintWriter(new BufferedWriter(new OutputStreamWriter(Values.getSocket().getOutputStream())), true));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void addMessageAreaListener() {
        messageArea.textProperty().addListener(((observable, oldValue, newValue) -> {
            if (!messageArea.getText().toString().equals("")){
                send.setDisable(false);
            }
            else {
                send.setDisable(true);
            }
        }));
    }

    private void loadAllMessages() {
        System.out.println(Values.getEnseignant());
        ArrayList<Message> allmsgs = mesageDao.readAllMessages(Values.getEnseignant());
        System.out.println(allmsgs.size());
        Iterator<Message> allmsgsindex = allmsgs.iterator();
        while (allmsgsindex.hasNext()){
            try {
                Message message = allmsgsindex.next();
                Parent root = null;
                FXMLLoader loader = new FXMLLoader();
                root = loader.load(getClass().getResource("../View/MessagePane.fxml").openStream());
                MessagePaneController controller = loader.getController();
                controller.setmessageText(message.getCentenu());//************
                controller.setUsername(message.getSource().getNom()+" "+message.getSource().getPrenom()+"  "+"@"+message.getSource().getUsername());
                controller.setDateTime(message.getCurrentTime().toString());
                messagesBox.getChildren().add(root);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        messagesScrool.setVvalue(Double.MAX_VALUE);
    }

    private void loadAllMessages(ArrayList<Message> allmsgs) {
        messagesBox.getChildren().clear();
        showUsersList(new ActionEvent());
        System.out.println(Values.getEnseignant());
        System.out.println(allmsgs.size());
        Iterator<Message> allmsgsindex = allmsgs.iterator();
        while (allmsgsindex.hasNext()){
            try {
                Message message = allmsgsindex.next();
                Parent root = null;
                FXMLLoader loader = new FXMLLoader();
                root = loader.load(getClass().getResource("../View/MessagePane.fxml").openStream());
                MessagePaneController controller = loader.getController();
                controller.setmessageText(message.getCentenu());//************
                controller.setUsername(message.getSource().getNom()+" "+message.getSource().getPrenom()+"  "+"@"+message.getSource().getUsername());
                controller.setDateTime(message.getCurrentTime().toString());
                messagesBox.getChildren().add(root);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        messagesScrool.setVvalue(Double.MAX_VALUE);
    }

    @FXML
    void showUsersList(ActionEvent event) {
        if(scrollUsersListe.getLayoutX()==309){
            scrollUsersListe.setLayoutX(700);
            messagesMainView.setDisable(false);
        }else {
            scrollUsersListe.setLayoutX(309);
            messagesMainView.setDisable(true);
        }
    }


    @FXML
    void exporterLesMessages(ActionEvent event) {
        String nameFile = "Messages"+realTimeMsg2()+" groupe"+Values.getEnseigner().getGroupe().getNumGroupe()
                +" module"+Values.getEnseigner().getModule().getNom()+"avec "+utilisateurActif.getNom()+" "+utilisateurActif.getPrenom();
        File file= new File("groupe"+Values.getEnseigner().getGroupe().getNumGroupe()+
                Values.getEnseigner().getModule().getSpécialité().getAbv()
                +File.separator+Values.getEnseigner().getModule().getNom()+
                File.separator+utilisateurActif.getNom()+utilisateurActif.getPrenom());
        oldMessages(nameFile, file);
    }

    private void oldMessages(String nameFile, File file) {
        FileOutputStream Msgs = null;
        ArrayList<Message> userMessage = mesageDao.readAllMessages(utilisateurActif);
        Iterator<Message> singleMessage = userMessage.iterator();
        try {
            Msgs = new FileOutputStream(file.getPath()+File.separator+nameFile+".xls", true);
            Msgs.write(("\n\t"+nameFile+"\n").getBytes());
            Msgs.write(("Nom\t Prenom\t Message\t temps\n").getBytes());
            while (singleMessage.hasNext()){
                Message m = singleMessage.next();
                Msgs.write((m.getSource().getNom()+"\t"+m.getSource().getPrenom()+"\t"+m.getCentenu()+"\t"+m.getCurrentTime()+"\n").getBytes());
            }
            Msgs.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String realTimeMsg2() {
        Calendar cal = new GregorianCalendar();
        int hour = cal.get(Calendar.HOUR);
        int minute = cal.get(Calendar.MINUTE);
        int seconds = cal.get(Calendar.SECOND);
        int date = cal.get(Calendar.DATE);
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);
        return year+"-"+month +"-" + date +" "+String.format("%02d", hour) + "_" + String.format("%02d", minute) + "_" + String.format("%02d", seconds);
    }
}
