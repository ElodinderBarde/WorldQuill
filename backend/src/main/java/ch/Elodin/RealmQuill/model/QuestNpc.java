package ch.Elodin.RealmQuill.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
@Entity
@Table(name = "questnpc")
public class QuestNpc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quest_npc_id") // exakt wie in der DB!
    private int questNpcId;

    @ManyToOne
    @JoinColumn(name = "npc_id", nullable = false)
    private Npc npc;

    @ManyToOne
    @JoinColumn(name = "quest_id", nullable = false)
    private Quest quest;

    @Column(name = "function")
    private String function;
}
