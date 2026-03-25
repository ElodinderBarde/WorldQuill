package ch.Elodin.RealmQuill.dto;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ShopItemDTO {
    private Integer shopItemId;
    private Integer shopId;
    private Integer itemId;
    private Integer quantity;
    private Double specialPrice;
    private Double discount;
    private ItemDTO item;
}
