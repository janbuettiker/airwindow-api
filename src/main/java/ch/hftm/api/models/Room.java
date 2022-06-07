package ch.hftm.api.models;

import java.util.List;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity
public class Room extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String name;
    public String description;

    @JsonbTransient
    @ManyToOne
    public Home home;

    @OneToMany(mappedBy = "room")
    public List<Window> windowList;

    public Room() {
        // Emtpy constructor for Panache
    }

    public static List<Room> findAllRooms() {
        return findAll().list();
    }

    public static Room findRoomById(Long id) {
        return findById(id);
    }

    public static Boolean deleteRoomById(Long id) {
        return deleteById(id);
    }
}
