package Model;

/**
 * Created by MedTaki on 15/04/2018.
 */
public class Module {
    private int id;
    private String nom;
    private String déscription;
    private Spécialité spécialité;

    public Module() {
    }

    public Module(int id, String nom, String déscription, Spécialité spécialité) {
        this.id = id;
        this.nom = nom;
        this.déscription = déscription;
        this.spécialité = spécialité;
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

    public String getDéscription() {
        return déscription;
    }

    public void setDéscription(String déscription) {
        this.déscription = déscription;
    }

    public Spécialité getSpécialité() {
        return spécialité;
    }

    public void setSpécialité(Spécialité spécialité) {
        this.spécialité = spécialité;
    }

    @Override
    public String toString() {
        return "Module{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", déscription='" + déscription + '\'' +
                ", spécialité=" + spécialité +
                '}';
    }
}
