package Model;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by MedTaki on 15/04/2018.
 */
public class Message {
    private Utilisateur destination;
    private Utilisateur source;
    private Timestamp currentTime ;
    private String centenu;
    private Fichier fichier;

    public Message() {
    }

    public Message(Utilisateur destination, Utilisateur source, Timestamp currentTime, String centenu, Fichier fichier) {
        this.destination = destination;
        this.source = source;
        this.currentTime = currentTime;
        this.centenu = centenu;
        this.fichier = fichier;
    }

    public Utilisateur getDestination() {
        return destination;
    }

    public void setDestination(Utilisateur destination) {
        this.destination = destination;
    }

    public Utilisateur getSource() {
        return source;
    }

    public void setSource(Utilisateur source) {
        this.source = source;
    }

    public Timestamp getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(Timestamp currentTime) {
        this.currentTime = currentTime;
    }

    public String getCentenu() {
        return centenu;
    }

    public void setCentenu(String centenu) {
        this.centenu = centenu;
    }

    public Fichier getFichier() {
        return fichier;
    }

    public void setFichier(Fichier fichier) {
        this.fichier = fichier;
    }

    @Override
    public String toString() {
        return "Message{" +
                "destination=" + destination +
                ", source=" + source +
                ", currentTime=" + currentTime +
                ", centenu='" + centenu + '\'' +
                ", fichier=" + fichier +
                '}';
    }
}
