package ch.Elodin.RealmQuill.controller.npcinfo;

import ch.Elodin.RealmQuill.controller.GenericController;
import ch.Elodin.RealmQuill.model.npcinfo.Dislikes;
import ch.Elodin.RealmQuill.repository.npcinfo.DislikesRepository;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = "http://localhost:5137")
@RestController
@RequestMapping("/api/Dislikes")
public class DislikesController extends GenericController<Dislikes, Integer> {
    public DislikesController(DislikesRepository repository) {
        super(repository);
    }
}



