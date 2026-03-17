package ch.Elodin.RealmQuill.model.npcinfo;
import ch.Elodin.RealmQuill.model.enums.Verbreitung;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
@Entity
@Table(name = "race")
public class Race {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "race_ID")
    private int id;

    @Column(name = "racename", unique = true)
    private String racename;

    @Column(name = "maxage")
    private int maxAge;

    @Column(name = "oldage")
    private int oldAge;

    @Column(name = "adultage")
    private int adultAge;

    @Column(name = "teenage")
    private int teenage;

    @Column(name = "promt", length = 1000)
    private String prompt;

    @Column(name = "verbreitung")
    @Enumerated(EnumType.STRING)
    private Verbreitung verbreitung;

    @Column(name = "verbreitung_in_promille")
    private int verbreitungInPromille;



}
