package main.java;

import javax.swing.*;

public class Dto2typescriptDialog {
    private JPanel rootPanel;
    private JLabel tipText = new JLabel("请勾选需要生成的属性");
    private JTable fieldTable;


    public Dto2typescriptDialog() {
        rootPanel = new JPanel();
        rootPanel.add(tipText);
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }

    public void setRootPanel(JPanel rootPanel) {
        this.rootPanel = rootPanel;
    }

    public JTable getFieldTable() {
        return fieldTable;
    }

    public void setFieldTable(JTable fieldTable) {
        this.fieldTable = fieldTable;
    }













}
