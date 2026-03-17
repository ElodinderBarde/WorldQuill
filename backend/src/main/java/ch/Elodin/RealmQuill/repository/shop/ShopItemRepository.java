package ch.Elodin.RealmQuill.repository.shop;

import ch.Elodin.RealmQuill.model.shop.ShopItem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopItemRepository extends JpaRepository<ShopItem, Integer> {
    List<ShopItem> findByShop_ShopId(Integer shopId);

    List<ShopItem> findByShop_ShopIdAndQuantityGreaterThan(Integer shopId, Integer quantity);
}



