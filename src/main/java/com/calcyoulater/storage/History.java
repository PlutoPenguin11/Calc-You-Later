package com.calcyoulater.storage;

public class History {
    
    private Equation head;
    private Equation current;
    private Equation tail;

    private static History uniqueInstance;

    private History() {

    }

    public static History instance() {
        if(uniqueInstance == null) uniqueInstance = new History();
        return uniqueInstance;
    }

    public Equation getPreviousEquation() {
        return current = current.prev;
    }

    public String selectEquation() {
        return current.getNode();
    }

    public void goToHead() {
        current = head;
    }

    public void goToTail() {
        current = tail;
    }

    public void addEquation(Equation newEquation) {
        if(head == null) {
            head = current = tail = newEquation;
        } else {
            newEquation.prev = tail;
            current = tail = tail.next = newEquation;
        }
    }

    public void deleteSelected() {
        int flag = 2;

        if (current.prev != null) current.prev.next = current.next;
        else flag--;

        if (current.next != null) current.next.prev = current.prev;
        else flag--;

        if (flag != 0) getPreviousEquation();
        else head = current = null;
    }

    public void moveToNext() {
        current = current.next == null ? current : current.next;
    }

    public void moveToPrev() {
        current = current.prev == null ? current : current.prev;
    }

    //For testing
    public Equation getHead() {
        return head;
    }

    public Equation getCurrent() {
        return current;
    }

    public Equation getTail() {
        return tail;
    }
}
