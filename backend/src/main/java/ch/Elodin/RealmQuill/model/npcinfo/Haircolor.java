package ch.Elodin.RealmQuill.model.npcinfo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "npc_haircolor")
public class Haircolor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "npc_haircolor_ID")
    private int haircolor_ID;

    @Column(name = "name", unique = true)
    private String name;

	public int getHaircolor_ID() {
		return haircolor_ID;
	}

	public void setHaircolor_ID(int haircolor_ID) {
		this.haircolor_ID = haircolor_ID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
    
    


    
    
    
    

    
}