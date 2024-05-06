package Repository;

import DTO.Mage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class MageRepository {
    private Collection<Mage> collection;

    public MageRepository() {
        this.collection = new ArrayList<>();
    }

    public Collection<Mage> getCollection() {
        return collection;
    }

    public void setCollection(Collection<Mage> collection) {
        this.collection = collection;
    }

    public MageRepository(Collection<Mage> c){
        this.collection = c;
    }

    public Optional<Mage> find(String name){
        return collection.stream().filter(mage -> mage.getName() == name).findFirst();
    }

    public void delete(String name){
        boolean flag = collection.removeIf(mage -> mage.getName() == name);
        if(!flag){
            throw new IllegalArgumentException("Mage: '" + name +"' does not exist");
        }
    }

    public void save(Mage m){
        if(find(m.getName()).isPresent()){
            throw new IllegalArgumentException("Mage: '" + m +"' already exists");
        }
        else{
            collection.add(m);
        }
    }
}
