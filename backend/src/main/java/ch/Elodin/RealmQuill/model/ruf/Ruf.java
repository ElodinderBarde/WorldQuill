package ch.Elodin.RealmQuill.model.ruf;
import ch.Elodin.RealmQuill.model.Clan;
import ch.Elodin.RealmQuill.model.world.Location;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "ruf")
public class Ruf {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ruf_ID")
	private int RufID;

	@Column(name = "partei")
	private String partei;

	@Column(name = "amount")
	private int amount;

	
	
	@ManyToOne
	@JoinColumn(name = "location_ID")
	private Location location;

	
	@ManyToOne
	@JoinColumn(name = "fk_clan_ID")
	private Clan clan;

	public int getRufID() {
		return RufID;
	}

	public void setRufID(int rufID) {
		RufID = rufID;
	}

	public String getPartei() {
		return partei;
	}

	public void setPartei(String partei) {
		this.partei = partei;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocationID(Location location) {
		this.location = location;
	}

	public Clan getClan() {
		return clan;
	}

	public void setClan(Clan clan) {
		this.clan = clan;
	}



	
	



	
	
	
}