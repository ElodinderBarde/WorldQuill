package ch.Elodin.RealmQuill.model.world;
import jakarta.persistence.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor
@Entity @Table(name = "campaign")
public class Campaign {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "campaign_ID")
    private int id;
    @Column(name = "campaign_name")
    private String campaignName;

    @Column (name = "user_id")
    private int userId;

    @Column(name = "app_user_id")
    private Long appUserId;
}
