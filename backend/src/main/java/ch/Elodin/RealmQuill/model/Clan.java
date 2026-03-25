package ch.Elodin.RealmQuill.model;
import ch.Elodin.RealmQuill.model.enums.Familienclan;
import ch.Elodin.RealmQuill.model.ruf.Reputation;
import ch.Elodin.RealmQuill.model.ruf.ReputationConflict;
import ch.Elodin.RealmQuill.model.world.Campaign;
import ch.Elodin.RealmQuill.model.world.Location;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Getter @Setter @NoArgsConstructor
@Entity @Table(name = "npc_clan")
public class Clan {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "clan_name", unique = true)
    private String clan;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_ID")
    private Location location;
    @Column(name = "member_count")
    private Integer memberCount;
    @Column(name = "is_family_clan", columnDefinition = "ENUM('Y','N')")
    @Enumerated(EnumType.STRING)
    private Familienclan isFamilyClan;
    @Column(name = "notes")
    private String clanNotes;
    @OneToMany(mappedBy = "sourceClan")
    private List<ReputationConflict> reputationConflictsAsSource;
    @OneToMany(mappedBy = "targetClan")
    private List<ReputationConflict> reputationConflictsAsTarget;
    @OneToMany(mappedBy = "clan", fetch = FetchType.LAZY)
    private List<Reputation> reputations;
    @ManyToOne
    @JoinColumn(name = "campaign_id")
    private Campaign campaign;
}
