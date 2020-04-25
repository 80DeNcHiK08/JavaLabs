package com.LabExceptions.PatternMatcher;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Objects;

public class PMApplication implements Runnable {
    private static final PattenMatcher matcher = new PattenMatcher();
    private final Object sync;

    public PMApplication(Object sync) {
        this.sync = sync;
    }

    public PMApplication() {
        this(new Object());
    }

    private String input() throws IOException {
        var buff = new BufferedReader(new InputStreamReader(System.in));
        return buff.readLine();
    }

    private void print(boolean isMatch) {
        System.out.println("[lab2] string " + (isMatch ? "matches" : "not matches") + " the pattern!");
    }

    public void run() {
        var finished = false;

        while (!finished) {
            try {
                synchronized(sync)
                {
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
        }
    }
}
