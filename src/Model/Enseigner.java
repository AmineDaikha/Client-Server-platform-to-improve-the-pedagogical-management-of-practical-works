package Model;

/**
 * Created by MedTaki on 27/05/2018.
 */
public class Enseigner {
    private int id;
    private Enseignant enseignant;
    private Module module;
    private Groupe groupe;

    public Enseigner() {
    }

    public Enseigner(int id, Enseignant enseignant, Module module, Groupe groupe) {
        this.id = id;
        this.enseignant = enseignant;
        this.module = module;
        this.groupe = groupe;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Enseignant getEnseignant() {
        return enseignant;
    }

    public void setEnseignant(Enseignant enseignant) {
        this.enseignant = enseignant;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public Groupe getGroupe() {
        return groupe;
    }

    public void setGroupe(Groupe groupe) {
        this.groupe = groupe;
    }

    @Override
    public String toString() {
        return "Enseigner{" +
                "id=" + id +
                ", enseignant=" + enseignant +
                ", module=" + module +
                ", groupe=" + groupe +
                '}';
    }
}
