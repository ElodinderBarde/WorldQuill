package ch.Elodin.RealmQuill.model.shop;
import ch.Elodin.RealmQuill.model.world.Campaign;
import ch.Elodin.RealmQuill.model.world.Location;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "shop")
public class Shop{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shop_ID")
    private int shopId;

    
    
    @Column(name = "name")
    private String name;
    
    @ManyToOne
    @JoinColumn(name = "shop_type_ID")
    private ShopType shop_type;

    @ManyToOne
    @JoinColumn(name ="location_ID")
    private Location location;


    
    @Column(name ="notes")
    private String notes;

    @ManyToOne
    @JoinColumn(name = "campaign_ID")
    private Campaign campaign;


    public int getShopId() { return shopId; }
    public void setShopId(int shopId) { this.shopId = shopId; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ShopType getShop_type() {
        return shop_type;
    }

    public void setShop_type(ShopType shop_type) {
        this.shop_type = shop_type;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }


}