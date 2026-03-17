package ch.Elodin.RealmQuill.repository.world;

import ch.Elodin.RealmQuill.model.world.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {
}



