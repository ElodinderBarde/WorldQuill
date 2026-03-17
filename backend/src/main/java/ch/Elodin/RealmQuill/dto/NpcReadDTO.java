package ch.Elodin.RealmQuill.dto;
import ch.Elodin.RealmQuill.model.Clan;
import ch.Elodin.RealmQuill.model.enums.EnumSymbol;
import ch.Elodin.RealmQuill.model.npcinfo.Armor;
import ch.Elodin.RealmQuill.model.npcinfo.Background;
import ch.Elodin.RealmQuill.model.npcinfo.Beardstyle;
import ch.Elodin.RealmQuill.model.npcinfo.Betonung;
import ch.Elodin.RealmQuill.model.npcinfo.Dislikes;
import ch.Elodin.RealmQuill.model.npcinfo.Family;
import ch.Elodin.RealmQuill.model.npcinfo.Firstname;
import ch.Elodin.RealmQuill.model.npcinfo.Flaw;
import ch.Elodin.RealmQuill.model.npcinfo.Gender;
import ch.Elodin.RealmQuill.model.npcinfo.Haircolor;
import ch.Elodin.RealmQuill.model.npcinfo.Hairstyle;
import ch.Elodin.RealmQuill.model.npcinfo.Ideals;
import ch.Elodin.RealmQuill.model.npcinfo.Jackets;
import ch.Elodin.RealmQuill.model.npcinfo.Jewellery;
import ch.Elodin.RealmQuill.model.npcinfo.Lastname;
import ch.Elodin.RealmQuill.model.npcinfo.Level;
import ch.Elodin.RealmQuill.model.npcinfo.Likes;
import ch.Elodin.RealmQuill.model.npcinfo.NpcClass;
import ch.Elodin.RealmQuill.model.npcinfo.OtherDescription;
import ch.Elodin.RealmQuill.model.npcinfo.Personality;
import ch.Elodin.RealmQuill.model.npcinfo.Race;
import ch.Elodin.RealmQuill.model.npcinfo.Stats;
import ch.Elodin.RealmQuill.model.npcinfo.Subclass;
import ch.Elodin.RealmQuill.model.npcinfo.TalkingStyle;
import ch.Elodin.RealmQuill.model.npcinfo.Trousers;
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


