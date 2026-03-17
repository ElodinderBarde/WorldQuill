package ch.Elodin.RealmQuill.model.npcinfo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "npc_picture")
public class Picture{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "npc_picture_ID")
    private int picture_ID;

    @Column(name = "picture")
    private String picture;

	public int getPicture_ID() {
		return picture_ID;
	}

	public void setPicture_ID(int picture_ID) {
		this.picture_ID = picture_ID;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}



}