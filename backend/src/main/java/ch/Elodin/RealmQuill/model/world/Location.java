package ch.Elodin.RealmQuill.model.world;



import ch.Elodin.RealmQuill.model.Quest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "location")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="location_ID")
    private int id;



    @ManyToOne
    @JoinColumn(name ="village_ID", unique = true)
    private Village villageID;
    

    @ManyToOne
    @JoinColumn(name ="city_ID", unique = true)
    private City cityID;

    @ManyToOne
    @JoinColumn(name= "regierungsformID")
    private Regierungsform regierungsformID;
    
    
    @Column(name ="ortschaft_ruf")
    private int ortschaftRuf;

    @ManyToOne
    @JoinColumn(name = "campaign_ID")
    private Campaign campaign;
    
    @ManyToOne
    @JoinColumn(name="questlocation")
    private Quest questlocation;

	@Override
	public String toString() {
		String ort = "";
		if (cityID != null) {
			ort += cityID.getCity_name();
		} else if (villageID != null) {
			ort += villageID.getName();
		}



		return ort.isEmpty() ? "Unbekannter Ort" : ort;
	}



}