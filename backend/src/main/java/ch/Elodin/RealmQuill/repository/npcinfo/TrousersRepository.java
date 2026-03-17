package ch.Elodin.RealmQuill.repository.npcinfo;



import ch.Elodin.RealmQuill.model.npcinfo.Trousers;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrousersRepository extends JpaRepository<Trousers, Integer> {

	@SuppressWarnings("null")
	@Override
	List<Trousers> findAll();

	Optional<Trousers> findByname(String name);


}


