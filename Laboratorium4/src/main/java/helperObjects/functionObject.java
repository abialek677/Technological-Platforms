package helperObjects;

import org.example.Mage;
import org.example.Tower;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class functionObject {
    private EntityManager eManager;

    public functionObject(EntityManager emg) {
        eManager = emg;
    }


    public void printMenu() {
        System.out.println();
        System.out.println("Choose an option:");
        System.out.println("1. Add mage");
        System.out.println("2. Add tower");
        System.out.println("3. Remove mage");
        System.out.println("4. Remove tower");
        System.out.println("5. Show all towers");
        System.out.println("6. Show all mages");
        System.out.println("7. Show mages with their towers");
        System.out.println("8. Assign towerless mage");
        System.out.println("9. Queries");
        System.out.println("10. Exit");
    }


    public void assignTowerlessMage() {
        System.out.println();
        System.out.println("ASSIGN TOWERLESS MAGE");
        System.out.println();
        Query q = eManager.createQuery(
                "SELECT m FROM Mage m WHERE m.tower = null", Mage.class
        );
        List<Mage> mages = q.getResultList();
        if (!mages.isEmpty()) {
            System.out.println("Unassigned mages: ");
            for (Mage m : mages) {
                System.out.println(m);
            }
            System.out.println();
            System.out.println("Choose a mage:");
            Scanner s = new Scanner(System.in);
            String mageName = s.nextLine();
            Query q2 = eManager.createQuery(
                    "SELECT m FROM Mage m WHERE m.name = :mName", Mage.class
            );
            q2.setParameter("mName", mageName);
            Mage mage = (Mage) q2.getSingleResult();
            showTowers();
            System.out.println();
            System.out.println("Choose a tower: ");
            String towerName = s.nextLine();
            Query q3 = eManager.createQuery(
                    "SELECT t FROM Tower t WHERE t.name = :tName", Tower.class
            );
            q3.setParameter("tName", towerName);
            Tower tower = (Tower) q3.getSingleResult();
            mage.setTower(tower);

            EntityTransaction transaction = eManager.getTransaction();
            transaction.begin();
            transaction.commit();
        } else {
            System.out.println("There are no towerless mages");
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }


    public void showTowers() {
        System.out.println();
        System.out.println("Towers:");
        System.out.println();
        Query q = eManager.createQuery(
                "SELECT t FROM Tower t", Tower.class
        );
        List<Tower> queryResult = q.getResultList();
        for (Tower t : queryResult) {
            System.out.println(t);
        }
    }

    private List<Tower> getAllTowers(){
        Query q = eManager.createQuery(
                "SELECT t FROM Tower t", Tower.class
        );
        List<Tower> queryResult = q.getResultList();
        return queryResult;
    }

    public void showTowersMages() {
        System.out.println();
        System.out.println("Towers with their mages:");
        Query q = eManager.createQuery(
                "SELECT t FROM Tower t", Tower.class
        );
        List<Tower> queryResult = q.getResultList();
        for (Tower t : queryResult) {
            System.out.println(t);
            for (Mage m : t.getMages()) {
                System.out.println("\t" + m);
            }
        }
    }

    public void showMages() {
        System.out.println();
        System.out.println("Mages:");
        Query q = eManager.createQuery(
                "SELECT m FROM Mage m", Mage.class
        );
        List<Mage> queryResult = q.getResultList();
        for (Mage m : queryResult) {
            System.out.println(m);
        }
    }

    public void addMage() {
        if(getAllTowers().isEmpty()){
            System.out.println();
            System.out.println("ADD TOWER BEFORE ADDING MAGE");
            System.out.println();
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return;
        }
        System.out.println();
        System.out.println("ADD MAGE");
        System.out.println();
        System.out.println("Type 'q' if you want to go back");
        System.out.println();
        Scanner s = new Scanner(System.in);
        System.out.println("Name:");
        String name = s.nextLine();
        if (name.toLowerCase().equals("q")) {
            return;
        }
        System.out.println("Level:");
        Integer level = s.nextInt();
        s.nextLine();
        showTowers();
        System.out.println("Tower:");
        String towerName = s.nextLine();

        Query q = eManager.createQuery("SELECT t FROM Tower t WHERE t.name = :tName", Tower.class);
        q.setParameter("tName", towerName);
        Tower t = (Tower) q.getSingleResult();

        EntityTransaction transaction = eManager.getTransaction();
        Mage m = new Mage(name, level, t);
        transaction.begin();
        eManager.persist(m);
        transaction.commit();
    }

    public void addTower() {
        System.out.println();
        System.out.println("ADD TOWER");
        System.out.println();
        System.out.println("Type 'q' if you want to go back");
        System.out.println();
        Scanner s = new Scanner(System.in);
        System.out.println("Name:");
        String name = s.nextLine();
        if (name.toLowerCase().equals("q")) {
            return;
        }
        System.out.println("Height:");
        Integer height = s.nextInt();
        s.nextLine();
        EntityTransaction transaction = eManager.getTransaction();
        Tower t = new Tower(name, height);
        transaction.begin();
        eManager.persist(t);
        transaction.commit();
    }

    public void removeMage() {
        System.out.println();
        System.out.println("REMOVE MAGE");
        System.out.println();
        System.out.println("Type 'q' if you want to go back");
        System.out.println();
        Scanner s = new Scanner(System.in);
        System.out.println("Name:");
        String name = s.nextLine();
        if (name.toLowerCase().equals("q")) {
            return;
        }
        Query q = eManager.createQuery("SELECT m FROM Mage m WHERE m.name = :mNam", Mage.class);
        q.setParameter("mNam", name);
        Mage m = (Mage) q.getSingleResult();

        if (m != null) {
            EntityTransaction transaction = eManager.getTransaction();
            transaction.begin();
            eManager.remove(m);
            transaction.commit();
        } else {
            System.out.println("Mage with name " + name + " could not be found");
        }
    }

    public void removeTower() {
        System.out.println();
        System.out.println("REMOVE Tower");
        System.out.println();
        System.out.println("Type 'q' if you want to go back");
        System.out.println();
        Scanner s = new Scanner(System.in);
        System.out.println("Name:");
        String name = s.nextLine();
        if (name.toLowerCase().equals("q")) {
            return;
        }
        Query q = eManager.createQuery("SELECT t FROM Tower t WHERE t.name = :tName", Tower.class);
        q.setParameter("tName", name);
        Tower t = (Tower) q.getSingleResult();

        Query mageQ = eManager.createQuery("SELECT m FROM Mage m WHERE m.tower = :tow", Mage.class);
        mageQ.setParameter("tow", t);
        List<Mage> mages = mageQ.getResultList();

        EntityTransaction transaction = eManager.getTransaction();

        transaction.begin();

        for (Mage m : mages) {
            m.setTower(null);
            eManager.merge(m);
        }
        eManager.remove(t);

        transaction.commit();
    }


    private void showQueryMenu() {
        System.out.println();
        System.out.println("1. Show all mages with level higher than 'x'");
        System.out.println("2. Show all towers shorter than 'x'");
        System.out.println("3. Show all mages with level lower than 'x' from tower 't'");
        System.out.println("4. Get the strongest mage");
        System.out.println("5. Get the weakest mage");
        System.out.println("6. Show all non-homeless mages");
        System.out.println("7. Go back to regular menu");
        System.out.println();
    }

    public void queryMenu() {
        Scanner s = new Scanner(System.in);
        boolean running = true;
        Integer x;
        while (running) {
            showQueryMenu();
            Integer option = s.nextInt();
            s.nextLine();
            switch (option) {
                case 1:
                    System.out.println("Type in x:");
                    x = s.nextInt();
                    s.nextLine();
                    Query q1 = eManager.createQuery(
                            "SELECT m FROM Mage m WHERE m.level > :lv", Mage.class
                    );
                    q1.setParameter("lv", x);
                    List<Mage> mages = q1.getResultList();
                    for(Mage m : mages){
                        System.out.println(m);
                    }
                    break;
                case 2:
                    System.out.println("Type in x:");
                    x = s.nextInt();
                    s.nextLine();
                    Query q2 = eManager.createQuery(
                            "SELECT t FROM Tower t WHERE  t.height < :hg", Tower.class
                    );
                    q2.setParameter("hg", x);
                    List<Tower> towers = q2.getResultList();
                    for(Tower t : towers){
                        System.out.println(t);
                    }
                    break;
                case 3:
                    showTowers();
                    System.out.println();
                    System.out.println("Type in tower name:");
                    String towerName = s.nextLine();
                    System.out.println("Type in x:");
                    x = s.nextInt();
                    s.nextLine();
                    Query q3_help = eManager.createQuery(
                            "SELECT t FROM Tower t WHERE t.name = :tNam", Tower.class
                    );
                    q3_help.setParameter("tNam", towerName);
                    Tower t = (Tower) q3_help.getSingleResult();
                    Query q3 = eManager.createQuery(
                            "SELECT m FROM Mage m WHERE m.tower = :tower AND m.level < :lv", Mage.class
                    );
                    q3.setParameter("tower", t);
                    q3.setParameter("lv", x);
                    List<Mage> mages3 = q3.getResultList();
                    for(Mage m : mages3){
                        System.out.println(m);
                    }
                    break;
                case 4:
                    Query q4 = eManager.createQuery(
                            "SELECT m FROM Mage m ORDER BY m.level DESC", Mage.class
                    );
                    q4.setMaxResults(1);
                    Mage bestMage = (Mage) q4.getSingleResult();
                    System.out.println(bestMage);
                    break;
                case 5:
                    Query q5 = eManager.createQuery(
                            "SELECT m FROM Mage m ORDER BY m.level ASC", Mage.class
                    );
                    q5.setMaxResults(1);
                    Mage worstMage = (Mage) q5.getSingleResult();
                    System.out.println(worstMage);
                    break;
                case 6:
                    Query q6 = eManager.createQuery(
                            "SELECT m FROM Mage m WHERE m.tower IS NOT NULL", Mage.class
                    );
                    List<Mage> nonHomeless = q6.getResultList();
                    for(Mage m : nonHomeless){
                        System.out.println(m);
                    }
                    break;
                case 7:
                    running = false;
                    break;

            }
        }
    }
}
