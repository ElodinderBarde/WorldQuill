package ch.Elodin.RealmQuill.model;
import lombok.*;
import jakarta.persistence.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Entity @Table(name = "items")
public class Item {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "item_name")
    private String itemName;
    @Column(name = "price")
    private int price;
    @Column(name = "item_type")
    private String itemType;
    @Column(name = "rarity")
    private String rarity;
    @Column(name = "source_book")
    private String sourceBook;
    @Column(name = "page_1") private Integer page1;
    @Column(name = "page_2") private Integer page2;
    @Column(name = "page_3") private Integer page3;
    @Column(name = "attunement", columnDefinition = "ENUM('Y','N')")
    private String attunement;
    @Column(name = "description")
    private String description;
}
