package ch.Elodin.RealmQuill.mapper;

import ch.Elodin.RealmQuill.dto.ItemDTO;
import ch.Elodin.RealmQuill.dto.ShopItemDTO;
import ch.Elodin.RealmQuill.model.shop.ShopItem;
import java.util.List;

public class ShopItemMapper {

    public static ShopItemDTO toDTO(ShopItem entity) {
        ShopItemDTO dto = new ShopItemDTO();
        dto.setShopItemId(entity.getShopItemId());
        dto.setShopId(entity.getShop().getShopId());
        dto.setItemId(entity.getItem().getItemID());
        dto.setQuantity(entity.getQuantity());

        // ItemDTO korrekt befÃ¼llen
        if (entity.getItem() != null) {
            ItemDTO itemDTO = new ItemDTO();
            itemDTO.setId(entity.getItem().getItemID());
            itemDTO.setName(entity.getItem().getItemName());
            itemDTO.setPrice(entity.getItem().getPrice());
            itemDTO.setTyp(entity.getItem().getTyp());
            itemDTO.setSeltenheit(entity.getItem().getSeltenheit());
            itemDTO.setBuch(entity.getItem().getBuch());
            itemDTO.setSeite1(entity.getItem().getSeite1());
            itemDTO.setSeite2(entity.getItem().getSeite2());
            itemDTO.setSeite3(entity.getItem().getSeite3());
            itemDTO.setEinstimmung(entity.getItem().getEinstimmung());
            itemDTO.setBeschreibung(entity.getItem().getBeschreibung());

            dto.setItem(itemDTO);
        }

        return dto;
    }

    // Optional: Batch-Mapping
    public static List<ShopItemDTO> toDTOList(List<ShopItem> entities) {
        return entities.stream().map(ShopItemMapper::toDTO).toList();
    }
}




