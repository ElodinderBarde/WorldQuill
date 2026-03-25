package ch.Elodin.RealmQuill.model.shop;
import jakarta.persistence.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor
@Entity @Table(name = "shop_employee")
public class ShopEmployee {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shop_employee_ID")
    private int id;
    @Column(name = "position")
    private String position;
}
