package Controller.Threads;

import Model.Utilisateur;
import Model.Values;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by MedTaki on 06/06/2018.
 */
public class ThreadEnvoi extends Thread {
    public String cheminAbsolu;
    public Socket socket;
    public Utilisateur utilisateur;
    public ThreadEnvoi(String cheminAbsolu, Socket socket, Utilisateur utilisateur){
        this.cheminAbsolu = cheminAbsolu;
        this.socket = socket;
        this.utilisateur = utilisateur;
    }

    public void run() {
        try {
            Values.getUserFieTransferSemaphores().get(utilisateur.getUsername()).acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



        RandomAccessFile outFile = null;
        try {
            outFile = new RandomAccessFile(cheminAbsolu, "r");
            File f = new File(cheminAbsolu);
            OutputStream out2 = socket.getOutputStream();
            byte bb[] = new byte[1024];
            int amount;
            while ((amount = outFile.read(bb)) != -1) {
                out2.write(bb, 0, amount);
                out2.flush();
            }
            //   out2.flush(); vider la flux de la socket
            //out2.close();// fermer socket
            outFile.close();// fermer le fichier à envoyer
            System.out.println("transfert terminé pour nomFichier " + f.getName());
        } catch (FileNotFoundException ex) {
            System.out.println("-----" + ex);
        } catch (IOException ex) {
            System.out.println("-----" + ex);
        }
        Values.getUserFieTransferSemaphores().get(utilisateur.getUsername()).release();
    }
}

