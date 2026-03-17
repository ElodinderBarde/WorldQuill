package ch.Elodin.RealmQuill.model.ruf;
import ch.Elodin.RealmQuill.model.Clan;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "ruf_konflikte")
public class RufKonflikte {

    @EmbeddedId
    private RufKonflikteKey id;

    @ManyToOne
    @MapsId("partei_sourceID")
    @JoinColumn(name = "partei_sourceID")
    private Clan partei_source;

    @ManyToOne
    @MapsId("partei_targetID")
    @JoinColumn(name = "partei_targetID")
    private Clan partei_target;

    @Column(name = "relation")
    private String relation;

    @Column(name = "notes")
    private String notes;

    // Getter und Setter
    public RufKonflikteKey getId() {
        return id;
    }

    public void setId(RufKonflikteKey id) {
        this.id = id;
    }

    public Clan getPartei_source() {
        return partei_source;
    }

    public void setPartei_source(Clan partei_source) {
        this.partei_source = partei_source;
    }

    public Clan getPartei_target() {
        return partei_target;
    }

    public void setPartei_target(Clan partei_target) {
        this.partei_target = partei_target;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
