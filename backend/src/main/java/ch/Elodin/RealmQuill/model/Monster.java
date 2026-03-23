package ch.Elodin.RealmQuill.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "monster")
public class Monster{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Monster_ID")
	private Integer MonsterID;

	@Column(name = "monster_name")
	private String monsterName;

	@Column(name = "hg")
	private Double herausforderungsgrad;

	@Column(name = "shiftable")
	private Integer shiftable;

	@Column(name = "favorit")
	private Integer favorit;

	@Column(name = "buch")
	private String buch;

	@Column(name = "seite_1")
	private Integer seite1;

	@Column(name = "seite_2")
	private Integer seite2;
	
	@Column(name = "seite_3")
	private Integer seite3;

	@Column(name = "schlagwort")
	private String schlagworte;

	@Column(name = "monster_index")
	private Integer monsterIndex;

	@Column(name="beschreibung")
	private String beschreibung;


}