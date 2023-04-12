package com.calcyoulater.storage;

import java.util.ArrayList;

/*
 * @author Joshua Pokorzynski
 */
public class History {

    private static Equation current;
    private static Equation endcap;
    private static History uniqueInstance;

    // Uses singleton design pattern
    private History() {
        endcap = new Equation(null);
    }

    
    public static History instance() {
        if (uniqueInstance == null)
            uniqueInstance = new History();
        return uniqueInstance;
    }

    public void deleteHistory() {
        endcap.prev = null;
        current = endcap;
    }

    public String selectEquation() {
        return current.getNode();
    }

    public void goToTail() {
        current = endcap;
    }

    public void addEquation(Equation newEquation) {
        // If adding the first equation to the history
        if (endcap.prev == null) {
            // All pointers on the only relevant node
            // Remember: tail is just the endcap. tail.prev is the "conventional" tail
            current = endcap.prev = newEquation;
        } else {
            // Sandwiches new node between previous newest equation, and the endcap
            newEquation.prev = endcap.prev;
            current = endcap.prev = endcap.prev.next = newEquation;
        }
    }

    public void deleteSelected() {
        // Makes sure endcap is never deleted
        if (current != endcap) {
            if (current.prev != null) {
                current.prev.next = current.next;
            }
            if (current.next != null) {
                current.next.prev = current.prev;
            } else {
                // Since last equation can't link back to endcap
                endcap.prev = current.prev;
            }
            // Resets back to beginning for user
            current = endcap;
        }
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

    // Returns arraylist of all current nodes in history
    public ArrayList<Equation> getList() {
        ArrayList<Equation> list = new ArrayList<>();
        // Goes to head
        while (current.prev != null) {
            current = current.prev;
        }
        // Fills list
        while (current != null) {
            list.add(current);
            current = current.next;
        }
        // Resets to endcap for user
        current = endcap;
        return list;
    }

    // For testing //
    public Equation getCurrent() {
        return current;
    }

    public Equation getTail() {
        // Returns most recent equation
        return endcap.prev;
    }

}
