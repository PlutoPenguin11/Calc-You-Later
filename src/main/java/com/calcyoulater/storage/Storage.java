package com.calcyoulater.storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

//TODO: FIX FUCKING MEMORY LEAK HOLY SHIT THIS IS BAD
public class Storage {
    private final static String PROGRAM_NAME = "CalcYouLater";
    private final static String HISTORY_FILE = "history.ser";
    private static ArrayList<Equation> equations = new ArrayList<>();
    private static Storage uniqueInstance;

    private Storage() {
        if (!folderExists()) {
            createAppDataFolder();
            createFile();
        }
    }

    public static Storage instance() {
        if (uniqueInstance == null)
            uniqueInstance = new Storage();
        return uniqueInstance;
    }

    public void addNode(Equation newNode) {
        equations.add(newNode);
    }

    public void clearStorage() {
        equations.clear();
        serialize();
    }

    public void serialize() {
        if (folderExists() && fileExists()) {
            try {
                FileOutputStream fileOut = new FileOutputStream(getFolderPath() + HISTORY_FILE);
                ObjectOutputStream out = new ObjectOutputStream(fileOut);
                out.writeObject(equations);
                out.close();
                fileOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            createAppDataFolder();
            createFile();
            try {
                FileOutputStream fileOut = new FileOutputStream(getFolderPath() + HISTORY_FILE);
                ObjectOutputStream out = new ObjectOutputStream(fileOut);
                out.writeObject(equations);
                out.close();
                fileOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<Equation> deserialize() {
        try {
            FileInputStream filePath = new FileInputStream(getFolderPath() + HISTORY_FILE);
            ObjectInputStream in = new ObjectInputStream(filePath);
            equations = (ArrayList<Equation>) in.readObject();
            filePath.close();
            in.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return equations;
    }

    public static boolean createAppDataFolder() {
        String fileFolder = "";

        String os = System.getProperty("os.name").toUpperCase();
        if (os.contains("WIN")) {
            fileFolder = System.getenv("APPDATA") + "\\" + PROGRAM_NAME;
        } else if (os.contains("MAC")) {
            fileFolder = System.getProperty("user.home") + "/Library/Application " + "Support" + PROGRAM_NAME;
        } else if (os.contains("NUX")) {
            fileFolder = System.getProperty("user.dir") + PROGRAM_NAME;
        }

        File directory = new File(fileFolder);

        if (directory.exists()) {
            return false;
        } else {
            return directory.mkdir();
        }

    }

    private static boolean createFile() {
        String fileFolder = "";

        if (folderExists() && fileExists()) {
            return true;
        } else {
            fileFolder = getFolderPath();
            File historyFile = new File(fileFolder, HISTORY_FILE);

            try {
                return historyFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return false;
        }

    }

    private static boolean fileExists() {
        String fileFolder = getFolderPath();
        File historyFile = new File(fileFolder, HISTORY_FILE);
        return historyFile.exists();
    }

    private static boolean folderExists() {
        String fileFolder = getFolderPath();
        return (new File(fileFolder)).exists();
    }

    private static String getFolderPath() {
        String defaultOS = System.getenv("APPDATA") + "\\" + PROGRAM_NAME;

        String os = System.getProperty("os.name").toUpperCase();

        if (os.contains("WIN")) {
            return System.getenv("APPDATA") + "\\" + PROGRAM_NAME;
        } else if (os.contains("MAC")) {
            return System.getProperty("user.home") + "/Library/Application Support/" + PROGRAM_NAME;
        } else if (os.contains("NUX")) {
            return System.getProperty("user.dir") + "/" + PROGRAM_NAME;
        } else {
            return defaultOS;
        }
    }

}
