package com.calcyoulater.storage;

import com.calcyoulater.storage.Storage;

public class History {

    private static Equation current;
    private static Equation tail;
    private static History uniqueInstance;

    // Uses singleton design pattern
    private History() {
        tail = new Equation(null);
    }

    public static History instance() {
        if (uniqueInstance == null)
            uniqueInstance = new History();
        return uniqueInstance;
    }

    public Equation getPreviousEquation() {
        return current = current.prev;
    }

    public String selectEquation() {
        return current.getNode();
    }

    public void goToTail() {
        current = tail;
    }

    public void addEquation(Equation newEquation) {
        // If adding the first equation to the history
        if (tail.prev == null) {
            // All pointers on the only relevant node
            // Remember: tail is just the endcap. tail.prev is the "conventional" tail
            current = tail.prev = newEquation;
        } else {
            // Sandwiches new node between previous newest equation, and the endcap
            newEquation.prev = tail.prev;
            current = tail.prev = tail.prev.next = newEquation;
        }
    }

    //TODO: Rework
    public void deleteSelected() {
        int flag = 2;

        if (current.prev != null)
            current.prev.next = current.next;
        else
            flag--;

        if (current.next != null)
            current.next.prev = current.prev;
        else
            flag--;

        if (flag != 0)
            getPreviousEquation();
        else
            current = tail;
    }

    public Boolean moveToNext() {
        Boolean changed = false;
        // If at the most recent equation, node doesn't change and method returns false
        if (current.next != null) {
            current = current.next;
            changed = true;
        }
        return changed;
    }

    public void moveToPrev() {
        current = current.prev == null ? current : current.prev;
    }

    // For testing //
    public Equation getCurrent() {
        return current;
    }

    public Equation getTail() {
        // Returns tail.prev as tail is just an empty endcap j(for the moveToPrev() method)
        return tail.prev;
    }

    public void save(Storage storage) {
        current = tail;
        
        while(current.prev != null) {
            current = current.prev;
        }

        do {
            storage.addNode(current);
        } while(moveToNext());
    }
}
