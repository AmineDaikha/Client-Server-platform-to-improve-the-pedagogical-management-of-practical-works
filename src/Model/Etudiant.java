package Model;

/**
 * Created by MedTaki on 07/03/2018.
 */
public class Etudiant extends Utilisateur {
    private int numCarte;
    private  Groupe groupe;
    private String espaceDeTravail;
    public Etudiant getEtudiant(){
        return this;
    }

    public Etudiant() {
    }

    public Etudiant(int id, String nom, String prenom, String username, String email, String password, int numCarte, Groupe groupe, String espaceDeTravail) {
        super(id, nom, prenom, username, email, password);
        this.numCarte = numCarte;
        this.groupe = groupe;
        this.espaceDeTravail = espaceDeTravail;
    }

    public int getNumCarte() {
        return numCarte;
    }

    public void setNumCarte(int numCarte) {
        this.numCarte = numCarte;
    }

    public Groupe getGroupe() {
        return groupe;
    }

    public void setGroupe(Groupe groupe) {
        this.groupe = groupe;
    }

    public String getEspaceDeTravail() {
        return espaceDeTravail;
    }

    public void setEspaceDeTravail(String espaceDeTravail) {
        this.espaceDeTravail = espaceDeTravail;
    }

    @Override
    public String toString() {
        return "Etudiant{" +
                "id=" + id +
                ", numCarte=" + numCarte +
                ", nom='" + nom + '\'' +
                ", groupe=" + groupe +
                ", prenom='" + prenom + '\'' +
                ", espaceDeTravail='" + espaceDeTravail + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
