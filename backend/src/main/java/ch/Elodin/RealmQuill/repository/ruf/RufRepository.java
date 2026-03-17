package ch.Elodin.RealmQuill.repository.ruf;
import ch.Elodin.RealmQuill.model.ruf.Ruf;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RufRepository extends JpaRepository<Ruf, Integer> {
@SuppressWarnings("null")
	@Override
  List<Ruf> findAll();

}


