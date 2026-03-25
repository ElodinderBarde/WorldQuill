package ch.Elodin.RealmQuill.model.npcinfo;
import jakarta.persistence.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor
@Entity @Table(name = "npc_subclass")
public class Subclass {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "name", unique = true)
    private String subclassname;
    @ManyToOne
    @JoinColumn(name = "class_id")
    private NpcClass npcClass;

}
