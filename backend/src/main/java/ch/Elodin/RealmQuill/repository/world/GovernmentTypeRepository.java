package ch.Elodin.RealmQuill.repository.world;
import ch.Elodin.RealmQuill.model.world.GovernmentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface GovernmentTypeRepository extends JpaRepository<GovernmentType, Integer> {}