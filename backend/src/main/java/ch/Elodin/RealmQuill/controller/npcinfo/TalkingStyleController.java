package ch.Elodin.RealmQuill.controller.npcinfo;

import ch.Elodin.RealmQuill.controller.GenericController;
import ch.Elodin.RealmQuill.model.npcinfo.TalkingStyle;
import ch.Elodin.RealmQuill.repository.npcinfo.TalkingStyleRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/TalkingStyle")
public class TalkingStyleController extends GenericController<TalkingStyle, Integer> {
    public TalkingStyleController(TalkingStyleRepository repository) {
        super(repository);
    }
}




