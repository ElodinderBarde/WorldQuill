package ch.Elodin.RealmQuill.repository.shop;

import ch.Elodin.RealmQuill.model.shop.ShopItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface ShopItemRepository extends JpaRepository<ShopItem, Integer> {

    @Query("SELECT si FROM ShopItem si WHERE si.shop.shopId = :shopId")
    List<ShopItem> findByShopId(@Param("shopId") int shopId);

}
