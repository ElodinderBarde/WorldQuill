package ch.Elodin.RealmQuill.model.npcinfo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "npc_ideals")
public class Ideals {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "npc_ideals_ID")
    private int ideals;

    @Column(name = "description", unique = true)
    private String description;

	public int getIdeals() {
		return ideals;
	}

	public void setIdeals(int ideals) {
		this.ideals = ideals;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}
    
    

}