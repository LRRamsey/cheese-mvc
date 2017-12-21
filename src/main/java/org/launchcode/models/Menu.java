package org.launchcode.models;


import org.launchcode.models.Cheese;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Menu {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @Size(min=3, max=128)
    private String name;

    @ManyToMany
    private List<Cheese> cheeses = new ArrayList<>();

    public void addItem(Cheese item) {cheeses.add(item);}

    public Menu() {
    }
    public Menu(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Cheese> getCheeses() {
        return cheeses;
    }

    public int getId() {
        return id;
    }


}
