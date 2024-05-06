package org.example;
import classes.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args)  {
        int threadCount = Integer.parseInt(args[0]);
        taskManager manager = new taskManager();
        Collector resultCollector = new Collector();

        Thread[] threads = new Thread[threadCount];
        for(int i = 0; i < threadCount;i++){
            Runnable runnable = new runnableThread(manager, resultCollector);
            Thread th = new Thread(runnable);
            threads[i] = th;
            threads[i].start();
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("Type in numbers that will be checked whether they are prime or not");
        System.out.println("Type 'STOP' to stop execution of a program and see the results");
        while(true){
            String s = scanner.nextLine();
            if("STOP".equals(s)){
                    break;
            }


            int number = Integer.parseInt(s);
            manager.addTask(number);
        }
        manager.stopWorking();


        for(Thread th : threads){
            try {
                th.join();
            }
            catch (InterruptedException e){
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, "Unable to join: thread interrupted", e);
            }
        }




        System.out.println(resultCollector);
    }
}