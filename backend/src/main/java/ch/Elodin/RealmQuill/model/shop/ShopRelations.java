package ch.Elodin.RealmQuill.model.shop;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
@Entity
@Table(name = "shop_relations")
public class ShopRelations {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shop_relations_ID")
    private int shop_relations;

    @ManyToOne
    @JoinColumn(name = "shop_ID")
    private Shop shop;

    @ManyToOne
    @JoinColumn(name = "shop_employee_ID", referencedColumnName = "shop_employee_ID")
    private ShopEmployee shopEmployeeRole;

    @ManyToOne
    @JoinColumn(name = "shop_customer_ID", referencedColumnName = "shop_customer_ID")
    private ShopCustomer shopCustomerRole;



}