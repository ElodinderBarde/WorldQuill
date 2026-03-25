package ch.Elodin.RealmQuill.controller.npcinfo;

import ch.Elodin.RealmQuill.controller.GenericController;
import ch.Elodin.RealmQuill.model.npcinfo.Jackets;
import ch.Elodin.RealmQuill.repository.npcinfo.JacketsRepository;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = "http://localhost:5137")
@RestController
@RequestMapping("/api/Jackets")
public class JacketsController extends GenericController<Jackets, Integer> {
    public JacketsController(JacketsRepository repository) {
        super(repository);
    }
}




