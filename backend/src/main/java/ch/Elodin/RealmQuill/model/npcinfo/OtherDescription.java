package ch.Elodin.RealmQuill.model.npcinfo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "npc_other_description")
public class OtherDescription{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "npc_other_description_ID")
    private int otherDescription_ID;

    @Column(name = "description", unique = true)
    		private String description;

	public int getOtherDescription_ID() {
		return otherDescription_ID;
	}

	public void setOtherDescription_ID(int otherDescription_ID) {
		this.otherDescription_ID = otherDescription_ID;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getText() {
		// TODO Auto-generated method stub
		return null;
	}
    
    
}
