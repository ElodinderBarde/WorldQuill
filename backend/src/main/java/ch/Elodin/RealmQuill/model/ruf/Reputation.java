package ch.Elodin.RealmQuill.model.ruf;
import ch.Elodin.RealmQuill.model.Clan;
import ch.Elodin.RealmQuill.model.world.Location;
import jakarta.persistence.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor
@Entity @Table(name = "reputation")
public class Reputation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "faction")
    private String faction;
    @Column(name = "amount")
    private int amount;
    @ManyToOne
    @JoinColumn(name = "location_ID")
    private Location location;
    @ManyToOne
    @JoinColumn(name = "fk_clan_ID")
    private Clan clan;
}
