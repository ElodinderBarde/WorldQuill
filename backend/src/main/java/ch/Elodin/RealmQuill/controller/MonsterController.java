package ch.Elodin.RealmQuill.controller;
import ch.Elodin.RealmQuill.dto.MonsterDTO;
import ch.Elodin.RealmQuill.service.MonsterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@CrossOrigin(origins = "http://localhost:5137")
@RestController @RequestMapping("/api/monsters") @RequiredArgsConstructor
public class MonsterController {
    private final MonsterService monsterService;
    @GetMapping public List<MonsterDTO> getAll() { return monsterService.getAllMonsters(); }
    @GetMapping("/keywords") public List<String> getKeywords() { return monsterService.getSchlagworte(); }
    @GetMapping("/challenge-ratings") public List<Double> getChallengeRatings() { return monsterService.getChallengeLvl(); }
    @GetMapping("/books") public List<String> getBooks() { return monsterService.getBooks(); }
    @GetMapping("/search") public List<MonsterDTO> search(@RequestParam String name) { return monsterService.getByName(name); }
    @GetMapping("/by-keyword/{keyword}") public List<MonsterDTO> byKeyword(@PathVariable String keyword) { return monsterService.getBySchlagworte(keyword); }
    @GetMapping("/by-challenge-rating/{cr}") public List<MonsterDTO> byCR(@PathVariable Double cr) { return monsterService.getByHerausforderungsgrad(cr); }
    @PostMapping @ResponseStatus(HttpStatus.CREATED) public MonsterDTO create(@RequestBody MonsterDTO dto) { return monsterService.createMonster(dto); }
    @PutMapping("/{id}/favorite") public MonsterDTO setFavorite(@PathVariable int id, @RequestBody MonsterDTO dto) { return monsterService.setFavorit(id, dto); }
}