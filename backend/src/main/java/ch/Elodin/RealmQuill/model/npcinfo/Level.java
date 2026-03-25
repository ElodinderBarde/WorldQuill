package ch.Elodin.RealmQuill.model.npcinfo;
import jakarta.persistence.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor
@Entity @Table(name = "npc_level")
public class Level {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "lvl_value", unique = true)
    private Integer lvl;
    // Kompatibilität mit NpcMapper Level::getLvl
    public Integer getLvl() { return lvl; }
}
