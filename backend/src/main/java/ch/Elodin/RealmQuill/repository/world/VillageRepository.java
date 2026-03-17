package ch.Elodin.RealmQuill.repository.world;

import ch.Elodin.RealmQuill.model.world.Village;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VillageRepository extends JpaRepository<Village, Integer> {
}



