package ch.Elodin.RealmQuill.model.npcinfo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name="npc_beardstyle")
public class Beardstyle {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="npc_beardstyle_ID")
	private int beardstyle_ID;
	
	@Column(name="name", unique = true)
	private String name;

	public int getBeardstyle_ID() {
		return beardstyle_ID;
	}

	public void setBeardstyle_ID(int beardstyle_ID) {
		this.beardstyle_ID = beardstyle_ID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
	
}
