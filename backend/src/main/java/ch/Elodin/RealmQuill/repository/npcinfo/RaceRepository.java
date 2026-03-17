package ch.Elodin.RealmQuill.repository.npcinfo;

import ch.Elodin.RealmQuill.model.npcinfo.Race;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RaceRepository extends JpaRepository<Race, Integer> {
	// nichts Ã¼berschreiben â€“ findAll(), findById(), save() etc. kommen automatisch
}



