package ch.hftm.api.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity
public class Home extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String name;
    public String description;
    public Integer postalCode;

    @OneToMany(mappedBy = "home")
    public List<Room> roomList;

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

}
