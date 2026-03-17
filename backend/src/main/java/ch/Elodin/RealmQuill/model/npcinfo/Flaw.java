package ch.Elodin.RealmQuill.model.npcinfo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "npc_flaw")
public class Flaw {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "npc_flaw_ID")
    private int flaw_ID;

    @Column(name = "name", unique = true)
    private String name;
    
    
    @Column(name = "description")
    private String description;


	public int getFlaw_ID() {
		return flaw_ID;
	}


	public void setFlaw_ID(int flaw_ID) {
		this.flaw_ID = flaw_ID;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}






}
