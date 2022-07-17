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
@Table(name = "airwindow_home")
public class Home extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String name;
    public String description;
    public Integer postalCode;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "HOME_ID")
    public List<Room> roomList = new ArrayList<>();

    public Home() {
        // Emtpy constructor for Panache
    }

    public static List<Home> findAllHomes() {
        return findAll().list();
    }

    public static Home findHomeById(Long id) {
        return findById(id);
    }

    public static Boolean deleteHomeById(Long id) {
        return deleteById(id);
    }

    public void addRoom(Room room) {
        roomList.add(room);
    }

}
