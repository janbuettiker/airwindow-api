package ch.hftm.api.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import ch.hftm.api.models.enums.StateType;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity
@Table(name = "airwindow_window")
public class Window extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String name;
    public String description;

    public StateType currentState;
    public StateType desiredState;

    public Boolean weatherAware;

    public Window() {
        // Emtpy constructor for Panache
        weatherAware = false;
    }

    public static List<Window> findAllWindows() {
        return findAll().list();
    }

    public static Window findWindowById(Long id) {
        return findById(id);
    }

    public static Boolean deleteWindowById(Long id) {
        return deleteById(id);
    }
}
