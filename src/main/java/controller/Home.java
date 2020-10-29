package controller;

import java.util.ArrayList;

public class Home {
    static final int THREADS = 5;
    public static void main(String[] args) {
        DatabaseManagement db = new DatabaseManagement();
        db.readFromDatabaseInMainThread();
        ArrayList <DatabaseManagement> mydbs = new ArrayList<>();
        System.out.println("Ahora 5 con hilos");
        DatabaseManagement dbThreads;
        int numberOfRegisters = db.requestNumberOfRegisters();
        int initial, last, total = 0;
        long timeInit = System.currentTimeMillis();
        for (int i = 0; i < THREADS; i++) {
            initial = (numberOfRegisters/THREADS) * (i-1);
            if (i == THREADS) {
                last = numberOfRegisters;
            }
            else {
                last = (numberOfRegisters/THREADS) * i;
            }

            if (i == 1) {
                initial = 1;
            }
            else {
                last = (numberOfRegisters/THREADS) * i;
            }

            dbThreads = new DatabaseManagement(initial,last);
            dbThreads.start();
            mydbs.add(dbThreads);

        }
        System.out.println("Duración: " + (timeInit - System.currentTimeMillis()) + " milisegundos");
       long timeInitSume = System.currentTimeMillis();
        for (DatabaseManagement mydb : mydbs) {
            total+=mydb.getTotal();
        }
        System.out.println("Duración de la suma: " + (timeInitSume - System.currentTimeMillis()) + " milisegundos");
    }
}
