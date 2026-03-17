package ch.Elodin.RealmQuill.controller.npcinfo;

import ch.Elodin.RealmQuill.controller.GenericController;
import ch.Elodin.RealmQuill.model.npcinfo.Lastname;
import ch.Elodin.RealmQuill.repository.npcinfo.LastnameRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/Lastname")
public class LastnameController extends GenericController<Lastname, Integer> {
    public LastnameController(LastnameRepository repository) {
        super(repository);
    }
}




