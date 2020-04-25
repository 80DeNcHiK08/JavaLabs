package com.LabSemaphore;

import com.LabExceptions.PatternMatcher.PattenMatcher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.Semaphore;

public class PMApplication implements Runnable {
    private static final PattenMatcher matcher = new PattenMatcher();
    private Semaphore sem;
    public final String name = "Pattern Matcher [lab2]";

    PMApplication(Semaphore s) {
        this.sem = s;
        new Thread(this).start();
    }

    private String input() throws IOException {
        var buff = new BufferedReader(new InputStreamReader(System.in));
        return buff.readLine();
    }

    private void print(boolean isMatch) {
        System.out.println("[lab2] string " + (isMatch ? "matches" : "not matches") + " the pattern!");
    }

    public void run() {
        System.out.println("Starting " + name);
        var finished = false;
        try {
            System.out.println(name + " is waiting for permit.");
            sem.acquire();
            System.out.println(name + " gets a permit.");

            Thread.sleep(10);

            while (!finished) {
                System.out.println("[lab2] Type any string to check if it match to hex color pattern (or Quit to exit):");
                var input = input();
                if(input.equalsIgnoreCase("quit")) {
                    finished = true;
                    continue;
                }
                var matches = matcher.isValid(input);
                print(matches);
            }
        }
        catch (Exception e) {
            System.err.println(String.format("[lab2] There was an error: %s", e.getMessage()));
        }

        System.out.println(name + " releases the permit.");
        sem.release();
    }
}
