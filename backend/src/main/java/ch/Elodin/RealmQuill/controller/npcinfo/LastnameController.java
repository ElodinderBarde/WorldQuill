package ch.Elodin.RealmQuill.controller.npcinfo;

import ch.Elodin.RealmQuill.controller.GenericController;
import ch.Elodin.RealmQuill.model.npcinfo.Lastname;
import ch.Elodin.RealmQuill.repository.npcinfo.LastnameRepository;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = "http://localhost:5137")
@RestController
@RequestMapping("/api/Lastname")
public class LastnameController extends GenericController<Lastname, Integer> {
    public LastnameController(LastnameRepository repository) {
        super(repository);
    }
}




