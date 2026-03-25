package ch.Elodin.RealmQuill.model.npcinfo;
import jakarta.persistence.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor
@Entity @Table(name = "npc_last_name")
public class Lastname {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "last_name", unique = true)
    private String lastname;
    @ManyToOne
    @JoinColumn(name = "race_id")
    private Race race;
    // Kompatibilität mit NpcMapper.getLastname()
    public String getLastname() { return lastname; }
}
