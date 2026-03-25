package ch.Elodin.RealmQuill.model.npcinfo;
import ch.Elodin.RealmQuill.model.enums.Familienclan;
import jakarta.persistence.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor
@Entity @Table(name = "npc_family_group")
public class Family {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "family_name")
    private String familyName;
    @Column(name = "position")
    private String position;
    @Column(name = "is_family_clan", columnDefinition = "ENUM('Y','N')")
    @Enumerated(EnumType.STRING)
    private Familienclan isFamilyClan;
    // Kompatibilität mit NpcMapper.getFamilienname()
    public String getFamilienname() { return familyName; }
}
