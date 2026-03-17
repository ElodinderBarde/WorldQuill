package ch.Elodin.RealmQuill.model.npcinfo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "npc_armorclass")
public class Armor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="npc_armorclass_ID")
    private int id;

    @Column(name ="acvalue")
    private int acvalue;
    
    @Column(name = "npc_id")
    private Integer npc_id;



	public int getArmor() {
		// TODO Auto-generated method stub
		return acvalue;
	}
    



    
}
