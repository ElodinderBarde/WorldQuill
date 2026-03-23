
package ch.Elodin.RealmQuill.dto;

import ch.Elodin.RealmQuill.model.enums.Familienclan;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ItemDTO {

    private int id;
    private String name;
    private int price;
    private String typ;
    private String seltenheit;
    private String buch;
    private Integer seite1;
    private Integer seite2;
    private Integer seite3;
    private Familienclan einstimmung;
    private String beschreibung;


    public ItemDTO(int id, String name, int price, String typ, String seltenheit,
                   String buch, Integer seite1, Integer seite2, Integer seite3,
                   Familienclan einstimmung, String beschreibung) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.typ = typ;
        this.seltenheit = seltenheit;
        this.buch = buch;
        this.seite1 = seite1;
        this.seite2 = seite2;
        this.seite3 = seite3;
        this.einstimmung = einstimmung;
        this.beschreibung = beschreibung;
    }

    public ItemDTO() {
    }


}
