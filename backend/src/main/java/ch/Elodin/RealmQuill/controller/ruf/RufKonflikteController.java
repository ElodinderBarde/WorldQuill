package ch.Elodin.RealmQuill.controller.ruf;

import ch.Elodin.RealmQuill.controller.GenericController;
import ch.Elodin.RealmQuill.model.ruf.RufKonflikte;
import ch.Elodin.RealmQuill.model.ruf.RufKonflikteKey;
import ch.Elodin.RealmQuill.repository.ruf.RufKonflikteRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/RufKonflikte")
public class RufKonflikteController extends GenericController<RufKonflikte, RufKonflikteKey> {
    public RufKonflikteController(RufKonflikteRepository repository) {
        super(repository);
    }
}





