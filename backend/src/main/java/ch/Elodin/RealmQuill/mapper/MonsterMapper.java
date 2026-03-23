package ch.Elodin.RealmQuill.mapper;

import ch.Elodin.RealmQuill.dto.MonsterDTO;
import ch.Elodin.RealmQuill.model.Monster;

import java.util.List;
import java.util.Objects;

public class MonsterMapper {

    // =========================
    // ENTITY → DTO
    // =========================
    public static MonsterDTO toDTO(Monster entity) {
        if (entity == null) return null;

        MonsterDTO dto = new MonsterDTO();
        dto.setId(entity.getMonsterID());
        dto.setName(entity.getMonsterName());
        dto.setDescription(entity.getBeschreibung());
        dto.setChallengeLvl(entity.getHerausforderungsgrad());
        dto.setShiftable(entity.getShiftable());
        dto.setBook(entity.getBuch());
        dto.setPage1(entity.getSeite1());
        dto.setPage2(entity.getSeite2());
        dto.setPage3(entity.getSeite3());
        dto.setFavorit(entity.getFavorit());
        dto.setSchlagwort(entity.getSchlagworte());
        dto.setMonsterIndex(entity.getMonsterIndex());

        return dto;
    }

    public static List<MonsterDTO> toDTOList(List<Monster> entities) {
        if (entities == null) return List.of();

        return entities.stream()
                .filter(Objects::nonNull)
                .map(MonsterMapper::toDTO)
                .toList();
    }

    // =========================
    // DTO → ENTITY (CREATE)
    // =========================
    public static Monster toEntity(MonsterDTO dto) {
        if (dto == null) return null;

        Monster entity = new Monster();

        // KEIN setMonsterID → DB übernimmt das
        entity.setMonsterName(dto.getName());
        entity.setBeschreibung(dto.getDescription());
        entity.setFavorit(dto.getFavorit());
        entity.setBuch(dto.getBook());
        entity.setSeite1(dto.getPage1());
        entity.setSeite2(dto.getPage2());
        entity.setSeite3(dto.getPage3());
        entity.setHerausforderungsgrad(dto.getChallengeLvl());
        entity.setSchlagworte(dto.getSchlagwort());
        entity.setShiftable(dto.getShiftable());
        entity.setMonsterIndex(dto.getMonsterIndex());

        return entity;
    }

    // =========================
    // DTO → ENTITY (UPDATE)
    // =========================
    public static void updateEntity(Monster entity, MonsterDTO dto) {
        if (entity == null || dto == null) return;

        // nur Felder setzen, die geändert werden dürfen
        if (dto.getName() != null) entity.setMonsterName(dto.getName());
        if (dto.getDescription() != null) entity.setBeschreibung(dto.getDescription());
        if (dto.getFavorit() != null) entity.setFavorit(dto.getFavorit());
        if (dto.getBook() != null) entity.setBuch(dto.getBook());
        if (dto.getPage1() != null) entity.setSeite1(dto.getPage1());
        if (dto.getPage2() != null) entity.setSeite2(dto.getPage2());
        if (dto.getPage3() != null) entity.setSeite3(dto.getPage3());
        if (dto.getChallengeLvl() != null) entity.setHerausforderungsgrad(dto.getChallengeLvl());
        if (dto.getSchlagwort() != null) entity.setSchlagworte(dto.getSchlagwort());
        if (dto.getShiftable() != null) entity.setShiftable(dto.getShiftable());
        if (dto.getMonsterIndex() != null) entity.setMonsterIndex(dto.getMonsterIndex());
    }
}