package ch.Elodin.RealmQuill.model.npcinfo;
import ch.Elodin.RealmQuill.model.enums.Verbreitung;
import jakarta.persistence.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor
@Entity @Table(name = "race")
public class Race {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "race_ID")
    private int id;
    @Column(name = "racename", unique = true)
    private String racename;
    @Column(name = "max_age")
    private int maxAge;
    @Column(name = "adult_age")
    private int adultAge;
    @Column(name = "teen_age")
    private int teenAge;
    @Column(name = "promt", length = 1000)
    private String prompt;
    @Column(name = "verbreitung")
    @Enumerated(EnumType.STRING)
    private Verbreitung verbreitung;
    @Column(name = "verbreitung_in_promille")
    private int verbreitungInPromille;
}
