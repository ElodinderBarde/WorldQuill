package ch.Elodin.RealmQuill.controller.npcinfo;

import ch.Elodin.RealmQuill.controller.GenericController;
import ch.Elodin.RealmQuill.model.npcinfo.Background;
import ch.Elodin.RealmQuill.repository.npcinfo.BackgroundRepository;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = "http://localhost:5137")
@RestController
@RequestMapping("/api/background")
public class BackgroundController extends GenericController<Background, Integer> {
    public BackgroundController(BackgroundRepository repository) {
        super(repository);
    }
}



