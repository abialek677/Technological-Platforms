package Controller;

import DTO.Mage;
import Repository.MageRepository;

import java.util.Optional;

public class MageController {
    private MageRepository repository;

    public MageController(MageRepository repo){
        this.repository = repo;
    }

    public String find(String name){
        Optional<Mage> optMage = repository.find(name);

        if(optMage.isPresent()){
            return optMage.toString();
        }
        else{
            return "not found";
        }
    }

    public String delete(String name){
        try{
            repository.delete(name);
            return "done";
        } catch(IllegalArgumentException e){
            return "not found";
        }
    }

    public String save(String name, int level){
        try{
            Mage m = new Mage(name, level);
            repository.save(m);
            return "done";
        } catch(IllegalArgumentException e){
            return "bad request";
        }
    }
}
