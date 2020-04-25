package com.LabSemaphore;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.Semaphore;

public class SAApplication implements Runnable{
    private Semaphore sem;
    public final String name = "Sort Array [lab3]";

    public SAApplication(Semaphore s) {
        this.sem = s;

        new Thread(this).start();
    }

    private String input() throws IOException {
        var buff = new BufferedReader(new InputStreamReader(System.in));
        return buff.readLine();
    }

    public void run() {
        var finished = false;
        System.out.println("Starting " + name);
        try {
            System.out.println(name + " is waiting for a permit.");
            sem.acquire();
            System.out.println(name + " gets a permit.");

            Thread.sleep(5);

            while (!finished) {

                System.out.println("[lab3] Enter numbers between -infty and + infty (or quit to exit app):");
                String inputline = input();
                if (inputline.equalsIgnoreCase("quit")) {
                    finished = true;
                    continue;
                }
                String[] line = inputline.split(" ");
                ArrayList<Integer> arrayList = new ArrayList<Integer>();
                for(int i = 0; i < line.length; i++) {
                    arrayList.add(Integer.parseInt(line[i]));
                }
                System.out.println("[lab3] Input array: " + arrayList);
                Collections.sort(arrayList, Collections.reverseOrder());
                System.out.println("[lab3] Result: " + arrayList);
            }
        }
        catch (Exception e) {
            System.err.println(String.format("[lab3] There was an error: %s", e.getMessage()));
        }

        System.out.println(name + " releases the permit.");
        sem.release();
    }
}
