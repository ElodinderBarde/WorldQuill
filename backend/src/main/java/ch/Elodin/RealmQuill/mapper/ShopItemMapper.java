package ch.Elodin.RealmQuill.mapper;
import ch.Elodin.RealmQuill.dto.ItemDTO;
import ch.Elodin.RealmQuill.dto.ShopItemDTO;
import ch.Elodin.RealmQuill.model.shop.ShopItem;
import java.util.List;
public class ShopItemMapper {
    public static ShopItemDTO toDTO(ShopItem entity) {
        ShopItemDTO dto = new ShopItemDTO();
        dto.setShopItemId(entity.getId());
        dto.setShopId(entity.getShop().getShopId());
        dto.setItemId(entity.getItem().getId());
        dto.setQuantity(entity.getQuantity());
        dto.setSpecialPrice(entity.getSpecialPrice());
        dto.setDiscount(entity.getDiscount());
        if (entity.getItem() != null) {
            var item = entity.getItem();
            dto.setItem(new ItemDTO(item.getId(), item.getItemName(), item.getPrice(),
                item.getItemType(), item.getRarity(), item.getSourceBook(),
                item.getPage1(), item.getPage2(), item.getPage3(),
                item.getAttunement(), item.getDescription()));
        }
        return dto;
    }
    public static List<ShopItemDTO> toDTOList(List<ShopItem> entities) {
        return entities.stream().map(ShopItemMapper::toDTO).toList();
    }
}