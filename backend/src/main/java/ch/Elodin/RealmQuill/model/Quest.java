package ch.Elodin.RealmQuill.model;

import ch.Elodin.RealmQuill.model.enums.EnumQuest;
import ch.Elodin.RealmQuill.model.world.Campaign;
import ch.Elodin.RealmQuill.model.world.Location;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.List;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
@Entity
@Table(name = "quest")
public class Quest {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "quest_ID")
	private int questID;

	@Column(name = "questname")
	private String MonsterName;

	@Column(name = "description")
	private String description;

	
	
	
	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private EnumQuest status;

	@Column(name = "gruppe")
	private String group;

	@Column(name = "price_gold")
	private Integer price_gold;

	
	
	//evtl umstellen auf Item price_item, damit items erstellt und gegeben werden kÃ¶nnen
	@Column(name = "price_item")
	private String price_item;

	
	@ManyToOne
	@JoinColumn(name="questlocation")
	private Location questlocation;
	
	@Column(name = "notes")
	private String notes;

	@Column(name = "is_active")
	private boolean active;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "previous_quest_id")
	private Quest previousQuest;

	@JsonManagedReference
	@OneToMany(mappedBy = "previousQuest")
	private List<Quest> nextQuests;

	@ManyToOne
	@JoinColumn(name = "campaign_ID")
	private Campaign campaign;
}