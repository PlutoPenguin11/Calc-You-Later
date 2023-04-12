package com.calcyoulater.storage;

import java.util.ArrayList;

/*
 * @author Joshua Pokorzynski
 */
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

    public void deleteSelected() {
        // Makes sure endcap is never deleted
        if (current != tail) {
            if (current.prev != null) {
                current.prev.next = current.next;
            }
            if (current.next != null) {
                current.next.prev = current.prev;
            } else {
                // Since last equation can't link back to endcap
                tail.prev = current.prev;
            }
            // Resets back to beginning for user
            current = tail;
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
        current = tail;
        return list;
    }

    public static History newInstance() {
        uniqueInstance = new History();
        current = tail;
        return uniqueInstance;
    }

    // For testing //
    public Equation getCurrent() {
        return current;
    }

    public Equation getTail() {
        // Returns tail.prev as tail is just an empty endcap (for the moveToPrev() method)
        return tail.prev;
    }

}
