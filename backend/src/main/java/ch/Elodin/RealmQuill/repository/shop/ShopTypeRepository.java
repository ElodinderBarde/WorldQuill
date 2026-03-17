package ch.Elodin.RealmQuill.repository.shop;

import ch.Elodin.RealmQuill.model.shop.ShopType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopTypeRepository extends JpaRepository<ShopType, Integer> {
}



