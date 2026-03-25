package ch.Elodin.RealmQuill.controller.npcinfo;

import ch.Elodin.RealmQuill.controller.GenericController;
import ch.Elodin.RealmQuill.model.npcinfo.Personality;
import ch.Elodin.RealmQuill.repository.npcinfo.PersonalityRepository;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = "http://localhost:5137")
@RestController
@RequestMapping("/api/Personality")
public class PersonalityController extends GenericController<Personality, Integer> {
    public PersonalityController(PersonalityRepository repository) {
        super(repository);
    }
}




