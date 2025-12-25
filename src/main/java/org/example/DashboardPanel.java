package org.example;

import javax.swing.*;
import java.awt.*;

public class DashboardPanel extends JPanel {
    public DashboardPanel() {
        setLayout(new GridBagLayout());
        setOpaque(false);
        add(new JLabel("<html><center><h1 style='color:#A064FF; font-family:Segoe UI Black;'>DO YOUR TASKS.</h1>" +
                "<p style='color:gray;'>Gak usah banyak alibi, kerjain sekarang.</p></center></html>"));
    }
}