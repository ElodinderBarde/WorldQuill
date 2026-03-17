package ch.Elodin.RealmQuill.model.npcinfo;
import ch.Elodin.RealmQuill.model.enums.EnumClothes;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "npc_trousers")
public class Trousers{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "npc_trousers_ID")
    private int trousers_ID;

    @Column(name="gender")
    @Enumerated(EnumType.STRING)
    private EnumClothes gender;
    
    
    
    @Column(name = "name", unique = true)
    private String name;



	public int getTrousers_ID() {
		return trousers_ID;
	}



	public void setTrousers_ID(int trousers_ID) {
		this.trousers_ID = trousers_ID;
	}



	public EnumClothes getGender() {
		return gender;
	}



	public void setGender(EnumClothes gender) {
		this.gender = gender;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}





}