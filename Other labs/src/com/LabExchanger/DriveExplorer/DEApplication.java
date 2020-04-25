package com.LabExchanger.DriveExplorer;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Exchanger;

public class DEApplication implements Runnable {
    private final DriveExplorer explorer;
    private Exchanger<String> exchanger;
    public final String name = "Drive Explorer [lab1]";

    private final String commands = "[lab1] Avaliable commands: LIST|INFO|OPENTXT|DIVE|UP|QUIT|HELP";
    private final String message = "[lab1] Enter a command:";
    private Path currentPath = Paths.get("root");

    public DEApplication(Exchanger<String> e) {
        this.exchanger = e;
        explorer = new DriveExplorer(e);
        new Thread(this).start();
    }

    private String input() throws IOException {
        var buff = new BufferedReader(new InputStreamReader(System.in));
        return buff.readLine();
    }

    public void run() {
        System.out.println("Starting " + name);
        var finished = false;
        try {
            Thread.sleep(0);

            while (!finished) {
                System.out.println("[lab1] Current path is: " + currentPath.toString());
                System.out.println(message);
                String input = input();
                if(input.isEmpty()) {
                    System.err.println("[lab1] Command you entered, doesn't exist!");
                } else if(input.equalsIgnoreCase("help")) {
                    System.out.println(commands);
                } else {
                    switch (input.toLowerCase()) {
                        case "list":
                            explorer.getList(currentPath);
                            break;
                        case "info":
                            System.out.println("[lab1] Choose file from this folder to get it's info: ");
                            Path filepath = Paths.get(currentPath.toString(), input());
                            explorer.getFileInfo(filepath);
                            break;
                        case "opentxt":
                            System.out.println("[lab1] Choose '.txt' file from this folder to get it's contents: ");
                            Path fileopath = Paths.get(currentPath.toString(), input());
                            explorer.getTxtContents(fileopath);
                            break;
                        case "dive":
                            if(currentPath.toString().equalsIgnoreCase("root") || currentPath.toString().isEmpty()) {
                                System.out.println("[lab1] You're at root, choose one from drives to dive into:");
                                explorer.getList(currentPath);
                                currentPath = Paths.get(input());
                            } else {
                                currentPath = Paths.get(currentPath.toString(), input());
                                if(new File(currentPath.toString()).exists() || new File(currentPath.toString()).isFile()) {
                                    //System.out.println("Now you're at: '" + currentPath.toString() + "';");
                                } else {
                                    System.err.println("[lab1] Directory you specified does not exists or it's a file!");
                                    currentPath = currentPath.getParent();
                                    //System.out.println("You're still at: '" + currentPath.toString() + "';");
                                }
                            }
                            break;
                        case "up":
                            currentPath = currentPath.getParent();
                            if(currentPath == null) {
                                System.out.println("[lab1] You're at root, choose one from drives to dive into:");
                                explorer.getList(currentPath);
                                currentPath = Paths.get(input());
                            }
                            break;
                        case "quit":
                            System.out.println("[lab1] Goodbye!");
                            finished = true;
                            break;
                        default:
                            System.err.println("[lab1] Command you entered, doesn't exist!");
                            break;
                    }
                }
            }
        }
        catch (Exception e) {
            System.err.println(String.format("[lab2] There was an error: %s", e.getMessage()));
        }
    }
}
