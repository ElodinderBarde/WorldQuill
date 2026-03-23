package ch.Elodin.RealmQuill.service;

import ch.Elodin.RealmQuill.dto.MonsterDTO;
import ch.Elodin.RealmQuill.mapper.MonsterMapper;
import ch.Elodin.RealmQuill.model.Monster;
import ch.Elodin.RealmQuill.repository.world.MonsterRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@Service
public class MonsterService {

    private final MonsterRepository monsterRepository;

    public MonsterService(MonsterRepository monsterRepository) {
        this.monsterRepository = monsterRepository;
    }

    // =========================
    // READ
    // =========================

    public List<MonsterDTO> getAllMonsters() {
        return MonsterMapper.toDTOList(monsterRepository.findAll());
    }

    public List<MonsterDTO> getBySchlagworte(String schlagwort) {
        return MonsterMapper.toDTOList(
                monsterRepository.findBySchlagworteContaining(schlagwort)
        );
    }

    public List<MonsterDTO> getByHerausforderungsgrad(Double hg) {
        return MonsterMapper.toDTOList(
                monsterRepository.findByHerausforderungsgrad(hg)
        );
    }

    public List<String> getSchlagworte() {
        return monsterRepository.findAll().stream()
                .map(Monster::getSchlagworte)
                .filter(Objects::nonNull)
                .flatMap(s -> List.of(s.split(",")).stream())
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .distinct()
                .toList();
    }

    public List<Double> getChallengeLvl() {
        return monsterRepository.findAll().stream()
                .map(Monster::getHerausforderungsgrad)
                .distinct()
                .toList();
    }

    public List<String> getBooks() {
        return monsterRepository.findAll().stream()
                .map(Monster::getBuch)
                .filter(b -> b != null && !b.isBlank())
                .distinct()
                .toList();
    }

    public List<MonsterDTO> getByName(String name) {
        return MonsterMapper.toDTOList(
                monsterRepository.findByMonsterNameContainingIgnoreCase(name)
        );
    }
    // =========================
    // CREATE
    // =========================

    public MonsterDTO createMonster(MonsterDTO dto) {
        if (dto == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Monster darf nicht null sein");
        }

        Monster entity = MonsterMapper.toEntity(dto);

        Monster saved = monsterRepository.save(entity);
        return MonsterMapper.toDTO(saved);
    }

    // =========================
    // UPDATE
    // =========================

    public MonsterDTO setFavorit(Integer id, MonsterDTO dto) {
        Monster existing = monsterRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Monster nicht gefunden")
                );

        if (dto.getFavorit() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Favorit darf nicht null sein");
        }

        existing.setFavorit(dto.getFavorit());

        Monster saved = monsterRepository.save(existing);
        return MonsterMapper.toDTO(saved);
    }
}