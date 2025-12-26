package org.example;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class ListDataPanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private ArrayList<String[]> listData;
    private File database;
    private TableRowSorter<DefaultTableModel> sorter;
    private JTextField txtSearch;
    private HistoryPanel historyPanel;

    public ListDataPanel(JFrame parent, ArrayList<String[]> listData, File database, HistoryPanel historyPanel) {
        this.listData = listData;
        this.database = database;
        this.historyPanel = historyPanel;

        setLayout(new BorderLayout(0, 20));
        setBorder(new EmptyBorder(30, 30, 30, 30));
        setOpaque(false);

        // Filter & Search Bar
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setOpaque(false);

        JPanel filterBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        filterBar.setOpaque(false);
        filterBar.add(new JLabel("<html><b style='color:white;'>FILTER:</b></html>"));
        filterBar.add(createFilterBtn("SEMUA", Main.COLOR_ACCENT));
        filterBar.add(createFilterBtn("BELUM", Main.COLOR_RED));
        filterBar.add(createFilterBtn("SUDAH", Main.COLOR_CYAN));

        JPanel searchBar = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchBar.setOpaque(false);
        searchBar.add(new JLabel("<html><b style='color:white;'>CARI:</b></html>"));
        txtSearch = new JTextField(15);
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent e) {
                String text = txtSearch.getText();
                if (text.trim().length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }
        });
        searchBar.add(txtSearch);

        topBar.add(filterBar, BorderLayout.WEST);
        topBar.add(searchBar, BorderLayout.EAST);

        // Table setup with Sorter
        model = new DefaultTableModel(new String[]{"ID", "Matkul", "Tugas", "Tanggal", "Deadline", "Status"}, 0);
        table = new JTable(model);
        sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter); // Fitur Sorting

        table.setBackground(Main.COLOR_CARD);
        table.setForeground(Main.COLOR_TEXT);
        table.setRowHeight(40);
        table.setFont(Main.FONT_MAIN);

        JScrollPane scroll = new JScrollPane(table);
        scroll.getViewport().setBackground(Main.COLOR_BG);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        actions.setOpaque(false);
        JButton btnUpdate = UIUtils.styleBtn("Tandai Selesai", Main.COLOR_CYAN);
        btnUpdate.addActionListener(e -> updateStatusTugas());
        JButton btnHapus = UIUtils.styleBtn("Hapus", Main.COLOR_RED);
        btnHapus.addActionListener(e -> hapusData());
        actions.add(btnUpdate);
        actions.add(btnHapus);

        add(topBar, BorderLayout.NORTH);
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
        historyPanel.updateStats();
    }

    private void updateStatusTugas() {
        int r = table.getSelectedRow();
        if (r != -1) {
            int modelRow = table.convertRowIndexToModel(r);
            String id = model.getValueAt(modelRow, 0).toString();
            for (String[] row : listData) if (row[0].equals(id)) row[5] = "SUDAH";
            DataManager.saveData(database, listData);
            refreshTable("SEMUA");
        }
    }

    private void hapusData() {
        int r = table.getSelectedRow();
        if (r != -1) {
            int modelRow = table.convertRowIndexToModel(r);
            String id = model.getValueAt(modelRow, 0).toString();
            listData.removeIf(row -> row[0].equals(id));
            DataManager.saveData(database, listData);
            refreshTable("SEMUA");
        }
    }
}