package Model;

/**
 * Created by MedTaki on 07/03/2018.
 */
public class Utilisateur {
    protected int id;
    protected String nom;
    protected  String prenom;
    protected  String username;
    protected  String email;
    protected String password;

    public Utilisateur() {
    }

    public Utilisateur(int id, String nom, String prenom, String username, String email, String password) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.username = username;
        this.email = email;
        this.password = password;
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

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUtilisateur (Utilisateur utilisateur){
        setUsername(utilisateur.getUsername());
        setPassword(utilisateur.getPassword());
        setEmail(utilisateur.getEmail());
        setNom(utilisateur.nom);
        setPrenom(utilisateur.getNom());
    }

    @Override
    public String toString() {
        return "Utilisateur{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
