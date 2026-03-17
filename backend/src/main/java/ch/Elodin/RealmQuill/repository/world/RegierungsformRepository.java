package ch.Elodin.RealmQuill.repository.world;

import ch.Elodin.RealmQuill.model.world.Regierungsform;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegierungsformRepository extends JpaRepository<Regierungsform, Integer> {
}



