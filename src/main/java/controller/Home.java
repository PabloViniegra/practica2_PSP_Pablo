package controller;

import views.View;

import java.util.ArrayList;

public class Home {
    static final int THREADS = 5;

    public static void main(String[] args) {
        DatabaseManagement db = new DatabaseManagement();
        View.menu();
        db.readFromDatabaseInMainThread();
        ArrayList<DatabaseManagement> mydbs = new ArrayList<>();
        System.out.println("Ahora 5 con hilos");
        DatabaseManagement dbThreads;
        int numberOfRegisters = db.requestNumberOfRegisters();
        int initial, last;
        long timeInit = System.currentTimeMillis();
        for (int i = 0; i <= THREADS; i++) {
            initial = (numberOfRegisters / THREADS) * (i - 1);
            if (i == THREADS) {
                last = numberOfRegisters;
            } else {
                last = (numberOfRegisters / THREADS) * i;
            }

            if (i == 1) {
                initial = 1;
            } else {
                last = (numberOfRegisters / THREADS) * i;
            }

            dbThreads = new DatabaseManagement(initial, last);
            dbThreads.start();
            mydbs.add(dbThreads);
            try {
                dbThreads.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        long timeFinal = System.currentTimeMillis();
        long time = timeFinal - timeInit;
        System.out.println("Duración: " + time + " milisegundos");
        getDurationSume(mydbs);

    }

    public static void getDurationSume(ArrayList<DatabaseManagement> myArray) {
        int total = 0;
        long timeInitSume = System.currentTimeMillis();
        for (DatabaseManagement mydb : myArray) {
            total += mydb.getTotal();
        }
        System.out.println("Suma de sueldos: " + total);
        long timeFinalSume = System.currentTimeMillis();
        long timeSume = timeInitSume - timeFinalSume;
        System.out.println("Duración de la suma: " + timeSume + " milisegundos");
    }
}
