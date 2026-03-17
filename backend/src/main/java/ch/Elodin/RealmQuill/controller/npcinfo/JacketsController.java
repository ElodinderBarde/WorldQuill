package ch.Elodin.RealmQuill.controller.npcinfo;

import ch.Elodin.RealmQuill.controller.GenericController;
import ch.Elodin.RealmQuill.model.npcinfo.Jackets;
import ch.Elodin.RealmQuill.repository.npcinfo.JacketsRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/Jackets")
public class JacketsController extends GenericController<Jackets, Integer> {
    public JacketsController(JacketsRepository repository) {
        super(repository);
    }
}




