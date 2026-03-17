package ch.Elodin.RealmQuill.model.npcinfo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "npc_likes")
public class Likes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "npc_likes_ID")
    private int likes_ID;

    
    @Column(name = "description", unique = true)
    private String description;


	public int getLikes_ID() {
		return likes_ID;
	}


	public void setLikes_ID(int likes_ID) {
		this.likes_ID = likes_ID;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}





}
