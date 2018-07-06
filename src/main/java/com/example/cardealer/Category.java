package com.example.cardealer;

import javax.persistence.*;
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

    @OneToMany(mappedBy = "category",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    public Set<CarDealer> cars;

    public Set<CarDealer> getCars() {
        return cars;
    }

    public void setCars(Set<CarDealer> cars) {
        this.cars = cars;
    }
}
