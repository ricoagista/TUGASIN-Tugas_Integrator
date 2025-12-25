package org.example;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class SidebarPanel extends JPanel {
    public SidebarPanel(CardLayout cardLayout, JPanel mainPanel, ListDataPanel listDataPanel) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Main.COLOR_CARD);
        setPreferredSize(new Dimension(220, 0));
        setBorder(new EmptyBorder(40, 20, 0, 20));

        JLabel logo = new JLabel("TUGASIN.");
        logo.setForeground(Main.COLOR_ACCENT);
        logo.setFont(new Font("Segoe UI Black", Font.BOLD, 28));
        add(logo);
        add(Box.createRigidArea(new Dimension(0, 50)));

        add(createNavBtn("Dashboard", cardLayout, mainPanel, listDataPanel));
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(createNavBtn("Daftar Tugas", cardLayout, mainPanel, listDataPanel));
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(createNavBtn("Input Tugas", cardLayout, mainPanel, listDataPanel));
    }

    private JButton createNavBtn(String text, CardLayout cardLayout, JPanel mainPanel, ListDataPanel listDataPanel) {
        JButton btn = new JButton(text);
        btn.setFont(Main.FONT_BOLD);
        btn.setForeground(new Color(120, 120, 140));
        btn.setBackground(Main.COLOR_CARD);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addActionListener(e -> {
            if (text.equals("Daftar Tugas")) {
                listDataPanel.refreshTable("SEMUA");
            }
            cardLayout.show(mainPanel, text);
        });
        return btn;
    }
}