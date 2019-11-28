package database.models;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class Client {
    @GeneratedValue
    @Id
    private int id;
    private String name;
    private String secondName;
    @Column(unique = true)
    private String NIP;
    private String postcode1;
    private String postcode2;
    private String city;
    private String houseNumber;
    private String street;
    private String phoneNumber;


    public void Client() {

    }


    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", secondName='" + secondName + '\'' +
                ", NIP='" + NIP + '\'' +
                ", postcode='" + postcode1+ "-"+postcode2 + '\'' +
                ", city='" + city + '\'' +
                ", houseNumber=" + houseNumber +
                ", street='" + street + '\'' +
                '}';
    }
}
