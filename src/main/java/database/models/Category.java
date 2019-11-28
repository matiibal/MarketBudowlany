package database.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class Category {

    @Id
    @GeneratedValue
    private int id;
    @Column(unique = true)
    private String name;


}
