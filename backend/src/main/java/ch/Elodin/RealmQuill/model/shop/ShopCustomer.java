package ch.Elodin.RealmQuill.model.shop;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "shop_customer")
public class ShopCustomer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shop_customer_ID")
    private Integer shop_customer_ID; // Feldname anpassen


    @Column(name = "position")
    private String position;

}
