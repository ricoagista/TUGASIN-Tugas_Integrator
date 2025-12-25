package org.example;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class UIUtils {
    public static JButton styleBtn(String t, Color c) {
        JButton b = new JButton(t);
        b.setBackground(c);
        b.setForeground(Main.COLOR_BG);
        b.setFont(Main.FONT_BOLD);
        b.setBorder(new EmptyBorder(10, 20, 10, 20));
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.setFocusPainted(false);
        return b;
    }
}