package ch.Elodin.RealmQuill.service;
import ch.Elodin.RealmQuill.dto.MonsterDTO;
import ch.Elodin.RealmQuill.mapper.MonsterMapper;
import ch.Elodin.RealmQuill.model.Monster;
import ch.Elodin.RealmQuill.repository.world.MonsterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Objects;
@Service @RequiredArgsConstructor
public class MonsterService {
    private final MonsterRepository monsterRepository;
    public List<MonsterDTO> getAllMonsters() { return MonsterMapper.toDTOList(monsterRepository.findAll()); }
    public List<MonsterDTO> getBySchlagworte(String keyword) { return MonsterMapper.toDTOList(monsterRepository.findByKeywordContaining(keyword)); }
    public List<MonsterDTO> getByHerausforderungsgrad(Double cr) { return MonsterMapper.toDTOList(monsterRepository.findByChallengeRating(cr)); }
    public List<String> getSchlagworte() {
        return monsterRepository.findAll().stream().map(Monster::getKeyword).filter(Objects::nonNull)
            .flatMap(s -> List.of(s.split(",")).stream()).map(String::trim).filter(s -> !s.isEmpty()).distinct().toList();
    }
    public List<Double> getChallengeLvl() { return monsterRepository.findAll().stream().map(Monster::getChallengeRating).distinct().toList(); }
    public List<String> getBooks() { return monsterRepository.findAll().stream().map(Monster::getSourceBook).filter(b -> b != null && !b.isBlank()).distinct().toList(); }
    public List<MonsterDTO> getByName(String name) { return MonsterMapper.toDTOList(monsterRepository.findByMonsterNameContainingIgnoreCase(name)); }
    public MonsterDTO createMonster(MonsterDTO dto) {
        if (dto == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        return MonsterMapper.toDTO(monsterRepository.save(MonsterMapper.toEntity(dto)));
    }
    public MonsterDTO setFavorit(Integer id, MonsterDTO dto) {
        Monster e = monsterRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (dto.getIsFavorite() == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        e.setIsFavorite(dto.getIsFavorite());
        return MonsterMapper.toDTO(monsterRepository.save(e));
    }
}