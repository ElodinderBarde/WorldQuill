package ch.Elodin.RealmQuill.model;

import ch.Elodin.RealmQuill.model.enums.Familienclan;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "items")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {

	@Id
	@JsonProperty("id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "itemID")
	private int itemID;

	@Column(name = "itemName")
	private String itemName;

	@Column(name = "price")
	private int price;

	@Column(name = "Typ")
	private String typ;

	@Column(name = "seltenheit")
	private String seltenheit;

	@Column(name = "buch")
	private String buch;

	@Column(name = "seite1")
	private Integer seite1;

	@Column(name = "seite2")
	private Integer seite2;

	@Column(name = "seite3")
	private Integer seite3;

	@Column(name = "einstimmung")
	@Enumerated(EnumType.STRING)
	private Familienclan einstimmung;

	@Column(name = "beschreibung")
	private String beschreibung;

	// Falls ein Mapper getId() benÃ¶tigt:
	public int getId() {
		return this.itemID;
	}
}
