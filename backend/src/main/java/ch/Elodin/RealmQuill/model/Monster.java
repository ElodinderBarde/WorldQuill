package ch.Elodin.RealmQuill.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "monster")
public class Monster {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Monster_ID")
	private int MonsterID;

	@Column(name = "monster_name")
	private String MonsterName;

	@Column(name = "hg")
	private int herausforderungsgrad;

	@Column(name = "shiftable")

	private int shiftable;

	@Column(name = "favorit")
	private int favorit;

	@Column(name = "buch")
	private String buch;

	@Column(name = "seite 1")
	private Integer seite1;

	@Column(name = "seite 2")
	private Integer seite2;
	
	@Column(name = "seite 3")
	private Integer seite3;

	@Column(name = "schlagworte")
	private String schlagworte;

	@Column(name = "index")
	private int index;

	public int getMonsterID() {
		return MonsterID;
	}

	public void setMonsterID(int monsterID) {
		MonsterID = monsterID;
	}

	public String getMonsterName() {
		return MonsterName;
	}

	public void setMonsterName(String monsterName) {
		MonsterName = monsterName;
	}

	public int getHerausforderungsgrad() {
		return herausforderungsgrad;
	}

	public void setHerausforderungsgrad(int herausforderungsgrad) {
		this.herausforderungsgrad = herausforderungsgrad;
	}

	public int getShiftable() {
		return shiftable;
	}

	public void setShiftable(int shiftable) {
		this.shiftable = shiftable;
	}

	public int getFavorit() {
		return favorit;
	}

	public void setFavorit(int favorit) {
		this.favorit = favorit;
	}

	public String getBuch() {
		return buch;
	}

	public void setBuch(String buch) {
		this.buch = buch;
	}

	public Integer getSeite1() {
		return seite1;
	}

	public void setSeite1(Integer seite1) {
		this.seite1 = seite1;
	}

	public Integer getSeite2() {
		return seite2;
	}

	public void setSeite2(Integer seite2) {
		this.seite2 = seite2;
	}

	public Integer getSeite3() {
		return seite3;
	}

	public void setSeite3(Integer seite3) {
		this.seite3 = seite3;
	}

	public String getSchlagworte() {
		return schlagworte;
	}

	public void setSchlagworte(String schlagworte) {
		this.schlagworte = schlagworte;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	
	
	
	
}