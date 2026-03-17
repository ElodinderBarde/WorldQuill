package ch.Elodin.RealmQuill.controller.npcinfo;

import ch.Elodin.RealmQuill.controller.GenericController;
import ch.Elodin.RealmQuill.model.npcinfo.KleidungQuali;
import ch.Elodin.RealmQuill.repository.npcinfo.KleidungQualiRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/KleidungQuali")
public class KleidungQualiController extends GenericController<KleidungQuali, Integer> {
    public KleidungQualiController(KleidungQualiRepository repository) {
        super(repository);
    }
}




