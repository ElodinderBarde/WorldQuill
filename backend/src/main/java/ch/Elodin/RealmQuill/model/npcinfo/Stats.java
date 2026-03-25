package ch.Elodin.RealmQuill.model.npcinfo;
import ch.Elodin.RealmQuill.model.Npc;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor
@Entity @Table(name = "npc_stats")
public class Stats {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "npc_id", referencedColumnName = "id")
    @JsonIgnore
    private Npc npc;
    @Column(name = "strength")     private int strength;
    @Column(name = "dexterity")    private int dexterity;
    @Column(name = "constitution") private int constitution;
    @Column(name = "intelligence") private int intelligence;
    @Column(name = "wisdom")       private int wisdom;
    @Column(name = "charisma")     private int charisma;
}
