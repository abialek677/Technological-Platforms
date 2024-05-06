package DTO;

public class Mage {
    private String name;
    private int level;

    public Mage(String name, int level){
        this.name = name;
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public String toString(){
        String s = name + "| level: " + level;
        return s;
    }

    @Override
    public boolean equals(Object o){
        if(this.getClass() != o.getClass())
            return false;

        Mage m = (Mage) o;

        return this.level == m.level && this.name == m.name;
    }

}
