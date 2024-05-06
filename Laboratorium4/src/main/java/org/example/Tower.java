package org.example;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Tower {
    @Id
    private String name;
    private int height;

    @OneToMany(mappedBy = "tower")
    private List<Mage> mages;

    // getters and setters

    public Tower(){
        mages = new ArrayList<>();
    }

    public Tower(String name, int height){
        this.name = name;
        this.height = height;
        mages = new ArrayList<>();
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public List<Mage> getMages() {
        return mages;
    }

    public void setMages(List<Mage> mages) {
        this.mages = mages;
    }



    @Override
    public String toString(){
        String s = name + " height: " + height;
        return s;
    }
}
