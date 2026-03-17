package ch.Elodin.RealmQuill.model.npcinfo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
@Entity
@Table(name = "npc_personality")
public class Personality {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "npc_personality_ID")
    private int personality_ID;

    
    @Column(name = "description", unique = true)
    private String description;





}
