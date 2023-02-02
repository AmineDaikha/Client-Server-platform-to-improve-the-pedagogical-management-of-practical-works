package Model;

/**
 * Created by MedTaki on 07/03/2018.
 */
public class Groupe {
    //this is a test
    private int idGroupe;
    private int numGroupe;

    public Groupe() {
    }

    public Groupe(int idGroupe, int numGroupe) {
        this.idGroupe = idGroupe;
        this.numGroupe = numGroupe;
    }

    public int getIdGroupe() {
        return idGroupe;
    }

    public void setIdGroupe(int idGroupe) {
        this.idGroupe = idGroupe;
    }

    public int getNumGroupe() {
        return numGroupe;
    }

    public void setNumGroupe(int numGroupe) {
        this.numGroupe = numGroupe;
    }

    @Override
    public String toString() {
        return "Groupe{" +
                "idGroupe=" + idGroupe +
                ", numGroupe=" + numGroupe +
                '}';
    }
}

