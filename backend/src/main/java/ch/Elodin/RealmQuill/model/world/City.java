package ch.Elodin.RealmQuill.model.world;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "city")
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="city_ID")
    private int id;

    @Column(name ="city_name", unique = true)
    private String city_name;
    
    @Column(name = "citymap")
    private String citymap;

    
    @Column(name="notes")
    private String notes;
    
    
    
	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCity_name() {
		return city_name;
	}

	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}

	public String getCitymap() {
		return citymap;
	}

	public void setCitymap(String citymap) {
		this.citymap = citymap;
	}
 


}