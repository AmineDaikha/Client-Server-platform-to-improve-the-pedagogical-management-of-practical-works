package Controller.Threads;

import Model.Utilisateur;
import Model.Values;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.Socket;

/**
 * Created by MedTaki on 06/06/2018.
 */
    public class ThreadReception extends Thread{
        private  Socket socket;
        private File file;
        private Utilisateur utilisateur;
        public ThreadReception(Socket socket){
            this.socket=socket;
        }

    public ThreadReception(Socket fileSocket, String chemain, Utilisateur utilisateur) {
            socket = fileSocket;
            file = new File(chemain);
            this.utilisateur = utilisateur;
    }

    public ThreadReception() {

    }


    public void run() {
        try {
            Values.getUserFieTransferSemaphores().get(utilisateur.getUsername()).acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        RandomAccessFile inFile = null;
            try {
                InputStream in2 = socket.getInputStream();// flux de lecture de la socket
                try {
                    Thread.sleep(1000);

                } catch (Exception e) {
                    System.err.println(e);
                }
                File enseignerFile = new File("groupe"+Values.getEnseigner().getGroupe().getNumGroupe()+
                        Values.getEnseigner().getModule().getSpécialité().getAbv()
                        +File.separator+Values.getEnseigner().getModule().getNom());
                inFile = new RandomAccessFile( enseignerFile.getAbsolutePath()
                        +File.separator+utilisateur.getNom()+utilisateur.getPrenom()
                        +File.separator+file.getName(), "rw");
                byte bb[] = new byte[1024];
                int amount;
                while ((amount = in2.read(bb)) != -1) {
                    inFile.write(bb, 0, amount);
                    if(amount<1024){
                        break;
                    }
                }
                System.out.println(amount);
                System.out.println("not closed");
//                        in2.close();
                inFile.close();
                System.out.println("fichier bien recu "+ file.getName());
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("******" + e);
                e.printStackTrace();
            } finally {
                if (inFile != null){
                    try {
                        inFile.close();
                        inFile = null;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        System.out.println(this.isDaemon());
        System.out.println(this.isInterrupted());
        Values.getUserFieTransferSemaphores().get(utilisateur.getUsername()).release();
        }
    }
