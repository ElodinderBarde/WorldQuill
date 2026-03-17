package ch.Elodin.RealmQuill.model.npcinfo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
@Entity
@Table(name = "npc_subclass")

public class Subclass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "npc_subclass_ID")
    private int subclass_ID;

    
    @Column(name = "name", unique = true)
    private String subclassname;

	@ManyToOne
	@JoinColumn(name = "npc_class_ID")
	private NpcClass class_ID;



}
