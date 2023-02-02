package Model;

import java.sql.Timestamp;

/**
 * Created by MedTaki on 15/04/2018.
 */
public class Fichier {
    private int id;
    private String nom;
    private String chemain;
    private   Fichier fichier;
    private TypeFichier typeFichier;
    private Enseigner enseigner;

    public Fichier() {
    }

    public Fichier(int id, String nom, String chemain, Fichier fichier, TypeFichier typeFichier, Enseigner enseigner) {
        this.id = id;
        this.nom = nom;
        this.chemain = chemain;
        this.fichier = fichier;
        this.typeFichier = typeFichier;
        this.enseigner = enseigner;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getChemain() {
        return chemain;
    }

    public void setChemain(String chemain) {
        this.chemain = chemain;
    }

    public Fichier getFichier() {
        return fichier;
    }

    public void setFichier(Fichier fichier) {
        this.fichier = fichier;
    }

    public TypeFichier getTypeFichier() {
        return typeFichier;
    }

    public void setTypeFichier(TypeFichier typeFichier) {
        this.typeFichier = typeFichier;
    }

    public Enseigner getEnseigner() {
        return enseigner;
    }

    public void setEnseigner(Enseigner enseigner) {
        this.enseigner = enseigner;
    }

    @Override
    public String toString() {
        return "Fichier{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", chemain='" + chemain + '\'' +
                ", fichier=" + fichier +
                ", typeFichier=" + typeFichier +
                ", enseigner=" + enseigner +
                '}';
    }
}

