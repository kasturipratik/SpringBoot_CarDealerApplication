package com.example.cardealer;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String vehicleCatagory;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getVehicleCatagory() {
        return vehicleCatagory;
    }

    public void setVehicleCatagory(String vehicleCatagory) {
        this.vehicleCatagory = vehicleCatagory;
    }

    @OneToMany(mappedBy = "category",orphanRemoval = true,
            cascade = CascadeType.REMOVE)
    @Fetch(value = FetchMode.SUBSELECT)
    private Set<CarDealer> cars;

    public Set<CarDealer> getCars() {
        return cars;
    }

    public void setCars(Set<CarDealer> cars) {
        this.cars = cars;
    }


}
