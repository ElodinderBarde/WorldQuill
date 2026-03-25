package ch.Elodin.RealmQuill.controller.npcinfo;

import ch.Elodin.RealmQuill.controller.GenericController;
import ch.Elodin.RealmQuill.model.npcinfo.Firstname;
import ch.Elodin.RealmQuill.repository.npcinfo.FirstnameRepository;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = "http://localhost:5137")
@RestController
@RequestMapping("/api/Firstname")
public class FirstnameController extends GenericController<Firstname, Integer> {
    public FirstnameController(FirstnameRepository repository) {
        super(repository);
    }
}



