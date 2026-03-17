package ch.Elodin.RealmQuill.model.npcinfo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "npc_dislikes")
public class Dislikes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "npc_dislikes_ID")
    private int dislikes_ID;

    
    @Column(name = "description", unique = true)
    private String description;


	public int getDislikes_ID() {
		return dislikes_ID;
	}


	public void setDislikes_ID(int dislikes_ID) {
		this.dislikes_ID = dislikes_ID;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}






}
