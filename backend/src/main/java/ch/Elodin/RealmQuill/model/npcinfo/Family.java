package ch.Elodin.RealmQuill.model.npcinfo;
import ch.Elodin.RealmQuill.model.enums.Familienclan;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "npc_family")
public class Family {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "npc_family_ID")
    private int family_ID;

    @Column(name = "Familienclan", columnDefinition = "ENUM('Y','N')")
    @Enumerated(EnumType.STRING)
    private Familienclan familienclan;
    
    
    @Column(name = "position")
    private String position;
    
    @Column(name = "familienname")
    private String familienname;
    
    

	public String getFamilienname() {
		return familienname;
	}


	public void setFamilienname(String familienname) {
		this.familienname = familienname;
	}


	public int getFamily_ID() {
		return family_ID;
	}


	public void setFamily_ID(int family_ID) {
		this.family_ID = family_ID;
	}


	public Familienclan getFamilienclan() {
		return familienclan;
	}


	public void setFamilienclan(Familienclan familienclan) {
		this.familienclan = familienclan;
	}


	public String getPosition() {
		return position;
	}


	public void setPosition(String position) {
		this.position = position;
	}

    
    
    
}