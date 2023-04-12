package com.calcyoulater.gui;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.util.Locale;
import com.calcyoulater.storage.Equation;
import com.calcyoulater.storage.History;
import com.calcyoulater.storage.Storage;
import com.calcyoulater.working.Statistics;
import java.util.ArrayList;

public class Calculator extends JFrame {
        private JButton zeroButton;
        private JButton decimalButton;
        private JButton equalsButton;
        private JButton oneButton;
        private JButton twoButton;
        private JButton threeButton;
        private JButton fourButton;
        private JButton fiveButton;
        private JButton sixButton;
        private JButton sevenButton;
        private JButton eightButton;
        private JButton nineButton;
        private JButton commaButton;
        private JButton leftParenthesisButton;
        private JButton rightParenthesisButton;
        private JButton sinButton;
        private JButton cosButton;
        private JButton tanButton;
        private JButton enterButton;
        private JButton plusButton;
        private JButton subtractButton;
        private JButton multiplicationButton;
        private JButton divisionButton;
        private JButton caretButton;
        private JButton nextButton;
        private JButton deleteButton;
        private JButton clearButton;
        private JTextArea outputTextArea;
        private JPanel calculatorPanel;
        private JPanel buttonPanel;
        private JTextField inputField;
        private JButton homeButton;
        private JButton graphButton;
        private JButton statsButton;
        private JButton helpButton;
        private JButton prevButton;
        private JPanel mainPanel;
        private JPanel graphingContainerPanel;
        private JPanel windowPanel;
        private JLabel modeLabel;
        private JLabel stateLabel;
        private History historyInstance = History.instance();
        private Storage storageInstance = Storage.instance();
        private String stateString = "HOME";
        private static Calculator calculatorInstance;
        private JButton deleteHistoryButton;

        private Calculator() {
                loadStorage();
                $$$setupUI$$$();
                actionListenerInit();
                calculatorInit();
        }

        private void loadStorage() {
                ArrayList<Equation> list = storageInstance.getEquations();
                for (Equation eq : list) {
                        historyInstance.addEquation(eq);
                }
                historyInstance.goToTail();
        }

        public static Calculator instance() {
                if (calculatorInstance == null)
                        calculatorInstance = new Calculator();
                return calculatorInstance;
        }

