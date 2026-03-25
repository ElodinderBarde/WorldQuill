package ch.Elodin.RealmQuill.repository.npcinfo;
import ch.Elodin.RealmQuill.model.npcinfo.Family;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface FamilyRepository extends JpaRepository<Family, Integer> {
    Optional<Family> findByFamilyName(String familyName);
}
