package ch.Elodin.RealmQuill.model.world;
import jakarta.persistence.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor
@Entity @Table(name = "village")
public class Village {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "village_ID")
    private int id;
    @Column(name = "village_name", unique = true)
    private String name;
    @Column(name = "notes")
    private String notes;
}
