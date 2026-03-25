package ch.Elodin.RealmQuill.model.npcinfo;
import jakarta.persistence.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor
@Entity @Table(name = "npc_first_name")
public class Firstname {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "first_name", unique = true)
    private String firstname;
    @ManyToOne
    @JoinColumn(name = "gender_id")
    private Gender gender;
    @ManyToOne
    @JoinColumn(name = "race_ID")
    private Race race;
    // Kompatibilität mit NpcMapper.getFirstname()
    public String getFirstname() { return firstname; }
}
