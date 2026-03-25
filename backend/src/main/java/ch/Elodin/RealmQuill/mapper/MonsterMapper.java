package ch.Elodin.RealmQuill.mapper;
import ch.Elodin.RealmQuill.dto.MonsterDTO;
import ch.Elodin.RealmQuill.model.Monster;
import java.util.List;
import java.util.Objects;
public class MonsterMapper {
    public static MonsterDTO toDTO(Monster e) {
        if (e == null) return null;
        MonsterDTO dto = new MonsterDTO();
        dto.setId(e.getId());
        dto.setName(e.getMonsterName());
        dto.setDescription(e.getDescription());
        dto.setChallengeRating(e.getChallengeRating());
        dto.setIsShiftable(e.getIsShiftable());
        dto.setSourceBook(e.getSourceBook());
        dto.setPage1(e.getPage1()); dto.setPage2(e.getPage2()); dto.setPage3(e.getPage3());
        dto.setIsFavorite(e.getIsFavorite());
        dto.setKeyword(e.getKeyword());
        dto.setMonsterIndex(e.getMonsterIndex());
        return dto;
    }
    public static List<MonsterDTO> toDTOList(List<Monster> list) {
        if (list == null) return List.of();
        return list.stream().filter(Objects::nonNull).map(MonsterMapper::toDTO).toList();
    }
    public static Monster toEntity(MonsterDTO dto) {
        if (dto == null) return null;
        Monster e = new Monster();
        e.setMonsterName(dto.getName()); e.setDescription(dto.getDescription());
        e.setIsFavorite(dto.getIsFavorite()); e.setSourceBook(dto.getSourceBook());
        e.setPage1(dto.getPage1()); e.setPage2(dto.getPage2()); e.setPage3(dto.getPage3());
        e.setChallengeRating(dto.getChallengeRating()); e.setKeyword(dto.getKeyword());
        e.setIsShiftable(dto.getIsShiftable()); e.setMonsterIndex(dto.getMonsterIndex());
        return e;
    }
    public static void updateEntity(Monster e, MonsterDTO dto) {
        if (e == null || dto == null) return;
        if (dto.getName() != null) e.setMonsterName(dto.getName());
        if (dto.getDescription() != null) e.setDescription(dto.getDescription());
        if (dto.getIsFavorite() != null) e.setIsFavorite(dto.getIsFavorite());
        if (dto.getSourceBook() != null) e.setSourceBook(dto.getSourceBook());
        if (dto.getPage1() != null) e.setPage1(dto.getPage1());
        if (dto.getPage2() != null) e.setPage2(dto.getPage2());
        if (dto.getPage3() != null) e.setPage3(dto.getPage3());
        if (dto.getChallengeRating() != null) e.setChallengeRating(dto.getChallengeRating());
        if (dto.getKeyword() != null) e.setKeyword(dto.getKeyword());
        if (dto.getIsShiftable() != null) e.setIsShiftable(dto.getIsShiftable());
        if (dto.getMonsterIndex() != null) e.setMonsterIndex(dto.getMonsterIndex());
    }
}