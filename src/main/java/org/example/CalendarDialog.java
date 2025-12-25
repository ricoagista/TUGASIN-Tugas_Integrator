package org.example;

import javax.swing.*;
import java.awt.*;
import java.time.YearMonth;

public class CalendarDialog extends JDialog {
    private YearMonth cm = YearMonth.now();
    private JPanel pnl;
    private JLabel lbl;
    private JTextField target;

    public CalendarDialog(JFrame p, JTextField t) {
        super(p, "Date", true);
        this.target = t;
        setSize(350, 400);
        setLocationRelativeTo(p);
        getContentPane().setBackground(Main.COLOR_CARD);

        lbl = new JLabel("", JLabel.CENTER);
        lbl.setForeground(Main.COLOR_ACCENT);
        lbl.setFont(Main.FONT_BOLD);

        JButton bP = new JButton("<");
        JButton bN = new JButton(">");
        bP.addActionListener(e -> {
            cm = cm.minusMonths(1);
            update();
        });
        bN.addActionListener(e -> {
            cm = cm.plusMonths(1);
            update();
        });

        JPanel h = new JPanel(new BorderLayout());
        h.setOpaque(false);
        h.add(bP, BorderLayout.WEST);
        h.add(lbl);
        h.add(bN, BorderLayout.EAST);

        pnl = new JPanel(new GridLayout(0, 7, 2, 2));
        pnl.setOpaque(false);

        setLayout(new BorderLayout());
        add(h, BorderLayout.NORTH);
        add(pnl, BorderLayout.CENTER);

        update();
        setVisible(true);
    }

    private void update() {
        pnl.removeAll();
        lbl.setText(cm.getMonth() + " " + cm.getYear());
        String[] ds = {"M", "S", "S", "R", "K", "J", "S"};
        for (String d : ds) {
            JLabel l = new JLabel(d, JLabel.CENTER);
            l.setForeground(Main.COLOR_CYAN);
            pnl.add(l);
        }
        int gap = cm.atDay(1).getDayOfWeek().getValue() % 7;
        for (int i = 0; i < gap; i++) pnl.add(new JLabel(""));
        for (int i = 1; i <= cm.lengthOfMonth(); i++) {
            JButton b = new JButton(String.valueOf(i));
            b.setMargin(new Insets(0, 0, 0, 0));
            int d = i;
            b.addActionListener(e -> {
                target.setText(cm.atDay(d).toString());
                dispose();
            });
            pnl.add(b);
        }
        pnl.revalidate();
        pnl.repaint();
    }
}