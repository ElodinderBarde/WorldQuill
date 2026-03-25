package ch.Elodin.RealmQuill.controller.npcinfo;

import ch.Elodin.RealmQuill.controller.GenericController;
import ch.Elodin.RealmQuill.model.npcinfo.Beardstyle;
import ch.Elodin.RealmQuill.repository.npcinfo.BeardstyleRepository;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5137")
@RestController
@RequestMapping("/api/Beardstyle")
public class BeardstyleController extends GenericController<Beardstyle, Integer> {
    public BeardstyleController(BeardstyleRepository repository) {
        super(repository);
    }
}



