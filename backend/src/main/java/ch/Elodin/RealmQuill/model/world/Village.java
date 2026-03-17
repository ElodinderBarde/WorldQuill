package ch.Elodin.RealmQuill.model.world;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "village")
public class Village {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="village_ID")
    private int id;

    @Column(name ="village_name", unique = true)
    private String name;
    
    @Column(name="notes")
    private String notes;


}