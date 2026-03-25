package ch.Elodin.RealmQuill.model.shop;
import jakarta.persistence.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor
@Entity @Table(name = "shop_type")
public class ShopType {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shop_type_ID")
    private int id;
    @Column(name = "name")
    private String name;
}
