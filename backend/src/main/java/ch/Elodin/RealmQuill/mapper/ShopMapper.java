package ch.Elodin.RealmQuill.mapper;

import ch.Elodin.RealmQuill.dto.ShopDTO;
import ch.Elodin.RealmQuill.model.shop.Shop;
import java.util.List;

public class ShopMapper {

    public static ShopDTO toDTO(Shop shop) {
        ShopDTO dto = new ShopDTO();
        dto.setId(shop.getShopId());
        dto.setName(shop.getName());
        dto.setNotes(shop.getNotes());

        if (shop.getShop_type() != null) {
            dto.setShopTypeId(shop.getShop_type().getId());
            dto.setShopTypeName(shop.getShop_type().getName());
        }

        if (shop.getLocation() != null && shop.getLocation().getCityID() != null) {
            dto.setCityId(shop.getLocation().getCityID().getId());
            dto.setCityName(shop.getLocation().getCityID().getCity_name());
        }

        return dto;
    }

    public static List<ShopDTO> toDTOList(List<Shop> shops) {
        return shops.stream().map(ShopMapper::toDTO).toList();
    }
}



