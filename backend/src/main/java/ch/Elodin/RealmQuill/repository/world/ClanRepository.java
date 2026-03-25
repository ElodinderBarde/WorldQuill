package ch.Elodin.RealmQuill.repository.world;

import ch.Elodin.RealmQuill.model.Clan;
import ch.Elodin.RealmQuill.model.enums.Familienclan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClanRepository extends JpaRepository<Clan, Integer> {

    Optional<Clan> findByClan(String clanName);

    @Query("SELECT c FROM Clan c WHERE c.campaign = :campaignId")
    List<Clan> findByCampaignId(@Param("campaignId") int campaignId);

    @Query("SELECT c.clan FROM Clan c")
    List<String> findAllClanNames();

    // Familienclan-Enum korrekt als String-Literal in JPQL
    @Query("SELECT c FROM Clan c WHERE c.isFamilyClan = 'Y'")
    List<Clan> findAllFamilienclans();

    @Query("SELECT c FROM Clan c WHERE c.isFamilyClan = 'N'")
    List<Clan> findAllNonFamilienclans();
}
