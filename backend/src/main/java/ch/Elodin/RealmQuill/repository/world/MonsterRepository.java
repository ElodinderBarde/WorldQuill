package ch.Elodin.RealmQuill.repository.world;
import ch.Elodin.RealmQuill.model.Monster;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonsterRepository extends JpaRepository<Monster, Integer> {
@SuppressWarnings("null")
@Override
List<Monster> findAll();

}


