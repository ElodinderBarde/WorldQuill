package ch.Elodin.RealmQuill.controller.world;

import ch.Elodin.RealmQuill.controller.GenericController;
import ch.Elodin.RealmQuill.model.world.Regierungsform;
import ch.Elodin.RealmQuill.repository.world.RegierungsformRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/Regierungsform")
public class RegierungsformController extends GenericController<Regierungsform, Integer> {
    public RegierungsformController(RegierungsformRepository repository) {
        super(repository);
    }
}




