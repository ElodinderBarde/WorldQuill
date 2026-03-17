package ch.Elodin.RealmQuill.model.npcinfo;
import lombok.Getter;
import jakarta.persistence.*;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "npc_kleidungsqualitÃƒÂ¤t")
public class KleidungQuali {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "npc_kleidungsqualitÃƒÂ¤t_ID")
    private int kleidungsQuali;

    @Column(name = "description", unique = true)
    private String description;

    
    
    
    

}