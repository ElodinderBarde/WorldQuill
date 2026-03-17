package ch.Elodin.RealmQuill.model.npcinfo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "npc_jewellery")
public class Jewellery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "npc_jewellery_ID")
    private int jewellery;

    @Column(name = "name", unique = true)
    private String name;

	public int getJewellery_ID() {
		return jewellery;
	}

	public void setJewellery_ID(int jewellery_ID) {
		this.jewellery = jewellery_ID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


    

}