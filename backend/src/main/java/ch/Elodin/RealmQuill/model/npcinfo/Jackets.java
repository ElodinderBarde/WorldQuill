package ch.Elodin.RealmQuill.model.npcinfo;
import ch.Elodin.RealmQuill.model.enums.EnumClothes;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
@Entity
@Table(name = "npc_jackets")
public class Jackets {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "npc_jackets_ID")
    private int jackets_ID;

    @Column(name = "name", unique = true)
    private String name;
    
    
    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private EnumClothes gender;








    
}