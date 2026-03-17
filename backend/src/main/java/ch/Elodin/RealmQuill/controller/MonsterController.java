package ch.Elodin.RealmQuill.controller;

import ch.Elodin.RealmQuill.model.Monster;
import ch.Elodin.RealmQuill.repository.world.MonsterRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/Monster")
public class MonsterController extends ch.Elodin.RealmQuill.controller.GenericController<Monster, Integer> {
    public MonsterController(MonsterRepository repository) {
        super(repository);
    }
}




