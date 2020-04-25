package com.LabExchanger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Exchanger;

public class SAApplication implements Runnable{
    private Exchanger<String> exchanger;
    private String recievedMesage;
    public final String name = "Sort Array [lab3]";

    public SAApplication(Exchanger<String> e) {
        this.exchanger = e;
        try{
            recievedMesage = exchanger.exchange(new String());
        }
        catch(InterruptedException ex){
            System.out.println(ex.getMessage());
        }
        new Thread(this).start();
    }

    public void run() {
        System.out.println("Starting " + name);
        try {
            String[] line = recievedMesage.split(" ");
            ArrayList<Integer> arrayList = new ArrayList<Integer>();
            for(int i = 0; i < line.length; i++) {
                arrayList.add(Integer.parseInt(line[i]));
            }
            System.out.println("[lab3] Input array: " + arrayList);
            Collections.sort(arrayList, Collections.reverseOrder());
            System.out.println("[lab3] Result: " + arrayList);
        }
        catch (Exception e) {
            System.err.println(String.format("[lab3] There was an error: %s", e.getMessage()));
        }
    }
}
