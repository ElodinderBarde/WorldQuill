package ch.Elodin.RealmQuill.repository.shop;

import ch.Elodin.RealmQuill.model.shop.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Integer> {

}



