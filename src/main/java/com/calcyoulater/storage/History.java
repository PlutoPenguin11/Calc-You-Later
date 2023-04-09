package com.calcyoulater.storage;

public class History {

    private static Equation head;
    private static Equation current;
    private static Equation tail;

    private static History uniqueInstance;

    private History() {

    }

    public static History instance() {
        if (uniqueInstance == null)
            uniqueInstance = new History();
        return uniqueInstance;
    }

    public static Equation getPreviousEquation() {
        return current = current.prev;
    }

    public static String selectEquation() {
        return current.getNode();
    }

    public static void goToHead() {
        current = head;
    }

    public static void goToTail() {
        current = tail;
    }

    public static void addEquation(Equation newEquation) {
        if (head == null) {
            head = current = tail = newEquation;
        } else {
            newEquation.prev = tail;
            current = tail = tail.next = newEquation;
        }
    }

    public static void deleteSelected() {
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
            head = current = null;
    }

    public static void moveToNext() {
        current = current.next == null ? current : current.next;
    }

    public static void moveToPrev() {
        current = current.prev == null ? current : current.prev;
    }

    // For testing
    public static Equation getHead() {
        return head;
    }

    public static Equation getCurrent() {
        return current;
    }

    public static Equation getTail() {
        return tail;
    }
}
