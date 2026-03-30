package ch.Elodin.RealmQuill.repository.shop;
import ch.Elodin.RealmQuill.model.shop.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Integer> {

    @Query("SELECT s FROM Shop s WHERE s.campaign.id = :campaignId")
    List<Shop> findByCampaignId(@Param("campaignId") int campaignId);

    @Query("SELECT s FROM Shop s WHERE s.location.id = :locationId")
    List<Shop> findByLocationId(@Param("locationId") int locationId);

}
