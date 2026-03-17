package ch.Elodin.RealmQuill.model.ruf;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class RufKonflikteKey implements Serializable {

    private int partei_sourceID;
    private int partei_targetID;

    // Standard-Konstruktor
    public RufKonflikteKey() {}

    public RufKonflikteKey(int partei_sourceID, int partei_targetID) {
        this.partei_sourceID = partei_sourceID;
        this.partei_targetID = partei_targetID;
    }

    // Getter und Setter
    public int getPartei_sourceID() {
        return partei_sourceID;
    }

    public void setPartei_sourceID(int partei_sourceID) {
        this.partei_sourceID = partei_sourceID;
    }

    public int getPartei_targetID() {
        return partei_targetID;
    }

    public void setPartei_targetID(int partei_targetID) {
        this.partei_targetID = partei_targetID;
    }

    // equals() und hashCode() sind wichtig fÃƒÂ¼r Composite Keys
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RufKonflikteKey)) return false;
        RufKonflikteKey that = (RufKonflikteKey) o;
        return partei_sourceID == that.partei_sourceID && partei_targetID == that.partei_targetID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(partei_sourceID, partei_targetID);
    }
}
