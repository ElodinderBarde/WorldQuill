package ch.Elodin.RealmQuill.model.npcinfo;
import ch.Elodin.RealmQuill.model.enums.EnumClothes;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "npc_hairstyle")
public class Hairstyle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "npc_hairstyle_ID")
    private int hairstyle_ID;

    @Column(name = "name", unique = true)
    private String name;
    
    
    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private EnumClothes gender;


	public int getHairstyle_ID() {
		return hairstyle_ID;
	}


	public void setHairstyle_ID(int hairstyle_ID) {
		this.hairstyle_ID = hairstyle_ID;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public EnumClothes getGender() {
		return gender;
	}


	public void setGender(EnumClothes gender) {
		this.gender = gender;
	}
	
	





    
    
}