        private void actionListenerInit() {

                enterButton.addActionListener(e -> {
                        switch (this.stateString) {
                                case "HOME":
                                        Equation eq = new Equation(inputField.getText());
                                        // If string is not empty, adds to history
                                        if (eq.getNode().equalsIgnoreCase("Penguin")) {
                                                outputTextArea.setText("You pengWIN!!!");
                                        } else if (eq.getNode().equals("5318008")) {
                                                outputTextArea.setText("You win, I guess");
                                        } else if (eq.getNode().length() > 0) {
                                                historyInstance.addEquation(eq);
                                                outputTextArea.setText(eq.parse());
                                                storageInstance.addNode(eq);
                                                storageInstance.serialize();
                                        } else {
                                                outputTextArea.setText("Please enter an equation.");
                                        }
                                        break;
                                case "GRAPH":
                                        Grapher g = new Grapher(inputField.getText());
                                        break;
                                case "STATS":
                                        outputTextArea.setText(Statistics.getSummary(inputField.getText()));
                                        break;
                                default:

                        }
                });

                homeButton.addActionListener(e -> {
                        stateLabel.setText("            Home");
                        this.stateString = "HOME";
                });

                graphButton.addActionListener(e -> {
                        stateLabel.setText("            Graphing");
                        this.stateString = "GRAPH";
                });

                statsButton.addActionListener(e -> {
                        stateLabel.setText("            Statistics");
                        this.stateString = "STATS";
                });

                helpButton.addActionListener(e -> {
                        Help helpInstance = Help.instance();
                });

                prevButton.addActionListener(e -> {
                        historyInstance.moveToPrev();
                        inputField.setText(historyInstance.selectEquation());
                });

                nextButton.addActionListener(e -> {
                        // Checks and only shows next node if currently "viewing" a history node
                        if (historyInstance.moveToNext())
                                inputField.setText(historyInstance.selectEquation());
                });

                equalsButton.addActionListener(e -> inputField.setText(inputField.getText() + "="));

                decimalButton.addActionListener(e -> inputField.setText(inputField.getText() + "."));

                zeroButton.addActionListener(e -> inputField.setText(inputField.getText() + "0"));

                plusButton.addActionListener(e -> inputField.setText(inputField.getText() + "+"));

                threeButton.addActionListener(e -> inputField.setText(inputField.getText() + "3"));

                twoButton.addActionListener(e -> inputField.setText(inputField.getText() + "2"));

                oneButton.addActionListener(e -> inputField.setText(inputField.getText() + "1"));

                subtractButton.addActionListener(e -> inputField.setText(inputField.getText() + "-"));

                sixButton.addActionListener(e -> inputField.setText(inputField.getText() + "6"));

                fiveButton.addActionListener(e -> inputField.setText(inputField.getText() + "5"));

                fourButton.addActionListener(e -> inputField.setText(inputField.getText() + "4"));

                multiplicationButton.addActionListener(e -> inputField.setText(inputField.getText() + "*"));

                nineButton.addActionListener(e -> inputField.setText(inputField.getText() + "9"));

                eightButton.addActionListener(e -> inputField.setText(inputField.getText() + "8"));

                sevenButton.addActionListener(e -> inputField.setText(inputField.getText() + "7"));

                divisionButton.addActionListener(e -> inputField.setText(inputField.getText() + "/"));

                rightParenthesisButton.addActionListener(e -> inputField.setText(inputField.getText() + ")"));

                leftParenthesisButton.addActionListener(e -> inputField.setText(inputField.getText() + "("));

                commaButton.addActionListener(e -> inputField.setText(inputField.getText() + ","));

                caretButton.addActionListener(e -> inputField.setText(inputField.getText() + "^"));

                tanButton.addActionListener(e -> inputField.setText(inputField.getText() + "tan("));

                cosButton.addActionListener(e -> inputField.setText(inputField.getText() + "cos("));

                sinButton.addActionListener(e -> inputField.setText(inputField.getText() + "sin("));

                clearButton.addActionListener(e -> {
                        inputField.setText("");
                        outputTextArea.setText("");
                        // Clear moves back to most recent node
                        historyInstance.goToTail();
                });

                deleteButton.addActionListener(e -> {
                        if (inputField.getText().equals(historyInstance.selectEquation())) {
                                historyInstance.deleteSelected();
                                storageInstance.clearStorage();

                                ArrayList<Equation> list = historyInstance.getList();
                                for (Equation eq: list) {
                                        storageInstance.addNode(eq);
                                }

                                storageInstance.serialize();

                                inputField.setText("");
                                outputTextArea.setText("");
                                
                        } else if (inputField != null) {
                                inputField.setText(inputField.getText().replaceAll(".$", ""));
                        }
                });

                deleteHistoryButton.addActionListener(e -> {
                        historyInstance = History.newInstance();
                        storageInstance.clearStorage();
                        inputField.setText("");
                        outputTextArea.setText("");
                });
        }

