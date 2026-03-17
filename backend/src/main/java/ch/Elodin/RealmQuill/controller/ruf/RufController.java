package ch.Elodin.RealmQuill.controller.ruf;

import ch.Elodin.RealmQuill.controller.GenericController;
import ch.Elodin.RealmQuill.model.ruf.Ruf;
import ch.Elodin.RealmQuill.repository.ruf.RufRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/Ruf")
public class RufController extends GenericController<Ruf, Integer> {
    public RufController(RufRepository repository) {
        super(repository);
    }
}




