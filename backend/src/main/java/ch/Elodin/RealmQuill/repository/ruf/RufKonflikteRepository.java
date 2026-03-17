package ch.Elodin.RealmQuill.repository.ruf;

import ch.Elodin.RealmQuill.model.ruf.RufKonflikte;
import ch.Elodin.RealmQuill.model.ruf.RufKonflikteKey;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RufKonflikteRepository extends JpaRepository<RufKonflikte, RufKonflikteKey> {
    
    // Du brauchst das hier eigentlich nicht, da findAll() bereits von JpaRepository geerbt wird.
    // Falls du es dennoch explizit deklarieren willst:
    @Override
    @SuppressWarnings("null")
    List<RufKonflikte> findAll();

}



