package ch.Elodin.RealmQuill.repository.world;
import ch.Elodin.RealmQuill.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {
    @Query("SELECT DISTINCT i.sourceBook FROM Item i WHERE i.sourceBook IS NOT NULL")
    List<String> findDistinctSourceBooks();
}