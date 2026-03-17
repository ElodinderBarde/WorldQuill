package ch.Elodin.RealmQuill.model.world;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
@Entity
@Table(name = "campaign")
public class Campaign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="campaign_ID")
    private int id;


    @Column(name ="campaign_name")
    private String campaignName;


    @Column(name = "user_id")
    private Integer user_ID;
}
