package com.calcyoulater.storage;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Storage {
    private final static String HISTORY_FILE_PATH = "src/main/resources/history.ser";
    private ArrayList<Equation> equations = new ArrayList<>();
    private static Storage uniqueInstance;

    private Storage() {
        this.deserialize();
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
            try {
                FileOutputStream fileOut = new FileOutputStream(HISTORY_FILE_PATH);
                ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
                objOut.writeObject(this.equations);
                objOut.close();
                fileOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
    } 

    public void deserialize() {
        try {
            FileInputStream fileIn = new FileInputStream(HISTORY_FILE_PATH);
            ObjectInputStream objIn = new ObjectInputStream(fileIn);
            this.equations = (ArrayList<Equation>) objIn.readObject();
            fileIn.close();
            objIn.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Equation> getEquations(){
        return this.equations;
    }


}
