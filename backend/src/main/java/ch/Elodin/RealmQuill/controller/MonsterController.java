package ch.Elodin.RealmQuill.controller;

import ch.Elodin.RealmQuill.dto.MonsterDTO;
import ch.Elodin.RealmQuill.service.MonsterService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5137")
@RestController
@RequestMapping("/api/monsters")
public class MonsterController {

    private final MonsterService monsterService;

    public MonsterController(MonsterService monsterService) {
        this.monsterService = monsterService;
    }

    // =========================
    // READ
    // =========================

    @GetMapping
    public List<MonsterDTO> getAllMonsters() {
        return monsterService.getAllMonsters();
    }

    @GetMapping("/schlagworte")
    public List<String> getAllSchlagworte() {
        return monsterService.getSchlagworte();
    }

    @GetMapping("/herausforderungsgrade")
    public List<Double> getAllHerausforderungsgrade() {
        return monsterService.getChallengeLvl();
    }

    @GetMapping("/search")
    public List<MonsterDTO> searchByName(@RequestParam String name) {
        return monsterService.getByName(name);
    }
    @GetMapping("/books")

    public List<String> getAllBooks() {
        return monsterService.getBooks();
    }

    // Optional: Filter (bestehende Logik behalten)
    @GetMapping("/schlagworte/{schlagwort}")
    public List<MonsterDTO> getBySchlagwort(@PathVariable String schlagwort) {
        return monsterService.getBySchlagworte(schlagwort);
    }

    @GetMapping("/herausforderungsgrade/{hg}")
    public List<MonsterDTO> getByHG(@PathVariable Double hg) {
        return monsterService.getByHerausforderungsgrad(hg);
    }

    // =========================
    // CREATE
    // =========================

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MonsterDTO createMonster(@RequestBody MonsterDTO monster) {
        return monsterService.createMonster(monster);
    }

    // =========================
    // UPDATE
    // =========================

    @PutMapping("/{id}/favorit")
    public MonsterDTO setFavorit(@PathVariable int id, @RequestBody MonsterDTO monster) {
        return monsterService.setFavorit(id, monster);
    }
}