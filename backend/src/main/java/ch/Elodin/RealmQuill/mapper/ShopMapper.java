package ch.Elodin.RealmQuill.mapper;
import ch.Elodin.RealmQuill.dto.ShopDTO;
import ch.Elodin.RealmQuill.model.shop.Shop;
import java.util.List;
public class ShopMapper {
    public static ShopDTO toDTO(Shop shop) {
        ShopDTO dto = new ShopDTO();
        dto.setId(shop.getShopId()); dto.setName(shop.getName()); dto.setNotes(shop.getNotes());
        if (shop.getShopType() != null) {
            dto.setShopTypeId(shop.getShopType().getId());
            dto.setShopTypeName(shop.getShopType().getName());
        }
        if (shop.getLocation() != null) {
            dto.setLocationId(shop.getLocation().getId());
            if (shop.getLocation().getCity() != null)
                dto.setCityName(shop.getLocation().getCity().getCityName());
        }
        if (shop.getCampaign() != null) dto.setCampaignId(shop.getCampaign().getId());
        return dto;
    }
    public static List<ShopDTO> toDTOList(List<Shop> shops) {
        return shops.stream().map(ShopMapper::toDTO).toList();
    }
}