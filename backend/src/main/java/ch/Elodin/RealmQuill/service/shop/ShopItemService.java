package ch.Elodin.RealmQuill.service.shop;

import ch.Elodin.RealmQuill.dto.ShopItemDTO;
import ch.Elodin.RealmQuill.mapper.ShopItemMapper;
import ch.Elodin.RealmQuill.model.Item;
import ch.Elodin.RealmQuill.model.shop.Shop;
import ch.Elodin.RealmQuill.model.shop.ShopItem;
import ch.Elodin.RealmQuill.repository.shop.ShopItemRepository;
import ch.Elodin.RealmQuill.repository.shop.ShopRepository;
import ch.Elodin.RealmQuill.repository.world.ItemRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ShopItemService {

    private final ShopItemRepository shopItemRepository;
    private final ShopRepository shopRepository;
    private final ItemRepository itemRepository;

    public ShopItemService(ShopItemRepository shopItemRepository,
                           ShopRepository shopRepository,
                           ItemRepository itemRepository) {
        this.shopItemRepository = shopItemRepository;
        this.shopRepository = shopRepository;
        this.itemRepository = itemRepository;
    }

    /**
     * Gibt alle Items eines Shops als DTO-Liste zurÃ¼ck.
     */
    public List<ShopItemDTO> getItemsByShopId(Integer shopId) {
        return shopItemRepository.findByShop_ShopId(shopId).stream()
                .map(ShopItemMapper::toDTO)
                .toList();
    }


    public void updateQuantity(Integer id, Integer newQuantity) {
        ShopItem shopItem = shopItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ShopItem nicht gefunden: ID " + id));

        shopItem.setQuantity(newQuantity);
        ShopItem updated = shopItemRepository.save(shopItem);

        ShopItemMapper.toDTO(updated);
    }

    /**
     * FÃ¼gt eine Liste an Items zu einem Shop hinzu und gibt DTOs der neuen EintrÃ¤ge zurÃ¼ck.
     */
    public List<ShopItemDTO> addItemsToShop(List<ShopItemDTO> itemsToAdd) {
        List<ShopItemDTO> result = new ArrayList<>();

        for (ShopItemDTO dto : itemsToAdd) {
            Shop shop = shopRepository.findById(dto.getShopId())
                    .orElseThrow(() -> new RuntimeException("Shop nicht gefunden: ID " + dto.getShopId()));

            Item item = itemRepository.findById(dto.getItemId())
                    .orElseThrow(() -> new RuntimeException("Item nicht gefunden: ID " + dto.getItemId()));

            ShopItem shopItem = new ShopItem();
            shopItem.setShop(shop);
            shopItem.setItem(item);
            shopItem.setQuantity(dto.getQuantity());

            ShopItem saved = shopItemRepository.save(shopItem);
            result.add(ShopItemMapper.toDTO(saved));
        }

        return result;
    }
}



