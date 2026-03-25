package ch.Elodin.RealmQuill.model.npcinfo;
import jakarta.persistence.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor
@Entity @Table(name = "npc_description")
public class OtherDescription {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "description", unique = true)
    private String description;
    // Kompatibilität mit NpcMapper.getText()
    public String getText() { return description; }
}
