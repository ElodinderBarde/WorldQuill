package ch.Elodin.RealmQuill.repository.world;

import ch.Elodin.RealmQuill.model.world.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location, Integer> {


}



