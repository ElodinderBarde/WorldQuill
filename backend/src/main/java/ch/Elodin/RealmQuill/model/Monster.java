package ch.Elodin.RealmQuill.model;
import jakarta.persistence.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor
@Entity @Table(name = "monster")
public class Monster {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "monster_name")
    private String monsterName;
    @Column(name = "challenge_rating")
    private Double challengeRating;
    @Column(name = "is_shiftable")
    private Integer isShiftable;
    @Column(name = "is_favorite")
    private Integer isFavorite;
    @Column(name = "source_book")
    private String sourceBook;
    @Column(name = "page_1") private Integer page1;
    @Column(name = "page_2") private Integer page2;
    @Column(name = "page_3") private Integer page3;
    @Column(name = "keyword")
    private String keyword;
    @Column(name = "monster_index")
    private Integer monsterIndex;
    @Column(name = "description")
    private String description;
}
