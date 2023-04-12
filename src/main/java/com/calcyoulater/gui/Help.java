package com.calcyoulater.gui;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;

/*
 * @author Joel Halkey
 */
public class Help extends JFrame {

    private static Help uniqueInstance;

    //Using singleton pattern
    private Help() {
        $$$setupUI$$$();
        helpInit();
        loadHelpText();
    }

    private JPanel MainPanel;
    private JTextPane helpTextPane;
    private JPanel textPanel;

    public static Help instance() {
        
        if (uniqueInstance == null){
            uniqueInstance = new Help();
        }
        uniqueInstance.setVisible(true);
        return uniqueInstance;
    }

    // Auto generated code by IntelliJ GUI Builder
    private void $$$setupUI$$$() {
        MainPanel = new JPanel();
        MainPanel.setLayout(new GridLayoutManager(3, 3, new Insets(0, 0, 0, 0), -1, -1));
        final Spacer spacer1 = new Spacer();
        MainPanel.add(spacer1, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER,
                GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        MainPanel.add(spacer2, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_CENTER,
                GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer3 = new Spacer();
        MainPanel.add(spacer3, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER,
                GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer4 = new Spacer();
        MainPanel.add(spacer4, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_CENTER,
                GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        textPanel = new JPanel();
        textPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        MainPanel.add(textPanel,
                new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null,
                        0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        scrollPane1.setVerticalScrollBarPolicy(22);
        textPanel.add(scrollPane1,
                new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW,
                        new Dimension(350, 350), new Dimension(350, 350), new Dimension(350, 350), 0, false));
        helpTextPane = new JTextPane();
        helpTextPane.setAutoscrolls(false);
        helpTextPane.setEditable(false);
        Font helpTextPaneFont = this.$$$getFont$$$("SansSerif", Font.BOLD, 12, helpTextPane.getFont());
        if (helpTextPaneFont != null)
            helpTextPane.setFont(helpTextPaneFont);
        scrollPane1.setViewportView(helpTextPane);
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
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
    }

    // Auto generated code by IntelliJ GUI Builder
    public JComponent $$$getRootComponent$$$() {
        return MainPanel;
    }

    private void loadHelpText() {
        try {
            BufferedReader txtReader = new BufferedReader(
                    new InputStreamReader(Help.class.getResourceAsStream("/help.txt")));
            (this).helpTextPane.read(txtReader, null);
        } catch (IOException e) {
            System.out.println("File might help.txt might have failed to open or might not exist");
            throw new RuntimeException(e);
        }
    }

    private void helpInit() {
        (this).setTitle("Help");
        (this).setContentPane(this.MainPanel);
        (this).setLocationRelativeTo(null);
        (this).setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        (this).setSize(375, 375);
        (this).setResizable(false);
        (this).setVisible(true);
    }
}
