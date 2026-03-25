package ch.Elodin.RealmQuill.model.npcinfo;
import jakarta.persistence.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor
@Entity @Table(name = "npc_gender")
public class Gender {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "npc_gender")
    private String gendername;
}
