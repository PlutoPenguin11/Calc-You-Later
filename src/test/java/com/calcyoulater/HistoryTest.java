package com.calcyoulater;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.*;
import com.calcyoulater.storage.Equation;
import com.calcyoulater.storage.History;

public class HistoryTest {

    History history;
    Equation equation1;
    Equation equation2;
    Equation equation3;
    String string1;
    String string2;
    String string3;

    @BeforeEach
    public void setUp() {
        history = History.instance();

        string1 = "cos(45)";
        string2 = "1+3-2";
        string3 = "sin(42)";

        equation1 = new Equation(string1);
        equation2 = new Equation(string2);
        equation3 = new Equation(string3);

        history.addEquation(equation1);
        history.addEquation(equation2);
        history.addEquation(equation3);
    }

    @Test
    public void instance() {
        History history2 = History.instance();
        assertEquals(history, history2);
    }

    @Test
    public void selectEquation() {
        String equationText = history.selectEquation();
        assertEquals(string3, equationText);
    }

    @Test
    public void goToTail() {
        history.goToTail();
        assertEquals(equation3, history.getTail());
    }

    @Test
    public void addEquation() {
        Equation newEquation = new Equation("15/5");
        history.addEquation(newEquation);

        assertEquals(newEquation, history.getTail());
        assertEquals(newEquation, history.getCurrent());
        //assertEquals(equation3, history.getPreviousEquation());

        History history2 = History.instance();;
        history2.addEquation(newEquation);
        //assertEquals(newEquation, history2.getHead());
        assertEquals(newEquation, history2.getCurrent());
        assertEquals(newEquation, history2.getTail());
    }

    @Test
    public void deleteSelected() {
        assertEquals(equation3, history.getCurrent());

        history.deleteSelected();
        assertEquals(equation2, history.getTail());
        history.deleteSelected();
        history.moveToPrev();

        history.deleteSelected();
        assertEquals(equation1, history.getTail());
        history.deleteSelected();
        history.moveToPrev();
    }

    @Test
    public void moveToPrev() {
        Equation current = history.getTail();
        history.goToTail();
        history.moveToPrev();
        assertEquals(equation3, current);

        history.moveToPrev();
        current = history.getCurrent();
        assertEquals(equation2, current);

        history.moveToPrev();
        current = history.getCurrent();
        assertEquals(equation1, current);
    }

    @Test
    public void moveToNext() {
        for (int i = 0; i < 3; i++) {
            history.moveToPrev();
        }
        Equation current = history.getCurrent();
        
        assertEquals(equation1, current);

        history.moveToNext();
        current = history.getCurrent();
        assertEquals(equation2, current);

        history.moveToNext();
        current = history.getCurrent();
        assertEquals(equation3, current);

        history.moveToNext();
        current = history.getCurrent();
        assertEquals(equation3, current);
    }

    @Test
    public void getList() {
        ArrayList<Equation> list = history.getList();

        assertEquals(equation1, list.get(0));
        assertEquals(equation2, list.get(1));
        assertEquals(equation3, list.get(2));
    }
}
