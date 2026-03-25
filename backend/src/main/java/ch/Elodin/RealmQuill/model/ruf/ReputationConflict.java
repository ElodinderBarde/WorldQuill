package ch.Elodin.RealmQuill.model.ruf;
import ch.Elodin.RealmQuill.model.Clan;
import jakarta.persistence.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor
@Entity @Table(name = "reputation_conflict")
public class ReputationConflict {
    @EmbeddedId
    private ReputationConflictKey id;
    @ManyToOne
    @MapsId("sourceClanId")
    @JoinColumn(name = "source_clan_id")
    private Clan sourceClan;
    @ManyToOne
    @MapsId("targetClanId")
    @JoinColumn(name = "target_clan_id")
    private Clan targetClan;
    @Column(name = "conflict_type")
    private String conflictType;
    @Column(name = "notes")
    private String notes;
}
