package ch.Elodin.RealmQuill.repository.npcinfo;



import ch.Elodin.RealmQuill.model.npcinfo.TalkingStyle;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TalkingStyleRepository extends JpaRepository<TalkingStyle, Integer> {
@SuppressWarnings("null")
	@Override
	List<TalkingStyle> findAll();

	Optional<TalkingStyle> findBydescription(String description);


}


