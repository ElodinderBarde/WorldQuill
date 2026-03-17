package ch.Elodin.RealmQuill.model.shop;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

;

@Getter
@Setter
@Entity
@Table(name = "shop_employee")
public class ShopEmployee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "shop_employee_ID")
	private Integer shop_employee_ID; // Feldname anpassen


	@Column(name = "position")
	private String position;

	// Getter & Setter
}
