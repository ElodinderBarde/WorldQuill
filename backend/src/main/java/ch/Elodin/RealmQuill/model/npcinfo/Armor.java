package ch.Elodin.RealmQuill.model.npcinfo;
import jakarta.persistence.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor
@Entity @Table(name = "npc_armor")
public class Armor {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "acvalue")
    private int acValue;
}
