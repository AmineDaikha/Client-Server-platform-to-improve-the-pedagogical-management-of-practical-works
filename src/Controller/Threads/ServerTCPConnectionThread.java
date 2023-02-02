package Controller.Threads;

import Controller.DAObjects.MessageDAO;
import Controller.DAObjects.UtilisateurDAO;
import Controller.MessagerieController;
import Model.Message;
import Model.Utilisateur;
import Model.Values;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.io.*;
import java.net.ServerSocket;
import java.util.concurrent.Semaphore;

/**
 * Created by MedTaki on 03/05/2018.
 */
public class ServerTCPConnectionThread extends Thread {

    private volatile boolean running = true;
    private ReciveThread reciveThread;
    private Utilisateur utilisateur = new Utilisateur();
    private UtilisateurDAO utilisateurDAO = new UtilisateurDAO();
    private MessageDAO messageDAO = new MessageDAO();
    public ServerTCPConnectionThread(){

    }

    public void run() {
        System.out.println("je suis un serveur");
            while (running){
                startServerSocket();
                System.out.println("Client Connected");
            }
        System.out.println("Shutting down thread");
    }
    public void shutdown() {
        running = false;
    }

    private void startServerSocket() {
        try {

                System.out.println("waitin for client");
                Values.setSocket(Values.getServerSocket().accept());
                System.out.println("Server : la connection etablir avec un client");
                Values.setIn(new BufferedReader(new InputStreamReader(Values.getSocket().getInputStream())));
                Values.setOut(new PrintWriter(new BufferedWriter(new OutputStreamWriter(Values.getSocket().getOutputStream())), true));
                Values.setFileSocket(Values.getFileServerSocket().accept());
                reciveThread = new ReciveThread();
                reciveThread.start();

//            Values.getMap().put(new Utilisateur(), Values.getServerSocket().accept());//TODO accept the Client Connection
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String msg){
        try {
            Values.getOut().println("@"+Values.getEnseignant().getUsername()+":"+msg);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    class ReciveThread extends Thread{
        public String reciveMessage(){
            System.out.println("Server : j'attent un message");
            String message = "";
            while (running){
                try {
                    message = Values.getIn().readLine();
                    if (message.contains("@LAN.local")) {
                        String [] user = message.split("@");
                        utilisateur.setUsername(user[0]);
                        Values.getMap().put(utilisateur.getUsername(), Values.getSocket());//inserer utilisateur connecté au Map
                        Values.getConnectedUsers().put(utilisateur.getUsername(), utilisateurDAO.readUser(utilisateur));
                        Values.getUserFieTransferSemaphores().put(utilisateur.getUsername(), new Semaphore(1));
                        Values.getFileSockets().put(utilisateur.getUsername(), Values.getFileSocket());
                        Values.getEtatEtudiants().put(utilisateur.getUsername(), "En Travail");
                        Platform.runLater(() -> {
                            try {
                                Values.messagerieController.loadConnectedUsers();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            Values.getGestionDeSceanceController().loadAllEtudiant();
                        });
                    } else if(message.contains("@ws")) {
                        String [] files = message.split("@");
                        System.out.println(message);
                        System.out.println(files[3]);
                        System.out.println(files[4]);
                        System.out.println(files[5]);
                        Platform.runLater(()->{
                            Values.getGestionDeSceanceController().loadWs(files);
                        });
                    } else if(message.contains("@Chemain&")){
                        String filePath = message.split("&")[1];
                        String[] messageFormat = message.split(":");
                        Utilisateur utilisateur = new Utilisateur();
                        utilisateur.setUsername(messageFormat[0].split("@")[1]);
                        System.out.println(messageFormat[0].split("@")[1]);
                        utilisateur = utilisateurDAO.readUser(utilisateur);
                        File file = new File(filePath);
                        System.out.println(file.getName());
                    //TODO Start the thread's upload
                        new ThreadEnvoi(file.getAbsolutePath(),
                                Values.getFileSockets().get(utilisateur.getUsername()),
                                utilisateur).start();
                    }else if(message.contains("StateChange&")) {
                        String[] messageFormat = message.split(":");
                        Utilisateur utilisateur = new Utilisateur();
                        utilisateur.setUsername(messageFormat[0].split("@")[1]);
                        System.out.println(messageFormat[0].split("@")[1]);
                        utilisateur = utilisateurDAO.readUser(utilisateur);
                        String etat =  message.split("&")[1];
                        Values.getEtatEtudiants().remove(utilisateur.getUsername());
                        Values.getEtatEtudiants().put(utilisateur.getUsername(), etat);
                        Utilisateur finalUtilisateur = utilisateur;
                        Platform.runLater(() -> {
                            try {
                                Values.messagerieController.loadConnectedUsers();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            Values.getGestionDeSceanceController().loadAllEtudiant();
                            changerStatNotif(finalUtilisateur, etat);
                            Values.getNotificationTexts().add(finalUtilisateur.getNom()+" " +finalUtilisateur.getPrenom()+" a changet son etat a :  "+etat);
                        });

                    }else if(message.contains("@@Projet")){
                        String[] messageFormat = message.split(":");
                        Utilisateur utilisateur = new Utilisateur();
                        utilisateur.setUsername(messageFormat[0].split("@")[1]);
                        System.out.println(messageFormat[0].split("@")[1]);
                        utilisateur = utilisateurDAO.readUser(utilisateur);
                        Utilisateur finalUtilisateur = utilisateur;
                        Platform.runLater(()->{
                            tpNotification(finalUtilisateur);
                            Values.getNotificationTexts().add("L'Enseignant a ajouté un nouveau Fichier");
                            try {
                                Values.getGestionProjetsController().loadAllprojet();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });
                    }else{
                            System.out.println("Server j'ai recue : " + message);
                            String finalMessage = message;

                            Platform.runLater(() -> {
                                try {
                                    Values.messagerieController.showMessage(finalMessage);
                                }catch (Exception e){
                                    e.printStackTrace();
                                    notificationMsg();
                                }
                            });
                            saveMsg(finalMessage);
                        }

                } catch (IOException e) {
                    e.printStackTrace();
                    //Notification
                    break;
                }
            }
            return message;
        }

        private void tpNotification(Utilisateur utilisateur) {

            Image img = new Image("View/Styles/AppLogo2.2.png");
            Notifications n = Notifications.create()
                    .title("CMS")
                    .text(utilisateur.getNom()+ " "+ utilisateur.getPrenom()+"\n a envoyé(e) un nouveau Projet")
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

        public void run(){
            reciveMessage();
        }
    }

    private void changerStatNotif(Utilisateur utilisateur, String etat) {
        Image img = new Image("View/Styles/AppLogo2.2.png");
        Notifications n = Notifications.create()
                .title("CMS")
                .text(Values.getEnseignant().getNom()+ " " + Values.getEnseignant().getPrenom() +": \n"+utilisateur.getNom()+" a changer son etat a :"+ etat)
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

    private void notificationMsg() {
        Image img = new Image("View/Styles/AppLogo2.2.png");
        Notifications n = Notifications.create()
                .title("CMS")
                .text(Values.getEnseignant().getNom()+ " " + Values.getEnseignant().getPrenom() +": \nvous avez recus une message")
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

    private void saveMsg(String message) {
        String[] messageFormat = message.split(":");
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setUsername(messageFormat[0].split("@")[1]);
        System.out.println(messageFormat[0].split("@")[1]);
        utilisateur = utilisateurDAO.readUser(utilisateur);
        Message msg = new Message();
        msg.setDestination(Values.getEnseignant());
        msg.setSource(utilisateur);
        msg.setCentenu(messageFormat[1]);
        messageDAO.insertMessage(msg);
    }
}
