package Model;

/**
 * Created by MedTaki on 07/03/2018.
 */
public class  Spécialité {
    private int id;
    private String abv;
    private String nom;
    private String déscription;
    private Departement departement;

    public Spécialité() {
    }

    public Spécialité(int id, String abv, String nom, String déscription, Departement departement) {
        this.id = id;
        this.abv = abv;
        this.nom = nom;
        this.déscription = déscription;
        this.departement = departement;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAbv() {
        return abv;
    }

    public void setAbv(String abv) {
        this.abv = abv;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDéscription() {
        return déscription;
    }

    public void setDéscription(String déscription) {
        this.déscription = déscription;
    }

    public Departement getDepartement() {
        return departement;
    }

    public void setDepartement(Departement departement) {
        this.departement = departement;
    }

    @Override
    public String toString() {
        return "Spécialité{" +
                "id=" + id +
                ", abv='" + abv + '\'' +
                ", nom='" + nom + '\'' +
                ", déscription='" + déscription + '\'' +
                ", departement=" + departement +
                '}';
    }
}
