package ch.Elodin.RealmQuill.dto;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ItemDTO {
    private int id;
    private String name;
    private int price;
    private String itemType;
    private String rarity;
    private String sourceBook;
    private Integer page1;
    private Integer page2;
    private Integer page3;
    private String attunement;
    private String description;
}
