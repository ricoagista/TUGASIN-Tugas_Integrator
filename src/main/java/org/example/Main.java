package org.example;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;

public class Main extends JFrame {

    private JTextField txtMatkul, txtTugas;
    private JTextField txtTglDiberikan, txtDeadline;
    private JTable table;
    private DefaultTableModel model;
    private CardLayout cardLayout;
    private JPanel mainPanel;

    private final File database = new File("database_tugasin.txt");
    private ArrayList<String[]> listData = new ArrayList<>();

    // ===== GEN Z PALETTE (DARK VIBES) =====
    private final Color COLOR_BG = new Color(13, 13, 18);
    private final Color COLOR_CARD = new Color(24, 24, 32);
    private final Color COLOR_ACCENT = new Color(160, 100, 255); // Neon Purple
    private final Color COLOR_CYAN = new Color(0, 220, 200);
    private final Color COLOR_RED = new Color(255, 70, 85);
    private final Color COLOR_TEXT = new Color(230, 230, 240);

    private final Font FONT_BOLD = new Font("Segoe UI Black", Font.BOLD, 14);
    private final Font FONT_MAIN = new Font("Segoe UI Semibold", Font.PLAIN, 14);

    public Main() {
        setTitle("TUGASIN - Task Manager");
        setSize(1100, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(COLOR_BG);

        muatDataDariFile();

        // --- SIDEBAR ---
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(COLOR_CARD);
        sidebar.setPreferredSize(new Dimension(220, 0));
        sidebar.setBorder(new EmptyBorder(40, 20, 0, 20));

        JLabel logo = new JLabel("TUGASIN.");
        logo.setForeground(COLOR_ACCENT);
        logo.setFont(new Font("Segoe UI Black", Font.BOLD, 28));
        sidebar.add(logo);
        sidebar.add(Box.createRigidArea(new Dimension(0, 50)));

        sidebar.add(createNavBtn("Dashboard"));
        sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebar.add(createNavBtn("Daftar Tugas"));
        sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebar.add(createNavBtn("Input Tugas"));

        // --- MAIN ---
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setOpaque(false);

        mainPanel.add(createDashboardPanel(), "Dashboard");
        mainPanel.add(createListDataPanel(), "Daftar Tugas");
        mainPanel.add(createInputPanel(), "Input Tugas");

        setLayout(new BorderLayout());
        add(sidebar, BorderLayout.WEST);
        add(mainPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private JButton createNavBtn(String text) {
        JButton btn = new JButton(text);
        btn.setFont(FONT_BOLD);
        btn.setForeground(new Color(120, 120, 140));
        btn.setBackground(COLOR_CARD);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.addActionListener(e -> {
            cardLayout.show(mainPanel, text);
            if(text.equals("Daftar Tugas")) refreshTable("SEMUA");
        });
        return btn;
    }

    private JPanel createDashboardPanel() {
        JPanel p = new JPanel(new GridBagLayout());
        p.setOpaque(false);
        p.add(new JLabel("<html><center><h1 style='color:#A064FF; font-family:Segoe UI Black;'>DO YOUR TASKS.</h1>" +
                "<p style='color:gray;'>Gak usah banyak alibi, kerjain sekarang.</p></center></html>"));
        return p;
    }

    private JPanel createListDataPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 20));
        panel.setBorder(new EmptyBorder(30, 30, 30, 30));
        panel.setOpaque(false);

        // --- FILTER SECTION ---
        JPanel filterBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        filterBar.setOpaque(false);

        filterBar.add(new JLabel("<html><b style='color:white;'>FILTER:</b></html>"));
        filterBar.add(createFilterBtn("SEMUA", COLOR_ACCENT));
        filterBar.add(createFilterBtn("BELUM", COLOR_RED));
        filterBar.add(createFilterBtn("SUDAH", COLOR_CYAN));

        // --- TABLE ---
        model = new DefaultTableModel(new String[]{"ID", "Matkul", "Tugas", "Tanggal", "Deadline", "Status"}, 0);
        table = new JTable(model);
        table.setBackground(COLOR_CARD);
        table.setForeground(COLOR_TEXT);
        table.setRowHeight(40);
        table.setFont(FONT_MAIN);
        table.setSelectionBackground(new Color(50, 50, 70));

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(40, 40, 50)));
        scroll.getViewport().setBackground(COLOR_BG);

        // --- ACTION SECTION ---
        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        actions.setOpaque(false);

        JButton btnUpdate = styleBtn("Tandai Selesai", COLOR_CYAN);
        btnUpdate.addActionListener(e -> updateStatusTugas());

        JButton btnHapus = styleBtn("Hapus", COLOR_RED);
        btnHapus.addActionListener(e -> hapusData());

        actions.add(btnUpdate);
        actions.add(btnHapus);

        panel.add(filterBar, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);
        panel.add(actions, BorderLayout.SOUTH);

        return panel;
    }

    private JButton createFilterBtn(String f, Color c) {
        JButton b = new JButton(f);
        b.setFont(new Font("Segoe UI Black", Font.BOLD, 10));
        b.setBackground(COLOR_CARD);
        b.setForeground(c);
        b.setBorder(BorderFactory.createLineBorder(c, 1));
        b.addActionListener(e -> refreshTable(f));
        return b;
    }

    private JPanel createInputPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);

        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(COLOR_CARD);
        card.setBorder(new EmptyBorder(30, 30, 30, 30));
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(10, 10, 10, 10); g.fill = 1;

        txtMatkul = createField(); txtTugas = createField();
        txtTglDiberikan = createField(); txtDeadline = createField();
        txtTglDiberikan.setEditable(false); txtDeadline.setEditable(false);

        addRow(card, "MATKUL", txtMatkul, g, 0);
        addRow(card, "TUGAS", txtTugas, g, 1);

        g.gridy=2; g.gridx=0; card.add(new JLabel("<html><b style='color:#A064FF;'>Tanggal</b></html>"), g);
        JPanel p1 = new JPanel(new FlowLayout(0)); p1.setOpaque(false);
        p1.add(txtTglDiberikan); JButton b1 = styleBtn("📅", Color.DARK_GRAY);
        b1.addActionListener(e -> new CalendarDialog(this, txtTglDiberikan));
        p1.add(b1); g.gridx=1; card.add(p1, g);

        g.gridy=3; g.gridx=0; card.add(new JLabel("<html><b style='color:#A064FF;'>Deadline</b></html>"), g);
        JPanel p2 = new JPanel(new FlowLayout(0)); p2.setOpaque(false);
        p2.add(txtDeadline); JButton b2 = styleBtn("📅", Color.DARK_GRAY);
        b2.addActionListener(e -> new CalendarDialog(this, txtDeadline));
        p2.add(b2); g.gridx=1; card.add(p2, g);

        JButton btnSave = styleBtn("ADD TASK 🔥", COLOR_ACCENT);
        btnSave.addActionListener(e -> createData());
        g.gridy=4; g.gridx=1; card.add(btnSave, g);

        panel.add(card);
        return panel;
    }

    private void addRow(JPanel p, String l, JTextField tf, GridBagConstraints g, int y) {
        g.gridy=y; g.gridx=0; p.add(new JLabel("<html><b style='color:#A064FF;'>"+l+"</b></html>"), g);
        g.gridx=1; p.add(tf, g);
    }

    private JTextField createField() {
        JTextField tf = new JTextField(20);
        tf.setBackground(new Color(35, 35, 45)); tf.setForeground(Color.WHITE);
        tf.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        tf.setFont(FONT_MAIN); return tf;
    }

    private JButton styleBtn(String t, Color c) {
        JButton b = new JButton(t); b.setBackground(c); b.setForeground(COLOR_BG);
        b.setFont(FONT_BOLD); b.setBorder(new EmptyBorder(10, 20, 10, 20));
        b.setCursor(new Cursor(Cursor.HAND_CURSOR)); return b;
    }

    // --- LOGIKA PROGRAM (KEMBALI NORMAL) ---
    private void muatDataDariFile() {
        if (!database.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(database))) {
            String line;
            while ((line = br.readLine()) != null) listData.add(line.split("\\|"));
        } catch (Exception e) {}
    }

    private void simpanKeFile() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(database))) {
            for (String[] r : listData) pw.println(String.join("|", r));
        } catch (Exception e) {}
    }

    private void refreshTable(String filter) {
        model.setRowCount(0);
        for (String[] r : listData) {
            if (filter.equals("SEMUA") || r[5].equals(filter)) model.addRow(r);
        }
    }

    private void createData() {
        if (txtMatkul.getText().isEmpty()) return;
        int nextID = 1;
        if(!listData.isEmpty()) {
            for(String[] r : listData) nextID = Math.max(nextID, Integer.parseInt(r[0]) + 1);
        }
        String[] data = {String.valueOf(nextID), txtMatkul.getText(), txtTugas.getText(),
                txtTglDiberikan.getText(), txtDeadline.getText(), "BELUM"};
        listData.add(data);
        simpanKeFile(); refreshTable("SEMUA");
        cardLayout.show(mainPanel, "Daftar Tugas");
    }

    private void updateStatusTugas() {
        int r = table.getSelectedRow();
        if (r != -1) {
            String id = table.getValueAt(r, 0).toString();
            for (String[] row : listData) if (row[0].equals(id)) row[5] = "SUDAH";
            simpanKeFile(); refreshTable("SEMUA");
        }
    }

    private void hapusData() {
        int r = table.getSelectedRow();
        if (r != -1) {
            String id = table.getValueAt(r, 0).toString();
            listData.removeIf(row -> row[0].equals(id));
            simpanKeFile(); refreshTable("SEMUA");
        }
    }

    // --- CALENDAR ---
    class CalendarDialog extends JDialog {
        private YearMonth cm = YearMonth.now();
        private JPanel pnl; private JLabel lbl; private JTextField target;

        public CalendarDialog(JFrame p, JTextField t) {
            super(p, "Date", true); this.target = t; setSize(350, 400); setLocationRelativeTo(p);
            getContentPane().setBackground(COLOR_CARD);
            lbl = new JLabel("", 0); lbl.setForeground(COLOR_ACCENT); lbl.setFont(FONT_BOLD);
            JButton bP = new JButton("<"); JButton bN = new JButton(">");
            bP.addActionListener(e -> { cm = cm.minusMonths(1); update(); });
            bN.addActionListener(e -> { cm = cm.plusMonths(1); update(); });
            JPanel h = new JPanel(new BorderLayout()); h.setOpaque(false);
            h.add(bP, BorderLayout.WEST); h.add(lbl); h.add(bN, BorderLayout.EAST);
            pnl = new JPanel(new GridLayout(0, 7, 2, 2)); pnl.setOpaque(false);
            update(); setLayout(new BorderLayout()); add(h, "North"); add(pnl, "Center"); setVisible(true);
        }
        private void update() {
            pnl.removeAll(); lbl.setText(cm.getMonth() + " " + cm.getYear());
            String[] ds = {"M","S","S","R","K","J","S"};
            for (String d : ds) { JLabel l = new JLabel(d, 0); l.setForeground(COLOR_CYAN); pnl.add(l); }
            int gap = cm.atDay(1).getDayOfWeek().getValue() % 7;
            for (int i=0; i<gap; i++) pnl.add(new JLabel(""));
            for (int i=1; i<=cm.lengthOfMonth(); i++) {
                JButton b = new JButton(String.valueOf(i)); b.setMargin(new Insets(0,0,0,0));
                int d = i; b.addActionListener(e -> { target.setText(cm.atDay(d).toString()); dispose(); });
                pnl.add(b);
            }
            pnl.revalidate(); pnl.repaint();
        }
    }

    public static void main(String[] args) { SwingUtilities.invokeLater(Main::new); }
}