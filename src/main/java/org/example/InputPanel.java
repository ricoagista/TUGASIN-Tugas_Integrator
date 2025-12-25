package org.example;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class InputPanel extends JPanel {
    private JTextField txtMatkul, txtTugas, txtTglDiberikan, txtDeadline;
    private ArrayList<String[]> listData;
    private File database;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private ListDataPanel listDataPanel;

    public InputPanel(JFrame parent, ArrayList<String[]> listData, File database, CardLayout cardLayout, JPanel mainPanel, ListDataPanel listDataPanel) {
        this.listData = listData;
        this.database = database;
        this.cardLayout = cardLayout;
        this.mainPanel = mainPanel;
        this.listDataPanel = listDataPanel;

        setLayout(new GridBagLayout());
        setOpaque(false);

        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(Main.COLOR_CARD);
        card.setBorder(new EmptyBorder(30, 30, 30, 30));
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(10, 10, 10, 10);
        g.fill = GridBagConstraints.BOTH;

        txtMatkul = createField();
        txtTugas = createField();
        txtTglDiberikan = createField();
        txtDeadline = createField();
        txtTglDiberikan.setEditable(false);
        txtDeadline.setEditable(false);

        addRow(card, "MATKUL", txtMatkul, g, 0);
        addRow(card, "TUGAS", txtTugas, g, 1);
        addDateRow(card, parent, "Tanggal", txtTglDiberikan, g, 2);
        addDateRow(card, parent, "Deadline", txtDeadline, g, 3);

        JButton btnSave = UIUtils.styleBtn("ADD TASK 🔥", Main.COLOR_ACCENT);
        btnSave.addActionListener(e -> createData());
        g.gridy = 4;
        g.gridx = 1;
        card.add(btnSave, g);

        add(card);
    }

    private void addRow(JPanel p, String l, JTextField tf, GridBagConstraints g, int y) {
        g.gridy = y;
        g.gridx = 0;
        p.add(new JLabel("<html><b style='color:#A064FF;'>" + l + "</b></html>"), g);
        g.gridx = 1;
        p.add(tf, g);
    }

    private void addDateRow(JPanel p, JFrame parent, String l, JTextField tf, GridBagConstraints g, int y) {
        g.gridy = y;
        g.gridx = 0;
        p.add(new JLabel("<html><b style='color:#A064FF;'>" + l + "</b></html>"), g);
        JPanel pnl = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnl.setOpaque(false);
        pnl.add(tf);
        JButton btn = UIUtils.styleBtn("📅", new Color(50, 50, 60));
        btn.addActionListener(e -> new CalendarDialog(parent, tf));
        pnl.add(btn);
        g.gridx = 1;
        p.add(pnl, g);
    }

    private JTextField createField() {
        JTextField tf = new JTextField(20);
        tf.setBackground(new Color(35, 35, 45));
        tf.setForeground(Color.WHITE);
        tf.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        tf.setFont(Main.FONT_MAIN);
        return tf;
    }

    private void createData() {
        if (txtMatkul.getText().isEmpty()) return;
        int nextID = DataManager.getNextID(listData);
        String[] data = {String.valueOf(nextID), txtMatkul.getText(), txtTugas.getText(),
                txtTglDiberikan.getText(), txtDeadline.getText(), "BELUM"};
        listData.add(data);
        DataManager.saveData(database, listData);

        // Clear fields
        txtMatkul.setText("");
        txtTugas.setText("");
        txtTglDiberikan.setText("");
        txtDeadline.setText("");

        // Refresh list and switch view
        listDataPanel.refreshTable("SEMUA");
        cardLayout.show(mainPanel, "Daftar Tugas");
    }
}