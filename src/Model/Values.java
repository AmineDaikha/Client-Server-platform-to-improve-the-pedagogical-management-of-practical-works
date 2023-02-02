package Model;

import Controller.*;
import Controller.Threads.ServerTCPConnectionThread;
import Controller.Threads.TimeThread;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.Semaphore;

/**
 * Created by MedTaki on 26/03/2018.
 */
public class Values {
    //test
    public static MessagerieController messagerieController;
    //***


    private static HashMap<String, Semaphore> userFieTransferSemaphores = new HashMap<>();

    public static HashMap<String, Semaphore> getUserFieTransferSemaphores() {
        return userFieTransferSemaphores;
    }

    public static void setUserFieTransferSemaphores(HashMap<String, Semaphore> userFieTransferSemaphores) {
        Values.userFieTransferSemaphores = userFieTransferSemaphores;
    }

    private static ArrayList<String> notificationTexts = new ArrayList<>();

    public static ArrayList<String> getNotificationTexts() {
        return notificationTexts;
    }

    public static void setNotificationTexts(ArrayList<String> notificationTexts) {
        notificationTexts = notificationTexts;
    }

    private static Enseignant enseignant = new Enseignant();
    private static Utilisateur utilisateur = new Utilisateur();
    private static Enseigner enseigner = new Enseigner();
    private static TimeThread timeThread = new TimeThread();
    private static Etudiant etudiant =  new Etudiant();
    private static HashMap<String, EtudiantPaneController> etudiantPaneControllerHashMap = new HashMap<String, EtudiantPaneController>();
    private static HashMap<String, Utilisateur> connectedUsers = new HashMap<>();
    private static HashMap<String, Socket> mapUsers = new HashMap();
    private static HashMap<String, Socket> fileSockets = new HashMap();
    private static ServerSocket serverSocket;
    private static ServerSocket fileServerSocket;
    private static Socket fileSocket;
    private static BufferedReader in;
    private static PrintWriter out;
    private static ServerTCPConnectionThread serverTCPConnectionThread = new ServerTCPConnectionThread();
    private static Socket socket;
    public static int port = 2000;
    private static GestionProjetsController  gestionProjetsController;



    private static HashMap <String, String> etatEtudiants = new HashMap<>();
    private static MainViewController mainViewController;
    private  static GestionDeSceanceController gestionDeSceanceController;

    public Values() {

    }

    public static GestionProjetsController getGestionProjetsController() {
        return gestionProjetsController;
    }

    public static void setGestionProjetsController(GestionProjetsController gestionProjetsController) {
        Values.gestionProjetsController = gestionProjetsController;
    }

    public static TimeThread getTimeThread() {
        return timeThread;
    }

    public static void setTimeThread(TimeThread timeThread) {
        Values.timeThread = timeThread;
    }

    public static Enseignant getEnseignant() {
        return enseignant;
    }

    public static void setEnseignant(Enseignant enseignant) {
        Values.enseignant = enseignant;
    }

    public static Enseigner getEnseigner() {
        return enseigner;
    }

    public static void setEnseigner(Enseigner enseigner) {
        Values.enseigner = enseigner;
    }

    public static Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public static void setUtilisateur(Utilisateur utilisateur) {
        Values.utilisateur = utilisateur;
    }

    public static HashMap<String, EtudiantPaneController> getEtudiantPaneControllerHashMap() {
        return etudiantPaneControllerHashMap;
    }

    public static void setEtudiantPaneControllerHashMap(HashMap<String, EtudiantPaneController> etudiantPaneControllerHashMap) {
        Values.etudiantPaneControllerHashMap = etudiantPaneControllerHashMap;
    }





    public static MainViewController getMainViewController() {
        return mainViewController;
    }

    public static void setMainViewController(MainViewController mainViewController) {
        Values.mainViewController = mainViewController;
    }



    public static BufferedReader getIn() {
        return in;
    }



    public static void setIn(BufferedReader in) {
        Values.in = in;
    }

    public static PrintWriter getOut() {
        return out;
    }

    public static void setOut(PrintWriter out) {
        Values.out = out;
    }




    public static Etudiant getEtudiant() {
        return etudiant;
    }

    public static void setEtudiant(Etudiant etudiant) {
        etudiant = etudiant;
    }

    public static ServerSocket getServerSocket() {
        return serverSocket;
    }

    public static void setServerSocket(ServerSocket serverSocket) {
        Values.serverSocket = serverSocket;
    }

    public static Socket getSocket() {
        return Values.socket;
    }

    public static void setSocket(Socket socket) {
        Values.socket = socket;
    }


    public static ServerTCPConnectionThread getServerTCPConnectionThread() {
        return serverTCPConnectionThread;
    }

    public static void setServerTCPConnectionThread(ServerTCPConnectionThread serverTCPConnectionThread) {
        Values.serverTCPConnectionThread = serverTCPConnectionThread;
    }

    public static HashMap<String, Socket> getMap() {
        return mapUsers;
    }

    public static void setMap(HashMap<String, Socket> map) {
        Values.mapUsers = map;
    }

    public static HashMap<String, Utilisateur> getConnectedUsers() {
        return connectedUsers;
    }

    public static void setConnectedUsers(HashMap<String, Utilisateur> connectedUsers) {
        Values.connectedUsers = connectedUsers;
    }


    public static GestionDeSceanceController getGestionDeSceanceController() {
        return gestionDeSceanceController;
    }

    public static void setGestionDeSceanceController(GestionDeSceanceController gestionDeSceanceController) {
        Values.gestionDeSceanceController = gestionDeSceanceController;
    }

    public static ServerSocket getFileServerSocket() {
        return fileServerSocket;
    }

    public static void setFileServerSocket(ServerSocket fileServerSocket) {
        Values.fileServerSocket = fileServerSocket;
    }

    public static Socket getFileSocket() {
        return fileSocket;
    }

    public static void setFileSocket(Socket fileSocket) {
        Values.fileSocket = fileSocket;
    }

    public static HashMap<String, Socket> getFileSockets() {
        return fileSockets;
    }

    public static void setFileSockets(HashMap<String, Socket> fileSockets) {
        Values.fileSockets = fileSockets;
    }

    public static HashMap<String, String> getEtatEtudiants() {
        return etatEtudiants;
    }

    public static void setEtatEtudiants(HashMap<String, String> etatEtudiants) {
       etatEtudiants = etatEtudiants;
    }

    public static void killApp(){
        Values.setEnseignant(null);
        Values.setEtudiant(null);
        Values.setEnseigner(null);
    }


}
