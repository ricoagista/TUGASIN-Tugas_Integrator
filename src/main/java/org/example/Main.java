package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class Main extends JFrame {

    JTextField txtID, txtMatkul, txtTugas, txtTglDiberikan, txtDeadline;
    JTable table;
    DefaultTableModel model;
    File database = new File("database_tugasin.txt");

    Color bgMain = new Color(244, 246, 249);
    Color headerColor = new Color(44, 62, 80);

    public Main() {
        setTitle("TUGASIN - Task Manager Mahasiswa");
        setSize(900, 550);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(bgMain);

        // ===== HEADER =====
        JLabel lblTitle = new JLabel("TUGASIN", JLabel.CENTER);
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 26));

        JPanel panelHeader = new JPanel();
        panelHeader.setBackground(headerColor);
        panelHeader.add(lblTitle);
        panelHeader.setPreferredSize(new Dimension(0, 60));

        // ===== INPUT PANEL =====
        JPanel panelInput = new JPanel(new GridLayout(5, 2, 10, 10));
        panelInput.setBorder(BorderFactory.createTitledBorder("Input Tugas"));
        panelInput.setBackground(bgMain);

        txtID = new JTextField();
        txtMatkul = new JTextField();
        txtTugas = new JTextField();
        txtTglDiberikan = new JTextField();
        txtDeadline = new JTextField();

        panelInput.add(new JLabel("ID"));
        panelInput.add(txtID);
        panelInput.add(new JLabel("Mata Kuliah"));
        panelInput.add(txtMatkul);
        panelInput.add(new JLabel("Nama Tugas"));
        panelInput.add(txtTugas);
        panelInput.add(new JLabel("Tanggal Diberikan"));
        panelInput.add(txtTglDiberikan);
        panelInput.add(new JLabel("Deadline"));
        panelInput.add(txtDeadline);

        // ===== BUTTON PANEL =====
        JButton btnCreate = createButton("CREATE", new Color(39, 174, 96));
        JButton btnUpdate = createButton("UPDATE", new Color(243, 156, 18));
        JButton btnSelesai = createButton("SELESAI", new Color(41, 128, 185));

        JPanel panelButton = new JPanel();
        panelButton.setBackground(bgMain);
        panelButton.add(btnCreate);
        panelButton.add(btnUpdate);
        panelButton.add(btnSelesai);

        JPanel panelLeft = new JPanel(new BorderLayout());
        panelLeft.setBackground(bgMain);
        panelLeft.add(panelInput, BorderLayout.CENTER);
        panelLeft.add(panelButton, BorderLayout.SOUTH);

        // ===== TABLE =====
        model = new DefaultTableModel(
                new String[]{"ID", "Mata Kuliah", "Tugas", "Diberikan", "Deadline", "Status"}, 0);

        table = new JTable(model);
        table.setRowHeight(25);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        JTableHeader th = table.getTableHeader();
        th.setBackground(new Color(52, 73, 94));
        th.setForeground(Color.WHITE);
        th.setFont(new Font("Segoe UI", Font.BOLD, 13));

        JScrollPane scrollPane = new JScrollPane(table);

        // ===== READ BUTTON =====
        JButton btnBelum = new JButton("📌 Tugas Belum");
        JButton btnSelesaiRead = new JButton("✅ Tugas Selesai");

        JPanel panelRead = new JPanel();
        panelRead.setBackground(bgMain);
        panelRead.add(btnBelum);
        panelRead.add(btnSelesaiRead);

        // ===== ADD COMPONENT =====
        add(panelHeader, BorderLayout.NORTH);
        add(panelLeft, BorderLayout.WEST);
        add(scrollPane, BorderLayout.CENTER);
        add(panelRead, BorderLayout.SOUTH);

        // ===== EVENT =====
        btnCreate.addActionListener(e -> createData());
        btnUpdate.addActionListener(e -> updateData());
        btnSelesai.addActionListener(e -> selesaiData());
        btnBelum.addActionListener(e -> readData("BELUM"));
        btnSelesaiRead.addActionListener(e -> readData("SELESAI"));
    }

    JButton createButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        return btn;
    }

    void createData() {
        try (FileWriter fw = new FileWriter(database, true)) {
            fw.write(txtID.getText() + "|" +
                    txtMatkul.getText() + "|" +
                    txtTugas.getText() + "|" +
                    txtTglDiberikan.getText() + "|" +
                    txtDeadline.getText() + "|BELUM\n");
            JOptionPane.showMessageDialog(this, "Tugas berhasil ditambahkan!");
            clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void readData(String status) {
        model.setRowCount(0);
        try (BufferedReader br = new BufferedReader(new FileReader(database))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split("\\|");
                if (data[5].equals(status)) model.addRow(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void updateData() {
        ArrayList<String> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(database))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] d = line.split("\\|");
                if (d[0].equals(txtID.getText())) {
                    line = txtID.getText() + "|" + txtMatkul.getText() + "|" +
                            txtTugas.getText() + "|" + txtTglDiberikan.getText() + "|" +
                            txtDeadline.getText() + "|BELUM";
                }
                list.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (FileWriter fw = new FileWriter(database)) {
            for (String s : list) fw.write(s + "\n");
            JOptionPane.showMessageDialog(this, "Data berhasil diupdate!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void selesaiData() {
        int row = table.getSelectedRow();
        if (row == -1) return;

        String id = model.getValueAt(row, 0).toString();
        ArrayList<String> list = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(database))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] d = line.split("\\|");
                if (d[0].equals(id)) {
                    d[5] = "SELESAI";
                    line = String.join("|", d);
                }
                list.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (FileWriter fw = new FileWriter(database)) {
            for (String s : list) fw.write(s + "\n");
            JOptionPane.showMessageDialog(this, "Tugas ditandai selesai!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void clear() {
        txtID.setText("");
        txtMatkul.setText("");
        txtTugas.setText("");
        txtTglDiberikan.setText("");
        txtDeadline.setText("");
    }

    public static void main(String[] args) {
        new Main().setVisible(true);
    }
}
