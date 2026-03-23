package ch.Elodin.RealmQuill.dto;
import ch.Elodin.RealmQuill.model.enums.EnumSymbol;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class NpcReadDTO {

    private int npcId;

    private String firstname;
    private String lastname;
    private String clan;
    private String clanPosition;
    private Integer age;
    private String race;
    private String gender;

    private String npcClass;
    private String subclass;
    private String background;

    private String personality;
    private String flaw;
    private String ideals;

    private String likes;
    private String dislikes;

    private String hairstyle;
    private String haircolor;
    private String beardstyle;
    private String talkingStyle;

    private String jackets;
    private String trousers;
    private String kleidungsQuali;
    private String jewellery;
    private Integer armor;
    private String betonung;

    private String pictureUrl;
    private String otherDescription;
    private String notes;

    private Integer level;

    private StatsDTO stats;



    private String shopRelation;
    private String family;
    private EnumSymbol symbol;



}


