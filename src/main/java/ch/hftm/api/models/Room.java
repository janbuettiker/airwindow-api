package ch.hftm.api.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity
@Table(name = "airwindow_room")
public class Room extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String name;
    public String description;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "ROOM_ID")
    public List<Window> windowList = new ArrayList<>();

    public Room() {
        // Emtpy constructor for Panache
    }

    public void addWindow(Window window) {
        windowList.add(window);
    }

    public void removeWindow(Window window) {
        windowList.remove(window);
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
