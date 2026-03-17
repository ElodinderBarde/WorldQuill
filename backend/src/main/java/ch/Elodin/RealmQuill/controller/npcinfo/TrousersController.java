package ch.Elodin.RealmQuill.controller.npcinfo;

import ch.Elodin.RealmQuill.controller.GenericController;
import ch.Elodin.RealmQuill.model.npcinfo.Trousers;
import ch.Elodin.RealmQuill.repository.npcinfo.TrousersRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/Trousers")
public class TrousersController extends GenericController<Trousers, Integer> {
    public TrousersController(TrousersRepository repository) {
        super(repository);
    }
}




