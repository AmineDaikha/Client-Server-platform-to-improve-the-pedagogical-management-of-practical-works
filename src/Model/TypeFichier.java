package Model;

/**
 * Created by MedTaki on 27/05/2018.
 */
public class TypeFichier {
    private int id;
    private String Type;

    public TypeFichier() {
    }

    public TypeFichier(int id, String type) {
        this.id = id;
        Type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    @Override
    public String toString() {
        return "TypeFichier{" +
                "id=" + id +
                ", Type='" + Type + '\'' +
                '}';
    }
}
