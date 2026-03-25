package ch.Elodin.RealmQuill.model.shop;
import ch.Elodin.RealmQuill.model.Item;
import jakarta.persistence.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor
@Entity @Table(name = "shop_items")
public class ShopItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shop_item_id")
    private int id;
    @ManyToOne
    @JoinColumn(name = "shop_ID", referencedColumnName = "shop_ID")
    private Shop shop;
    @ManyToOne
    @JoinColumn(name = "itemID", referencedColumnName = "id")
    private Item item;
    @Column(name = "quantity")
    private Integer quantity;
    @Column(name = "special_price")
    private Double specialPrice;
    @Column(name = "discount")
    private Double discount;
}
