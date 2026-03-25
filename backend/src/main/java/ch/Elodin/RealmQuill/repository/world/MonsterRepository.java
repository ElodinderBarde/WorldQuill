package ch.Elodin.RealmQuill.repository.world;
import ch.Elodin.RealmQuill.model.Monster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface MonsterRepository extends JpaRepository<Monster, Integer> {
    List<Monster> findByChallengeRating(Double cr);
    List<Monster> findByKeywordContaining(String keyword);
    List<Monster> findByMonsterNameContainingIgnoreCase(String name);
}