package ch.Elodin.RealmQuill.model.npcinfo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "npc_betonung")
public class Betonung {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "npc_betonung_id")
    private int betonung_ID;

    
    @Column(name = "betonung", unique = true)
    private String betonung;


	public int getBetonung_ID() {
		return betonung_ID;
	}


	public void setBetonung_ID(int betonung_ID) {
		this.betonung_ID = betonung_ID;
	}


	public String getBetonung() {
		return betonung;
	}


	public void setBetonung(String betonung) {
		this.betonung = betonung;
	}


	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}



    



}
