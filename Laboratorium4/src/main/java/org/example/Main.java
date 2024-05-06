package org.example;

import javax.persistence.*;
import java.util.Scanner;

import helperObjects.functionObject;


public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("my_PU");
        EntityManager eManager = emFactory.createEntityManager();

        functionObject funcObj = new functionObject(eManager);

        Scanner s = new Scanner(System.in);

        boolean running = true;
        while (running) {
            funcObj.printMenu();
            Integer option = s.nextInt();
            s.nextLine();
            switch (option) {
                case 1:
                    funcObj.addMage();
                    break;
                case 2:
                    funcObj.addTower();
                    break;
                case 3:
                    funcObj.removeMage();
                    break;
                case 4:
                    funcObj.removeTower();
                    break;
                case 5:
                    funcObj.showTowers();
                    break;
                case 6:
                    funcObj.showMages();
                    break;
                case 7:
                    funcObj.showTowersMages();
                    break;
                case 8:
                    funcObj.assignTowerlessMage();
                    break;
                case 9:
                    funcObj.queryMenu();
                    break;
                case 10:
                    running = false;
                    break;
                default:
                    System.out.println("Wrong option");
                    break;
            }
        }
        s.close();
        eManager.close();
        emFactory.close();
    }
}
