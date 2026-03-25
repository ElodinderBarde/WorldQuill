package ch.Elodin.RealmQuill.model;
import ch.Elodin.RealmQuill.model.Family.FamilyRelation;
import ch.Elodin.RealmQuill.model.enums.EnumSymbol;
import ch.Elodin.RealmQuill.model.npcinfo.*;
import ch.Elodin.RealmQuill.model.shop.ShopRelations;
import ch.Elodin.RealmQuill.model.world.Campaign;
import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter @NoArgsConstructor
@Entity @Table(name = "npc")
public class Npc {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int npc_ID;

    @ManyToOne @JoinColumn(name = "first_name_id")
    private Firstname firstname;
    @ManyToOne @JoinColumn(name = "last_name_id")
    private Lastname lastname;
    @ManyToOne @JoinColumn(name = "shop_relations_id")
    private ShopRelations shop_relations_ID;
    @Column(name = "clan_position")
    private String clan_position;
    @Column(name = "npc_age")
    private Integer npc_age;
    @ManyToOne @JoinColumn(name = "family_group_id")
    private Family npc_family_ID;
    @ManyToOne @JoinColumn(name = "race_ID")
    private Race race;
    @ManyToOne @JoinColumn(name = "gender_id")
    private Gender gender;
    @ManyToOne @JoinColumn(name = "clan_id")
    private Clan clan;
    @ManyToOne @JoinColumn(name = "accent_id")
    private Betonung betonung;
    @ManyToOne @JoinColumn(name = "class_id")
    private NpcClass npcClass;
    @ManyToOne @JoinColumn(name = "subclass_id")
    private Subclass subclass;
    @ManyToOne @JoinColumn(name = "like_id")
    private Likes likes;
    @ManyToOne @JoinColumn(name = "dislike_id")
    private Dislikes dislikes;
    @ManyToOne @JoinColumn(name = "personality_id")
    private Personality personality;
    @ManyToOne @JoinColumn(name = "flaw_id")
    private Flaw flaw;
    @ManyToOne @JoinColumn(name = "ideal_id")
    private Ideals ideals;
    @ManyToOne @JoinColumn(name = "top_id")
    private Jackets jackets;
    @ManyToOne @JoinColumn(name = "clothing_quality_id")
    private KleidungQuali KleidungQuali;
    @ManyToOne @JoinColumn(name = "jewellery_id")
    private Jewellery jewellery;
    @ManyToOne @JoinColumn(name = "description_id")
    private OtherDescription otherDescription;
    @ManyToOne @JoinColumn(name = "background_id")
    private Background background;
    @ManyToOne @JoinColumn(name = "picture_id")
    private Picture picture;
    @ManyToOne @JoinColumn(name = "hair_style_id")
    private Hairstyle hairstyle;
    @ManyToOne @JoinColumn(name = "talking_style_id")
    private TalkingStyle talkingstyle;
    @ManyToOne @JoinColumn(name = "bottom_id")
    private Trousers trousers;
    @ManyToOne @JoinColumn(name = "level_id")
    private Level level;
    @Column(name = "notes")
    private String notes;
    @ManyToOne @JoinColumn(name = "armor_id")
    private Armor armor_ID;
    @Enumerated(EnumType.STRING)
    @Column(name = "symbol")
    private EnumSymbol symbol;
    @ManyToOne @JoinColumn(name = "hair_color_id")
    private Haircolor haircolor;
    @ManyToOne @JoinColumn(name = "beard_style_id")
    private Beardstyle beardstyle;
    @ManyToOne @JoinColumn(name = "campaign_id")
    private Campaign campaign;
    @Column(name = "worldnote_id")
    private Long worldnoteId;
    @OneToMany(mappedBy = "sourceNpc", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FamilyRelation> familyRelations = new ArrayList<>();
}
