package ch.Elodin.RealmQuill.model;

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
import ch.Elodin.RealmQuill.model.npcinfo.KleidungQuali;
import ch.Elodin.RealmQuill.model.npcinfo.Lastname;
import ch.Elodin.RealmQuill.model.npcinfo.Level;
import ch.Elodin.RealmQuill.model.npcinfo.Likes;
import ch.Elodin.RealmQuill.model.npcinfo.NpcClass;
import ch.Elodin.RealmQuill.model.npcinfo.OtherDescription;
import ch.Elodin.RealmQuill.model.npcinfo.Personality;
import ch.Elodin.RealmQuill.model.npcinfo.Picture;
import ch.Elodin.RealmQuill.model.npcinfo.Race;
import ch.Elodin.RealmQuill.model.npcinfo.Subclass;
import ch.Elodin.RealmQuill.model.npcinfo.TalkingStyle;
import ch.Elodin.RealmQuill.model.npcinfo.Trousers;
import ch.Elodin.RealmQuill.model.shop.ShopRelations;
import ch.Elodin.RealmQuill.model.world.Campaign;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;



@Setter
@Getter
@Entity
@Table(name = "npc")
public class Npc {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int npc_ID;


	@ManyToOne
	@JoinColumn(name = "npc_firstname_ID")
	private Firstname firstname;

	@ManyToOne
	@JoinColumn(name = "npc_lastname_ID")
	private Lastname lastname;

	@ManyToOne
	@JoinColumn(name = "shop_relations_ID")
	private ShopRelations shop_relations_ID;


	@Column(name = "clan_position")
	private String clan_position;


	@Column(name = "npc_age")
	private Integer npc_age;


	@ManyToOne
	@JoinColumn(name = "npc_family_ID")
	private Family npc_family_ID;

	// VerknÃ¼pfung zu Race
	@ManyToOne
	@JoinColumn(name = "race_ID")
	private Race race;

	// VerknÃ¼pfung zu Gender
	@ManyToOne
	@JoinColumn(name = "npc_gender_ID")
	private Gender gender;

	// VerknÃ¼pfung zu Clan
	@ManyToOne
	@JoinColumn(name = "clan_ID")
	private Clan clan;

	// VerknÃ¼pfung zu Betonung
	@ManyToOne
	@JoinColumn(name = "npc_betonung_ID")
	private Betonung betonung;

	// VerknÃ¼pfung zu Klasse
	@ManyToOne
	@JoinColumn(name = "npc_class_ID")
	private NpcClass npcClass;

	// VerknÃ¼pfung zu Subklasse
	@ManyToOne
	@JoinColumn(name = "npc_subclass_ID")
	private Subclass subclass;

	// VerknÃ¼pfung zu Likes
	@ManyToOne
	@JoinColumn(name = "npc_likes_ID")
	private Likes likes;

	// VerknÃ¼pfung zu Dislikes
	@ManyToOne
	@JoinColumn(name = "npc_dislikes_ID")
	private Dislikes dislikes;

	// VerknÃ¼pfung zu Personality
	@ManyToOne
	@JoinColumn(name = "npc_personality_ID")
	private Personality personality;

	// VerknÃ¼pfung zu Flaw
	@ManyToOne
	@JoinColumn(name = "npc_flaw_ID")
	private Flaw flaw;

	@ManyToOne
	@JoinColumn(name = "npc_ideals_ID")
	private Ideals ideals;

	@ManyToOne
	@JoinColumn(name = "npc_jackets_ID")
	private Jackets jackets;


	@ManyToOne
	@JoinColumn(name = "npc_kleidungsqualitÃ¤t_ID")
	private KleidungQuali KleidungQuali;


	@ManyToOne
	@JoinColumn(name = "npc_jewellery_ID")
	private Jewellery jewellery;

	@ManyToOne
	@JoinColumn(name = "npc_other_description_ID")
	private OtherDescription otherDescription;

	@ManyToOne
	@JoinColumn(name = "npc_background_ID")
	private Background background;

	@Setter
	@ManyToOne
	@JoinColumn(name = "npc_picture_ID")
	private Picture picture;


	@ManyToOne
	@JoinColumn(name = "npc_hairstyle_ID")
	private Hairstyle hairstyle;


	@ManyToOne
	@JoinColumn(name = "npc_talkingstyle_ID")
	private TalkingStyle talkingstyle;


	@ManyToOne
	@JoinColumn(name = "npc_trousers_ID")
	private Trousers trousers;

	@ManyToOne
	@JoinColumn(name = "npc_level_ID")
	private Level level;

	@Column(name = "notes")
	private String notes;


	@ManyToOne
	@JoinColumn(name = "npc_armorclass_ID")
	private Armor armor_ID;

	@Enumerated(EnumType.STRING)
	@Column(name = "symbol")
	private EnumSymbol symbol;

	@ManyToOne
	@JoinColumn(name = "npc_haircolor_ID")
	private Haircolor haircolor;

	@ManyToOne
	@JoinColumn(name = "npc_beardstyle_ID")
	private Beardstyle beardstyle;

	@ManyToOne
	@JoinColumn(name = "campaign_ID")
	private Campaign campaign;

}
	