        // Auto generated code by IntelliJ GUI Builder
        private void $$$setupUI$$$() {
                windowPanel = new JPanel();
                windowPanel.setLayout(new GridLayoutManager(3, 3, new Insets(0, 0, 0, 0), -1, -1));
                mainPanel = new JPanel();
                mainPanel.setLayout(new GridLayoutManager(3, 4, new Insets(0, 0, 0, 0), -1, -1));
                mainPanel.setAutoscrolls(false);
                mainPanel.setMaximumSize(new Dimension(1000, 700));
                mainPanel.setPreferredSize(new Dimension(1000, 700));
                windowPanel.add(mainPanel,
                                new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER,
                                                GridConstraints.FILL_NONE,
                                                GridConstraints.SIZEPOLICY_CAN_SHRINK
                                                                | GridConstraints.SIZEPOLICY_CAN_GROW,
                                                GridConstraints.SIZEPOLICY_CAN_SHRINK
                                                                | GridConstraints.SIZEPOLICY_CAN_GROW,
                                                null, null, null,
                                                0, false));
                calculatorPanel = new JPanel();
                calculatorPanel.setLayout(new GridLayoutManager(6, 1, new Insets(0, 0, 0, 0), -1, -1));
                calculatorPanel.setMaximumSize(new Dimension(1000, 700));
                calculatorPanel.setOpaque(true);
                calculatorPanel.setVisible(true);
                mainPanel.add(calculatorPanel,
                                new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_CENTER,
                                                GridConstraints.FILL_NONE,
                                                GridConstraints.SIZEPOLICY_CAN_SHRINK
                                                                | GridConstraints.SIZEPOLICY_CAN_GROW,
                                                GridConstraints.SIZEPOLICY_CAN_SHRINK
                                                                | GridConstraints.SIZEPOLICY_CAN_GROW,
                                                null, null, null,
                                                0, false));
                buttonPanel = new JPanel();
                buttonPanel.setLayout(new GridLayoutManager(7, 4, new Insets(0, 0, 0, 0), -1, -1));
                buttonPanel.setEnabled(true);
                buttonPanel.setVisible(true);
                calculatorPanel.add(buttonPanel,
                                new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_CENTER,
                                                GridConstraints.FILL_BOTH,
                                                GridConstraints.SIZEPOLICY_CAN_SHRINK
                                                                | GridConstraints.SIZEPOLICY_CAN_GROW,
                                                GridConstraints.SIZEPOLICY_CAN_SHRINK
                                                                | GridConstraints.SIZEPOLICY_CAN_GROW,
                                                null,
                                                new Dimension(344, 244), null, 0, false));
                zeroButton = new JButton();
                zeroButton.setText("0");
                buttonPanel.add(zeroButton,
                                new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_CENTER,
                                                GridConstraints.FILL_HORIZONTAL,
                                                GridConstraints.SIZEPOLICY_CAN_SHRINK
                                                                | GridConstraints.SIZEPOLICY_CAN_GROW,
                                                GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
                oneButton = new JButton();
                oneButton.setText("1");
                buttonPanel.add(oneButton,
                                new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_CENTER,
                                                GridConstraints.FILL_HORIZONTAL,
                                                GridConstraints.SIZEPOLICY_CAN_SHRINK
                                                                | GridConstraints.SIZEPOLICY_CAN_GROW,
                                                GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
                decimalButton = new JButton();
                decimalButton.setText(".");
                buttonPanel.add(decimalButton,
                                new GridConstraints(6, 1, 1, 1, GridConstraints.ANCHOR_CENTER,
                                                GridConstraints.FILL_HORIZONTAL,
                                                GridConstraints.SIZEPOLICY_CAN_SHRINK
                                                                | GridConstraints.SIZEPOLICY_CAN_GROW,
                                                GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
                twoButton = new JButton();
                twoButton.setText("2");
                buttonPanel.add(twoButton,
                                new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_CENTER,
                                                GridConstraints.FILL_HORIZONTAL,
                                                GridConstraints.SIZEPOLICY_CAN_SHRINK
                                                                | GridConstraints.SIZEPOLICY_CAN_GROW,
                                                GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
                threeButton = new JButton();
                threeButton.setText("3");
                buttonPanel.add(threeButton,
                                new GridConstraints(5, 2, 1, 1, GridConstraints.ANCHOR_CENTER,
                                                GridConstraints.FILL_HORIZONTAL,
                                                GridConstraints.SIZEPOLICY_CAN_SHRINK
                                                                | GridConstraints.SIZEPOLICY_CAN_GROW,
                                                GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
                fourButton = new JButton();
                fourButton.setText("4");
                buttonPanel.add(fourButton,
                                new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER,
                                                GridConstraints.FILL_HORIZONTAL,
                                                GridConstraints.SIZEPOLICY_CAN_SHRINK
                                                                | GridConstraints.SIZEPOLICY_CAN_GROW,
                                                GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
                fiveButton = new JButton();
                fiveButton.setText("5");
                buttonPanel.add(fiveButton,
                                new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_CENTER,
                                                GridConstraints.FILL_HORIZONTAL,
                                                GridConstraints.SIZEPOLICY_CAN_SHRINK
                                                                | GridConstraints.SIZEPOLICY_CAN_GROW,
                                                GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
                sixButton = new JButton();
                sixButton.setText("6");
                buttonPanel.add(sixButton,
                                new GridConstraints(4, 2, 1, 1, GridConstraints.ANCHOR_CENTER,
                                                GridConstraints.FILL_HORIZONTAL,
                                                GridConstraints.SIZEPOLICY_CAN_SHRINK
                                                                | GridConstraints.SIZEPOLICY_CAN_GROW,
                                                GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
                sevenButton = new JButton();
                sevenButton.setText("7");
                buttonPanel.add(sevenButton,
                                new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER,
                                                GridConstraints.FILL_HORIZONTAL,
                                                GridConstraints.SIZEPOLICY_CAN_SHRINK
                                                                | GridConstraints.SIZEPOLICY_CAN_GROW,
                                                GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
                eightButton = new JButton();
                eightButton.setText("8");
                buttonPanel.add(eightButton,
                                new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_CENTER,
                                                GridConstraints.FILL_HORIZONTAL,
                                                GridConstraints.SIZEPOLICY_CAN_SHRINK
                                                                | GridConstraints.SIZEPOLICY_CAN_GROW,
                                                GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
                nineButton = new JButton();
                nineButton.setText("9");
                buttonPanel.add(nineButton,
                                new GridConstraints(3, 2, 1, 1, GridConstraints.ANCHOR_CENTER,
                                                GridConstraints.FILL_HORIZONTAL,
                                                GridConstraints.SIZEPOLICY_CAN_SHRINK
                                                                | GridConstraints.SIZEPOLICY_CAN_GROW,
                                                GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
                commaButton = new JButton();
                commaButton.setText(",");
                buttonPanel.add(commaButton,
                                new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER,
                                                GridConstraints.FILL_HORIZONTAL,
                                                GridConstraints.SIZEPOLICY_CAN_SHRINK
                                                                | GridConstraints.SIZEPOLICY_CAN_GROW,
                                                GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
                leftParenthesisButton = new JButton();
                leftParenthesisButton.setText("(");
                buttonPanel.add(leftParenthesisButton,
                                new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_CENTER,
                                                GridConstraints.FILL_HORIZONTAL,
                                                GridConstraints.SIZEPOLICY_CAN_SHRINK
                                                                | GridConstraints.SIZEPOLICY_CAN_GROW,
                                                GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
                rightParenthesisButton = new JButton();
                rightParenthesisButton.setText(")");
                buttonPanel.add(rightParenthesisButton,
                                new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_CENTER,
                                                GridConstraints.FILL_HORIZONTAL,
                                                GridConstraints.SIZEPOLICY_CAN_SHRINK
                                                                | GridConstraints.SIZEPOLICY_CAN_GROW,
                                                GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
                sinButton = new JButton();
                sinButton.setText("sin");
                buttonPanel.add(sinButton,
                                new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER,
                                                GridConstraints.FILL_HORIZONTAL,
                                                GridConstraints.SIZEPOLICY_CAN_SHRINK
                                                                | GridConstraints.SIZEPOLICY_CAN_GROW,
                                                GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
                cosButton = new JButton();
                cosButton.setText("cos");
                buttonPanel.add(cosButton,
                                new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER,
                                                GridConstraints.FILL_HORIZONTAL,
                                                GridConstraints.SIZEPOLICY_CAN_SHRINK
                                                                | GridConstraints.SIZEPOLICY_CAN_GROW,
                                                GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
                tanButton = new JButton();
                tanButton.setText("tan");
                buttonPanel.add(tanButton,
                                new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_CENTER,
                                                GridConstraints.FILL_HORIZONTAL,
                                                GridConstraints.SIZEPOLICY_CAN_SHRINK
                                                                | GridConstraints.SIZEPOLICY_CAN_GROW,
                                                GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
                enterButton = new JButton();
                enterButton.setText("enter");
                buttonPanel.add(enterButton,
                                new GridConstraints(6, 3, 1, 1, GridConstraints.ANCHOR_CENTER,
                                                GridConstraints.FILL_HORIZONTAL,
                                                GridConstraints.SIZEPOLICY_CAN_SHRINK
                                                                | GridConstraints.SIZEPOLICY_CAN_GROW,
                                                GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
                plusButton = new JButton();
                plusButton.setText("+");
                buttonPanel.add(plusButton,
                                new GridConstraints(5, 3, 1, 1, GridConstraints.ANCHOR_CENTER,
                                                GridConstraints.FILL_HORIZONTAL,
                                                GridConstraints.SIZEPOLICY_CAN_SHRINK
                                                                | GridConstraints.SIZEPOLICY_CAN_GROW,
                                                GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
                subtractButton = new JButton();
                subtractButton.setText("-");
                buttonPanel.add(subtractButton,
                                new GridConstraints(4, 3, 1, 1, GridConstraints.ANCHOR_CENTER,
                                                GridConstraints.FILL_HORIZONTAL,
                                                GridConstraints.SIZEPOLICY_CAN_SHRINK
                                                                | GridConstraints.SIZEPOLICY_CAN_GROW,
                                                GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
                multiplicationButton = new JButton();
                multiplicationButton.setText("x");
                buttonPanel.add(multiplicationButton,
                                new GridConstraints(3, 3, 1, 1, GridConstraints.ANCHOR_CENTER,
                                                GridConstraints.FILL_HORIZONTAL,
                                                GridConstraints.SIZEPOLICY_CAN_SHRINK
                                                                | GridConstraints.SIZEPOLICY_CAN_GROW,
                                                GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
                divisionButton = new JButton();
                divisionButton.setText("/");
                buttonPanel.add(divisionButton,
                                new GridConstraints(2, 3, 1, 1, GridConstraints.ANCHOR_CENTER,
                                                GridConstraints.FILL_HORIZONTAL,
                                                GridConstraints.SIZEPOLICY_CAN_SHRINK
                                                                | GridConstraints.SIZEPOLICY_CAN_GROW,
                                                GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
                caretButton = new JButton();
                caretButton.setText("^");
                buttonPanel.add(caretButton,
                                new GridConstraints(1, 3, 1, 1, GridConstraints.ANCHOR_CENTER,
                                                GridConstraints.FILL_HORIZONTAL,
                                                GridConstraints.SIZEPOLICY_CAN_SHRINK
                                                                | GridConstraints.SIZEPOLICY_CAN_GROW,
                                                GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
                nextButton = new JButton();
                nextButton.setText("next");
                buttonPanel.add(nextButton,
                                new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER,
                                                GridConstraints.FILL_HORIZONTAL,
                                                GridConstraints.SIZEPOLICY_CAN_SHRINK
                                                                | GridConstraints.SIZEPOLICY_CAN_GROW,
                                                GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
                deleteButton = new JButton();
                deleteButton.setText("delete");
                buttonPanel.add(deleteButton,
                                new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER,
                                                GridConstraints.FILL_HORIZONTAL,
                                                GridConstraints.SIZEPOLICY_CAN_SHRINK
                                                                | GridConstraints.SIZEPOLICY_CAN_GROW,
                                                GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
                clearButton = new JButton();
                clearButton.setText("clear");
                buttonPanel.add(clearButton,
                                new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER,
                                                GridConstraints.FILL_HORIZONTAL,
                                                GridConstraints.SIZEPOLICY_CAN_SHRINK
                                                                | GridConstraints.SIZEPOLICY_CAN_GROW,
                                                GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
                equalsButton = new JButton();
                equalsButton.setText("=");
                buttonPanel.add(equalsButton,
                                new GridConstraints(6, 2, 1, 1, GridConstraints.ANCHOR_CENTER,
                                                GridConstraints.FILL_HORIZONTAL,
                                                GridConstraints.SIZEPOLICY_CAN_SHRINK
                                                                | GridConstraints.SIZEPOLICY_CAN_GROW,
                                                GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
                prevButton = new JButton();
                prevButton.setText("prev");
                buttonPanel.add(prevButton,
                                new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER,
                                                GridConstraints.FILL_HORIZONTAL,
                                                GridConstraints.SIZEPOLICY_CAN_SHRINK
                                                                | GridConstraints.SIZEPOLICY_CAN_GROW,
                                                GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
                inputField = new JTextField();
                Font inputFieldFont = this.$$$getFont$$$("SansSerif", Font.BOLD, 12, inputField.getFont());
                if (inputFieldFont != null)
                        inputField.setFont(inputFieldFont);
                calculatorPanel.add(inputField,
                                new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_BOTH,
                                                GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED,
                                                null,
                                                new Dimension(344, 30), null, 0, false));
                final JToolBar toolBar1 = new JToolBar();
                toolBar1.setFloatable(false);
                toolBar1.setOrientation(0);
                toolBar1.setRollover(false);
                toolBar1.putClientProperty("JToolBar.isRollover", Boolean.FALSE);
                calculatorPanel.add(toolBar1,
                                new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER,
                                                GridConstraints.FILL_HORIZONTAL,
                                                GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED,
                                                null,
                                                new Dimension(344, 20), null, 0, false));
                deleteHistoryButton = new JButton();
                deleteHistoryButton.setText("DELETE HISTORY");
                deleteHistoryButton.setForeground(new Color(255, 0, 0));
                deleteHistoryButton.setBackground(new Color(100, 0, 0));
                mainPanel.add(deleteHistoryButton,
                                new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_CENTER,
                                                GridConstraints.FILL_HORIZONTAL,
                                                GridConstraints.SIZEPOLICY_CAN_SHRINK
                                                                | GridConstraints.SIZEPOLICY_CAN_GROW,
                                                GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));

                homeButton = new JButton();
                homeButton.setText("Home");
                toolBar1.add(homeButton);
                graphButton = new JButton();
                graphButton.setText("Graphing");
                toolBar1.add(graphButton);
                statsButton = new JButton();
                statsButton.setText("Stats");
                toolBar1.add(statsButton);
                helpButton = new JButton();
                helpButton.setContentAreaFilled(true);
                helpButton.setDoubleBuffered(false);
                helpButton.setText("Help");
                toolBar1.add(helpButton);
                outputTextArea = new JTextArea();
                outputTextArea.setEditable(true);
                Font outputTextAreaFont = this.$$$getFont$$$("SansSerif", Font.BOLD, 12, outputTextArea.getFont());
                if (outputTextAreaFont != null)
                        outputTextArea.setFont(outputTextAreaFont);
                calculatorPanel.add(outputTextArea,
                                new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER,
                                                GridConstraints.FILL_BOTH,
                                                GridConstraints.SIZEPOLICY_WANT_GROW,
                                                GridConstraints.SIZEPOLICY_WANT_GROW, null,
                                                new Dimension(344, 50), null, 0, false));
                final Spacer spacer1 = new Spacer();
                calculatorPanel.add(spacer1,
                                new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER,
                                                GridConstraints.FILL_VERTICAL, 1,
                                                GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(344, 14),
                                                null, 0, false));
                final Spacer spacer2 = new Spacer();
                calculatorPanel.add(spacer2,
                                new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER,
                                                GridConstraints.FILL_VERTICAL, 1,
                                                GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(344, 19),
                                                null, 0, false));
                graphingContainerPanel = new JPanel();
                graphingContainerPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
                mainPanel.add(graphingContainerPanel,
                                new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER,
                                                GridConstraints.FILL_BOTH,
                                                GridConstraints.SIZEPOLICY_CAN_SHRINK
                                                                | GridConstraints.SIZEPOLICY_CAN_GROW,
                                                GridConstraints.SIZEPOLICY_CAN_SHRINK
                                                                | GridConstraints.SIZEPOLICY_CAN_GROW,
                                                null, null, null,
                                                0, false));
                final Spacer spacer3 = new Spacer();
                mainPanel.add(spacer3, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER,
                                GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null,
                                null, 0, false));
                final Spacer spacer4 = new Spacer();
                mainPanel.add(spacer4, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_CENTER,
                                GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null,
                                null, 0, false));
                final Spacer spacer5 = new Spacer();
                mainPanel.add(spacer5, new GridConstraints(1, 3, 1, 1, GridConstraints.ANCHOR_CENTER,
                                GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null,
                                null, 0, false));
                final Spacer spacer6 = new Spacer();
                mainPanel.add(spacer6, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER,
                                GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null,
                                null, 0, false));
                final Spacer spacer7 = new Spacer();
                mainPanel.add(spacer7, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER,
                                GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null,
                                null, 0, false));
                final Spacer spacer8 = new Spacer();
                windowPanel.add(spacer8, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER,
                                GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null,
                                null, 0, false));
                final Spacer spacer9 = new Spacer();
                windowPanel.add(spacer9, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_CENTER,
                                GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null,
                                null, 0, false));
                final Spacer spacer10 = new Spacer();
                windowPanel.add(spacer10, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_CENTER,
                                GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null,
                                null, 0, false));
                final Spacer spacer11 = new Spacer();
                windowPanel.add(spacer11, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER,
                                GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null,
                                null, 0, false));
                modeLabel = new JLabel();
                modeLabel.setText("Mode:");
                calculatorPanel.add(modeLabel,
                                new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
                                                GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED,
                                                null, null, null, 0,
                                                false));
                stateLabel = new JLabel();
                stateLabel.setText("            Home");
                calculatorPanel.add(stateLabel,
                                new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
                                                GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED,
                                                null, null, null, 0,
                                                false));
        }

        // Auto generated code by IntelliJ GUI Builder
        private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
                if (currentFont == null)
                        return null;
                String resultName;
                if (fontName == null) {
                        resultName = currentFont.getName();
                } else {
                        Font testFont = new Font(fontName, Font.PLAIN, 10);
                        if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                                resultName = fontName;
                        } else {
                                resultName = currentFont.getName();
                        }
                }
                Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(),
                                size >= 0 ? size : currentFont.getSize());
                boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
                Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize())
                                : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
                return fontWithFallback instanceof FontUIResource ? fontWithFallback
                                : new FontUIResource(fontWithFallback);
        }

        // Auto generated code by IntelliJ GUI Builder
        public JComponent $$$getRootComponent$$$() {
                return windowPanel;
        }

        public void calculatorInit() {
                (this).setTitle("Calc-You-Later!");
                (this).setContentPane(this.windowPanel);
                (this).setSize(500, 500);
                (this).setLocationRelativeTo(null);
                (this).setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                (this).setVisible(true);
        }

}