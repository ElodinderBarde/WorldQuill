package ch.Elodin.RealmQuill.model.world;

import ch.Elodin.RealmQuill.model.enums.EnumRegierungsform;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "regierungsform")
public class Regierungsform {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="regierungsformID")
    private int regierungsformID;

    @Column(name ="regierungsform", unique = true)
    @Enumerated(EnumType.STRING)
    private EnumRegierungsform regierungsform;

	public int getRegierungsformID() {
		return regierungsformID;
	}

	public void setRegierungsformID(int regierungsformID) {
		this.regierungsformID = regierungsformID;
	}

	public EnumRegierungsform getRegierungsform() {
		return regierungsform;
	}

	public void setRegierungsform(EnumRegierungsform regierungsform) {
		this.regierungsform = regierungsform;
	}
    
    
}