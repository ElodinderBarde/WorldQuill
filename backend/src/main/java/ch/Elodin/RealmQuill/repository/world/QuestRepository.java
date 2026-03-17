package ch.Elodin.RealmQuill.repository.world;
import ch.Elodin.RealmQuill.model.Quest;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestRepository extends JpaRepository<Quest, Integer> {

@SuppressWarnings("null")
	@Override
    List<Quest> findAll();


    List<Quest> findByActiveTrue();

}


