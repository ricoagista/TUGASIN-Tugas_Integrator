package org.example;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class ListDataPanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private ArrayList<String[]> listData;
    private File database;

    public ListDataPanel(JFrame parent, ArrayList<String[]> listData, File database) {
        this.listData = listData;
        this.database = database;

        setLayout(new BorderLayout(0, 20));
        setBorder(new EmptyBorder(30, 30, 30, 30));
        setOpaque(false);

        JPanel filterBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        filterBar.setOpaque(false);
        filterBar.add(new JLabel("<html><b style='color:white;'>FILTER:</b></html>"));
        filterBar.add(createFilterBtn("SEMUA", Main.COLOR_ACCENT));
        filterBar.add(createFilterBtn("BELUM", Main.COLOR_RED));
        filterBar.add(createFilterBtn("SUDAH", Main.COLOR_CYAN));

        model = new DefaultTableModel(new String[]{"ID", "Matkul", "Tugas", "Tanggal", "Deadline", "Status"}, 0);
        table = new JTable(model);
        table.setBackground(Main.COLOR_CARD);
        table.setForeground(Main.COLOR_TEXT);
        table.setRowHeight(40);
        table.setFont(Main.FONT_MAIN);
        table.setSelectionBackground(new Color(50, 50, 70));

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(40, 40, 50)));
        scroll.getViewport().setBackground(Main.COLOR_BG);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        actions.setOpaque(false);
        JButton btnUpdate = UIUtils.styleBtn("Tandai Selesai", Main.COLOR_CYAN);
        btnUpdate.addActionListener(e -> updateStatusTugas());
        JButton btnHapus = UIUtils.styleBtn("Hapus", Main.COLOR_RED);
        btnHapus.addActionListener(e -> hapusData());
        actions.add(btnUpdate);
        actions.add(btnHapus);

        add(filterBar, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(actions, BorderLayout.SOUTH);

        refreshTable("SEMUA");
    }

    private JButton createFilterBtn(String f, Color c) {
        JButton b = new JButton(f);
        b.setFont(new Font("Segoe UI Black", Font.BOLD, 10));
        b.setBackground(Main.COLOR_CARD);
        b.setForeground(c);
        b.setBorder(BorderFactory.createLineBorder(c, 1));
        b.addActionListener(e -> refreshTable(f));
        return b;
    }

    public void refreshTable(String filter) {
        model.setRowCount(0);
        for (String[] r : listData) {
            if (filter.equals("SEMUA") || r[5].equals(filter)) model.addRow(r);
        }
    }

    private void updateStatusTugas() {
        int r = table.getSelectedRow();
        if (r != -1) {
            String id = table.getValueAt(r, 0).toString();
            for (String[] row : listData) if (row[0].equals(id)) row[5] = "SUDAH";
            DataManager.saveData(database, listData);
            refreshTable("SEMUA");
        }
    }

    private void hapusData() {
        int r = table.getSelectedRow();
        if (r != -1) {
            String id = table.getValueAt(r, 0).toString();
            listData.removeIf(row -> row[0].equals(id));
            DataManager.saveData(database, listData);
            refreshTable("SEMUA");
        }
    }
}