package ch.Elodin.RealmQuill.dto;

import ch.Elodin.RealmQuill.model.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShopItemDTO {
    private Integer shopItemId;
    private Integer shopId;
    private Integer itemId;
    private Integer quantity;
    private ItemDTO item;
}



