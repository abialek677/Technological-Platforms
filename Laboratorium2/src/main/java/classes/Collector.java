package classes;

import java.util.ArrayList;
import java.util.List;

public class Collector {
    private final List<Integer> collection = new ArrayList<>();

    public synchronized void addResult(int result){
        collection.add(result);
    }

    public synchronized  List<Integer> getCollection(){
        return collection;
    }

    @Override
    public String toString(){
        String s = "";
        for(Integer i : collection){
            s+= i.toString() + " ";
        }
        return s;
    }
}
