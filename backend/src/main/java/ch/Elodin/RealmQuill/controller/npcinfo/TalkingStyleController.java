package ch.Elodin.RealmQuill.controller.npcinfo;

import ch.Elodin.RealmQuill.controller.GenericController;
import ch.Elodin.RealmQuill.model.npcinfo.TalkingStyle;
import ch.Elodin.RealmQuill.repository.npcinfo.TalkingStyleRepository;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5137")
@RestController
@RequestMapping("/api/TalkingStyle")
public class TalkingStyleController extends GenericController<TalkingStyle, Integer> {
    public TalkingStyleController(TalkingStyleRepository repository) {
        super(repository);
    }
}




