package ch.Elodin.RealmQuill.service.shop;
import ch.Elodin.RealmQuill.dto.ShopDTO;
import ch.Elodin.RealmQuill.mapper.ShopMapper;
import ch.Elodin.RealmQuill.model.shop.Shop;
import ch.Elodin.RealmQuill.model.shop.ShopType;
import ch.Elodin.RealmQuill.model.world.Location;
import ch.Elodin.RealmQuill.repository.shop.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
@Service @RequiredArgsConstructor
public class ShopService {
    private final ShopRepository shopRepository;
    public List<ShopDTO> getAllShops() { return ShopMapper.toDTOList(shopRepository.findAll()); }
    public Optional<Shop> getShopById(Integer id) { return shopRepository.findById(id); }
    public Shop createShop(Shop shop) { return shopRepository.save(shop); }
    public Shop saveShop(Shop shop) { return shopRepository.save(shop); }
    public void deleteShop(Integer id) { shopRepository.deleteById(id); }
    public Shop fromDTO(ShopDTO dto, ShopType shopType, Location location) {
        Shop shop = new Shop();
        shop.setShopId(dto.getId()); shop.setName(dto.getName()); shop.setNotes(dto.getNotes());
        shop.setShopType(shopType); shop.setLocation(location);
        return shop;
    }

    public ShopDTO toDTO(Shop shop) {
        return new ShopDTO(
                shop.getShopId(),
                shop.getName(),
                shop.getShopType() != null ? shop.getShopType().getId() : 0,
                shop.getShopType() != null ? shop.getShopType().getName() : null,
                shop.getLocation() != null ? shop.getLocation().getId() : 0,
                shop.getLocation() != null ? shop.getLocation().toString() : null,
                shop.getLocation() != null && shop.getLocation().getCity() != null
                        ? shop.getLocation().getCity().getCityName() : null,
                shop.getLocation() != null && shop.getLocation().getCity() != null
                        ? shop.getLocation().getCity().getId() : 0,
                shop.getNotes()
        );
    }
}

