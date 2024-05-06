package classes;
import java.util.LinkedList;
import java.util.Queue;

public class taskManager {
    private final Queue<Integer> tasks = new LinkedList<>();
    public boolean flag = false;

    public synchronized int takeTask() throws InterruptedException {
        while(tasks.isEmpty()){
            if(flag){
                return -1;
            }
            wait();
        }

        return tasks.remove();
    }

    public synchronized void addTask(int num){
        tasks.add(num);
        notifyAll();
    }

    public synchronized void stopWorking(){
        flag = true;
        notifyAll();
    }

    public boolean isEmpty(){
        return tasks.isEmpty();
    }
}
