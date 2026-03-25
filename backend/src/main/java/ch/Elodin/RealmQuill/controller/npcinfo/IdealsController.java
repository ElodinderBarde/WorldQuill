package ch.Elodin.RealmQuill.controller.npcinfo;

import ch.Elodin.RealmQuill.controller.GenericController;
import ch.Elodin.RealmQuill.model.npcinfo.Ideals;
import ch.Elodin.RealmQuill.repository.npcinfo.IdealsRepository;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = "http://localhost:5137")
@RestController
@RequestMapping("/api/Ideals")
public class IdealsController extends GenericController<Ideals, Integer> {
    public IdealsController(IdealsRepository repository) {
        super(repository);
    }
}




