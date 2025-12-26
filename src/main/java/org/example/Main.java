package org.example;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class Main extends JFrame {
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private ArrayList<String[]> listData = new ArrayList<>();
    private final File database = new File("database_tugasin.txt");
    private ListDataPanel listDataPanel;
    private HistoryPanel historyPanel;

    // Color Palette
    protected static final Color COLOR_BG = new Color(13, 13, 18);
    protected static final Color COLOR_CARD = new Color(24, 24, 32);
    protected static final Color COLOR_ACCENT = new Color(160, 100, 255);
    protected static final Color COLOR_CYAN = new Color(0, 220, 200);
    protected static final Color COLOR_RED = new Color(255, 70, 85);
    protected static final Color COLOR_TEXT = new Color(230, 230, 240);

    // Fonts
    protected static final Font FONT_BOLD = new Font("Segoe UI Black", Font.BOLD, 14);
    protected static final Font FONT_MAIN = new Font("Segoe UI Semibold", Font.PLAIN, 14);

    public Main() {
        setTitle("TUGASIN - Task Manager");
        setSize(1100, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(COLOR_BG);

        DataManager.loadData(database, listData);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setOpaque(false);

        // Inisialisasi Panel
        historyPanel = new HistoryPanel(listData);
        listDataPanel = new ListDataPanel(this, listData, database, historyPanel);

        // Daftarkan 4 Halaman (Syarat Modul)
        mainPanel.add(new DashboardPanel(), "Dashboard");
        mainPanel.add(listDataPanel, "Daftar Tugas");
        mainPanel.add(new InputPanel(this, listData, database, cardLayout, mainPanel, listDataPanel), "Input Tugas");
        mainPanel.add(historyPanel, "Laporan");

        setLayout(new BorderLayout());
        add(new SidebarPanel(cardLayout, mainPanel, listDataPanel, historyPanel), BorderLayout.WEST);
        add(mainPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }
}