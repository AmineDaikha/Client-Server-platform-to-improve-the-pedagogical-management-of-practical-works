package Model;

/**
 * Created by MedTaki on 27/03/2018.
 */
public class Enseignant extends Utilisateur {

    private int idEnseignant;

    public Enseignant() {
    }
    public Enseignant(int id, String nom, String prenom, String username, String email, String password, int idEnseignant) {
        super(id, nom, prenom, username, email, password);
        this.idEnseignant = idEnseignant;
    }

    public int getIdEnseignant() {
        return idEnseignant;
    }

    public void setIdEnseignant(int idEnseignant) {
        this.idEnseignant = idEnseignant;
    }

    @Override
    public String toString() {
        return "Enseignant{" +
                "id=" + id +
                ", idEnseignant=" + idEnseignant +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
