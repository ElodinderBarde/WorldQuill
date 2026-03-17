package ch.Elodin.RealmQuill.repository.shop;

import ch.Elodin.RealmQuill.model.shop.ShopRelations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopRelationsRepository extends JpaRepository<ShopRelations, Integer> {
}



