package ch.Elodin.RealmQuill.controller.npcinfo;

import ch.Elodin.RealmQuill.controller.GenericController;
import ch.Elodin.RealmQuill.model.npcinfo.Gender;
import ch.Elodin.RealmQuill.repository.npcinfo.GenderRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/Gender")
public class GenderController extends GenericController<Gender, Integer> {
    public GenderController(GenderRepository repository) {
        super(repository);
    }
}




