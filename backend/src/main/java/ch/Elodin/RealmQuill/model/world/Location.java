package ch.Elodin.RealmQuill.model.world;
import ch.Elodin.RealmQuill.model.Quest;
import jakarta.persistence.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor
@Entity @Table(name = "location")
public class Location {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_ID")
    private int id;
    @ManyToOne
    @JoinColumn(name = "village_ID", unique = true)
    private Village village;
    @ManyToOne
    @JoinColumn(name = "city_ID", unique = true)
    private City city;
    @ManyToOne
    @JoinColumn(name = "government_type_id")
    private GovernmentType governmentType;
    @Column(name = "settlement_reputation")
    private int settlementReputation;
    @ManyToOne
    @JoinColumn(name = "campaign_ID")
    private Campaign campaign;
    @ManyToOne
    @JoinColumn(name = "questlocation")
    private Quest questlocation;

    @Override
    public String toString() {
        if (city != null) return city.getCityName();
        if (village != null) return village.getName();
        return "Unbekannter Ort";
    }

}
