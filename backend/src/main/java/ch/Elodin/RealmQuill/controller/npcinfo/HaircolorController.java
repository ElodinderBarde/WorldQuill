package ch.Elodin.RealmQuill.controller.npcinfo;

import ch.Elodin.RealmQuill.controller.GenericController;
import ch.Elodin.RealmQuill.model.npcinfo.Haircolor;
import ch.Elodin.RealmQuill.repository.npcinfo.HaircolorRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/Haircolor")
public class HaircolorController extends GenericController<Haircolor, Integer> {
    public HaircolorController(HaircolorRepository repository) {
        super(repository);
    }
}




