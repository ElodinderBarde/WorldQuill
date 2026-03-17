package ch.Elodin.RealmQuill.model;

import ch.Elodin.RealmQuill.model.enums.Familienclan;
import ch.Elodin.RealmQuill.model.ruf.Ruf;
import ch.Elodin.RealmQuill.model.ruf.RufKonflikte;
import ch.Elodin.RealmQuill.model.world.Location;
import java.util.List;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

;

@Entity
@Table(name = "npc_clan")
public class Clan {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="clan_ID")
    private int id;

    @Getter
    @Setter
    @Column(name ="clanname", unique = true)
    private String clan;

	@Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_ID")
    private Location location;
    
    @Getter
    @Setter
    @Column(name = "mitglieder")
    private Integer mitglieder;
    
    @Getter
    @Setter
    @Column(name = "Familienclan", columnDefinition = "ENUM('Y','N')")
    @Enumerated(EnumType.STRING)
    private Familienclan familienclan;
    
    @OneToMany(mappedBy = "partei_source")
    private List<RufKonflikte> rufkonfliktsource;

    @OneToMany(mappedBy = "partei_target")
    private List<RufKonflikte> rufkonflikttarget;


	@Getter
    @Setter
    @Column(name = "clan_notes")
	private String clanNotes;

    
    @OneToMany(mappedBy = "clan", fetch = FetchType.LAZY)
    private List<Ruf> rufe;


    public Integer getRufkonfliktsource()
    {return this.rufkonfliktsource.size();}


}
