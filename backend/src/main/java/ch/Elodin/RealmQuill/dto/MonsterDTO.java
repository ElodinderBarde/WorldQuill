package ch.Elodin.RealmQuill.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MonsterDTO {

    private Integer id;
    private String name;
    private String description;
    private Double challengeLvl;
    private Integer shiftable;
    private String book;
    private Integer page1;
    private Integer page2;
    private Integer page3;
    private Integer favorit;
    private String schlagwort;
    private Integer monsterIndex;

    public MonsterDTO() {}

    public MonsterDTO(Integer id, String name, String description, Double challengeLvl, Integer shiftable,
                      String book, Integer page1, Integer page2, Integer page3,
                      Integer favorit, String schlagwort, Integer index) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.challengeLvl = challengeLvl;
        this.shiftable = shiftable;
        this.book = book;
        this.page1 = page1;
        this.page2 = page2;
        this.page3 = page3;
        this.favorit = favorit;
        this.schlagwort = schlagwort;
        this.monsterIndex = index;
    }
}