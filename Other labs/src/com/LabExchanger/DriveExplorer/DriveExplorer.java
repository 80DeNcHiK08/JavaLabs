package com.LabExchanger.DriveExplorer;
import com.LabExceptions.DriveExplorer.DriveExplorerException;

import java.io.File;
import java.io.FileReader;
import java.nio.file.Path;
import java.util.Date;
import java.util.concurrent.Exchanger;

public class DriveExplorer {
    private static final String Alphabet = "abcdefghijklmnopqrstuvwxyz'";
    private static final String AlphabetR = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";
    private static final String FORMAT = "%5s";

    private Exchanger<String> exchanger;
    private String derivedMessage = "";

    public DriveExplorer(Exchanger<String> e) {
        this.exchanger = e;
    }

    private boolean CanRead(File file) {
        int i = file.getName().lastIndexOf(".");
        //C:\Users\Denis\OneDrive\Рабочий стол
        return (file.getName().substring(i + 1).equalsIgnoreCase("txt")) ? true : false;
    }

    private void getSymbols(final String line) {
        for (char symbol : line.toCharArray()) {
            System.out.print(String.format(FORMAT, symbol));
        }
    }

    private void getSymbolNumbers(final String line) {
        for (char symbol : line.toCharArray()) {
            int i = Alphabet.lastIndexOf(symbol);
            int j = AlphabetR.lastIndexOf(symbol);
            i+=1;
            j+=1;
            if(Alphabet.contains(Character.toString(symbol))) {
                System.out.print(String.format(FORMAT, i));
                derivedMessage += i + " ";
            } else if(AlphabetR.contains(Character.toString(symbol))) {
                System.out.print(String.format(FORMAT, j));
                derivedMessage += j + " ";
            }
        }
    }

    public void getList(Path path) throws DriveExplorerException {
        if(path == null || path.toString().isEmpty() || path.toString().equalsIgnoreCase("root")) {
            File[] root = File.listRoots();
            for(var r:root) {
                System.out.println(r.toString());
            }
            return;
        }

        File fpath = new File(path.toString());
        if(fpath.exists() && fpath.isDirectory()) {
            String filesresult = "Files: \n";
            System.out.println("Directories: ");
            for(var c:fpath.listFiles()) {
                if(c.isDirectory()) {
                    System.out.println("\t" + c.getName());
                } else {
                    filesresult += "\t";
                    filesresult = filesresult.concat(c.getName());
                    filesresult += "\n";
                }
            }
            System.out.println(filesresult);
        } else {
            throw new DriveExplorerException("[lab1] Cannot get contents of specified directory!");
        }
    }

    public void getTxtContents(Path path) throws DriveExplorerException{
        File fpath = new File(path.toString());
        if(!path.toString().isEmpty() && !path.toString().equalsIgnoreCase("root") && fpath.exists() && fpath.isFile()) {
            if(CanRead(fpath)) {
                System.out.println("Content of '" + fpath.getName() + "':");

                try(FileReader reader = new FileReader(fpath.getPath())) {
                    int c; String str = "";
                    while((c = reader.read()) != -1)
                    {
                        str += (char)c;
                    }
                    str = str.toLowerCase();
                    final String[] content = str.split("\\n");
                    for(final String contentline : content)
                    {
                        System.out.println("Letter:");
                        getSymbols(contentline);
                        System.out.println("\nIt's number in alphabet: ");
                        getSymbolNumbers(contentline);
                        System.out.println();
                    }
                    try{
                        exchanger.exchange(derivedMessage);
                    }
                    catch(InterruptedException ex){
                        System.out.println(ex.getMessage());
                    }
                }
                catch (Exception e) {
                    System.err.println(String.format("[lab1] There was an error: %s", e.getMessage()));
                }
            } else {
                throw new DriveExplorerException("[lab1] Can't open! Specified file has different from 'txt' extension!");
            }
        } else {
            throw new DriveExplorerException("[lab1] Cannot get specified file!");
        }
    }

    public void getFileInfo(Path path) throws DriveExplorerException{
        File fpath = new File(path.toString());
        if(!path.toString().isEmpty() && !path.toString().equalsIgnoreCase("root") && fpath.exists() && fpath.isFile()) {
            System.out.println(fpath.getName().toString() + " info:\n");
            System.out.println("Full path: '" + fpath.getPath() +
                    "';\nSize: " + Long.toString(fpath.length()) +
                    " bytes;\n" + "Last modified: " + new Date(fpath.lastModified()).toString() +
                    ";\nCan read: " + ((fpath.canRead()) ? "yes" : "no") +
                    ";\nCan write: " + ((fpath.canWrite()) ? "yes" : "no")  + ";\n");
        } else {
            throw new DriveExplorerException("[lab1] Cannot get info of specified file!");
        }
    }

}