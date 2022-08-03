package ch.hftm.api.models;

import javax.persistence.Entity;
import java.time.Instant;
import javax.persistence.Table;

import ch.hftm.api.models.enums.StateType;
import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
@Table(name = "TASKS")
public class Task extends PanacheEntity {

    public Instant createdAt;
    public Long windowId;
    public StateType stateType;
    public String taskType;

    public Task() {
        createdAt = Instant.now();
    }

    public Task(Instant time) {
        this.createdAt = time;
    }

    public Task(Long wId, StateType s, String tt) {
        this();
        this.windowId = wId;
        this.stateType = s;
        this.taskType = tt;
    }
}