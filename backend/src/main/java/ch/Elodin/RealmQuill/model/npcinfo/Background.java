package ch.Elodin.RealmQuill.model.npcinfo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "npc_background")
public class Background{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "npc_background_ID")
    private int background_ID;

    @Column(name = "name", unique = true)
    
    private String name;

	public int getBackground_ID() {
		return background_ID;
	}

	public void setBackground_ID(int background_ID) {
		this.background_ID = background_ID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
    
    



    
}