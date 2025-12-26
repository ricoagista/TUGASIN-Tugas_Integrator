package org.example;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

public class HistoryPanel extends JPanel {
    private JLabel lblTotal, lblSelesai, lblBelum;
    private ArrayList<String[]> listData;

    public HistoryPanel(ArrayList<String[]> listData) {
        this.listData = listData;
        setLayout(new GridBagLayout());
        setOpaque(false);
        setBorder(new EmptyBorder(30, 30, 30, 30));

        JPanel card = new JPanel(new GridLayout(4, 1, 10, 20));
        card.setBackground(Main.COLOR_CARD);
        card.setBorder(new EmptyBorder(40, 40, 40, 40));

        JLabel title = new JLabel("RINGKASAN TUGAS");
        title.setForeground(Main.COLOR_ACCENT);
        title.setFont(new Font("Segoe UI Black", Font.BOLD, 24));

        lblTotal = createStatLabel();
        lblSelesai = createStatLabel();
        lblBelum = createStatLabel();

        card.add(title);
        card.add(lblTotal);
        card.add(lblSelesai);
        card.add(lblBelum);

        add(card);
        updateStats();
    }

    private JLabel createStatLabel() {
        JLabel l = new JLabel();
        l.setForeground(Color.WHITE);
        l.setFont(Main.FONT_BOLD);
        return l;
    }

    public void updateStats() {
        int total = listData.size();
        long selesai = listData.stream().filter(r -> r[5].equals("SUDAH")).count();
        long belum = total - selesai;

        lblTotal.setText("Total Semua Tugas  : " + total);
        lblSelesai.setText("Tugas Selesai       : " + selesai);
        lblBelum.setText("Tugas Belum Selesai : " + belum);
    }
}