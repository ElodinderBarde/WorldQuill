package ch.Elodin.RealmQuill.repository.world;

import ch.Elodin.RealmQuill.model.Clan;
import java.util.Collection;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ClanRepository extends JpaRepository<Clan, Integer> {
    Optional<Clan> findByClan(String clan);

    @Query("SELECT c.clan FROM Clan c")
    Collection<String> findAllClanNames();


    @Query("SELECT c.location FROM  Clan c")
    String locationID();

    @Query("select c mitglieder from Clan c")
    Integer mitgliederAnzahl();

    @Query("SELECT c.familienclan FROM Clan c")
    Collection<String> familienclanValues();

    @Query("SELECT c FROM Clan c WHERE c.familienclan = 'Y'")
    Collection<Clan> findAllFamilienclans();

    @Query("SELECT c FROM Clan c WHERE c.familienclan = 'N'")
    Collection<Clan> findAllNonFamilienclans();

    @Query("SELECT COUNT(c) FROM Clan c")
    Integer countAllClans();



}




