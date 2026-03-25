package ch.Elodin.RealmQuill.repository.ruf;
import ch.Elodin.RealmQuill.model.ruf.Reputation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReputationRepository extends JpaRepository<Reputation, Integer> {

    @Query("SELECT r FROM Reputation r WHERE r.clan.id = :clanId")
    List<Reputation> findByClanId(@Param("clanId") int clanId);
}
