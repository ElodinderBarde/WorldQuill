package ch.Elodin.RealmQuill.dto;

import ch.Elodin.RealmQuill.model.enums.EnumSymbol;
import ch.Elodin.RealmQuill.model.npcinfo.Family;
import ch.Elodin.RealmQuill.model.npcinfo.Level;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Data
@Getter
@Setter

public class NpcWriteDTO {

    private Integer npc_ID;
    private Integer firstnameId;
    private Integer lastnameId;
    private Integer shopRelationsId;
    private String clan_position;
    private Integer npc_age;
    private Integer familyId;
    private Integer raceId;
    private Integer genderId;
    private Integer clanId;
    private Integer betonungId;
    private Integer classId;
    private Integer subclassId;
    private Integer likesId;
    private Integer dislikesId;
    private Integer personalityId;
    private Integer flawId;
    private Integer idealsId;
    private Integer jacketsId;
    private Integer kleidungQualiId;
    private Integer jewelleryId;
    private Integer otherDescriptionId;
    private Integer backgroundId;
    private Integer pictureId;
    private Integer hairstyleId;
    private Integer talkingstyleId;
    private Integer trousersId;
    private Integer levelId;
    private Integer armorId;
    private Integer haircolorId;
    private Integer beardstyleId;
    private String notes;



    private Integer level;
    private Integer strength;
    private Integer dexterity;
    private Integer constitution;
    private Integer intelligence;
    private Integer wisdom;
    private Integer charisma;

    private Integer shopRelation;
    private Integer shopEmployeeRole;
    private Integer shopCustomerRole;
    private Integer shopId;
    private Integer family;
    private EnumSymbol symbol;
}


