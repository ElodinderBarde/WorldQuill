package ch.Elodin.RealmQuill.model.world;
import jakarta.persistence.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor
@Entity @Table(name = "city")
public class City {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "city_ID")
    private int id;
    @Column(name = "city_name", unique = true)
    private String cityName;
    @Column(name = "citymap")
    private String citymap;
    @Column(name = "notes")
    private String notes;
    // Kompatibilität mit Location.toString()
    public String getCity_name() { return cityName; }
}
