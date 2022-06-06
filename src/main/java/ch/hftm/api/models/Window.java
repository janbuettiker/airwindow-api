package ch.hftm.api.models;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import ch.hftm.api.models.enums.StateType;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity
public class Window extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String name;
    public String description;

    public StateType currentState;
    public StateType desiredState;

    @JsonbTransient
    @ManyToOne
    public Room room;

    public Window() {
        // Emtpy constructor for Panache
    }
}
