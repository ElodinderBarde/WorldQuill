package ch.Elodin.RealmQuill.repository.world;
import ch.Elodin.RealmQuill.model.Quest;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestRepository extends JpaRepository<Quest, Integer> {

@Override
List<Quest> findAll();


    List<Quest> findByActiveTrue();


    @Query("SELECT Quest FROM Quest Quest WHERE Quest.questlocation.id = :id")
    List<Quest> findByLocationId(int id);
}


