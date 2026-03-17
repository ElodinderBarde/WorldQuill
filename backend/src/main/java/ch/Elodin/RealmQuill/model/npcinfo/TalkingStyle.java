package ch.Elodin.RealmQuill.model.npcinfo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "npc_talkingstyle")
public class TalkingStyle{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "npc_talkingstyle_ID")
    private int tralkingstyle_ID;

    @Column(name = "description", unique = true)
    private String description;

	public int getTralkingstyle_ID() {
		return tralkingstyle_ID;
	}

	public void setTralkingstyle_ID(int tralkingstyle_ID) {
		this.tralkingstyle_ID = tralkingstyle_ID;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Object getTalkingstyle() {
		// TODO Auto-generated method stub
		return description;
	}
    
    
    
}