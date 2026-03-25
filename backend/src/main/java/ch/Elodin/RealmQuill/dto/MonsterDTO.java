package ch.Elodin.RealmQuill.dto;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class MonsterDTO {
    private Integer id;
    private String name;
    private String description;
    private Double challengeRating;
    private Integer isShiftable;
    private String sourceBook;
    private Integer page1;
    private Integer page2;
    private Integer page3;
    private Integer isFavorite;
    private String keyword;
    private Integer monsterIndex;
}
