package ch.Elodin.RealmQuill.model.world;
import ch.Elodin.RealmQuill.model.enums.EnumRegierungsform;
import jakarta.persistence.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor
@Entity @Table(name = "government_type")
public class GovernmentType {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "name", unique = true)
    @Enumerated(EnumType.STRING)
    private EnumRegierungsform name;
}
