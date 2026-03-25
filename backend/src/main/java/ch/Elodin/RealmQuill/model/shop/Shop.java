package ch.Elodin.RealmQuill.model.shop;
import ch.Elodin.RealmQuill.model.world.Campaign;
import ch.Elodin.RealmQuill.model.world.Location;
import jakarta.persistence.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor
@Entity @Table(name = "shop")
public class Shop {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shop_ID")
    private int shopId;
    @Column(name = "name")
    private String name;
    @ManyToOne
    @JoinColumn(name = "shop_type_ID")
    private ShopType shopType;
    @ManyToOne
    @JoinColumn(name = "location_ID")
    private Location location;
    @Column(name = "notes")
    private String notes;
    @ManyToOne
    @JoinColumn(name = "campaign_ID")
    private Campaign campaign;
}
