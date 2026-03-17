package ch.Elodin.RealmQuill.controller.npcinfo;

import ch.Elodin.RealmQuill.controller.GenericController;
import ch.Elodin.RealmQuill.model.npcinfo.Dislikes;
import ch.Elodin.RealmQuill.repository.npcinfo.DislikesRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/Dislikes")
public class DislikesController extends GenericController<Dislikes, Integer> {
    public DislikesController(DislikesRepository repository) {
        super(repository);
    }
}



