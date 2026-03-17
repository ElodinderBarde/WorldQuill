package ch.Elodin.RealmQuill.model.npcinfo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "npc_level")
public class Level {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "npc_lvl_ID")
    private int lvl;

    @Column(name = "lvl_value", unique = true)
    private Integer lvlvalue;

	public int getLvl() {
		return lvl;
	}

	public void setLvl(int lvl) {
		this.lvl = lvl;
	}

	public Integer getLvlvalue() {
		return lvlvalue;
	}

	public void setLvl_value(Integer lvlvalue) {
		this.lvlvalue = lvlvalue;
	}

	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}
    
    
    
}

    