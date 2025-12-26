package org.example;

import java.io.*;
import java.util.ArrayList;

public class DataManager {
    public static void loadData(File database, ArrayList<String[]> listData) {
        if (!database.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(database))) {
            String line;
            while ((line = br.readLine()) != null) {
                listData.add(line.split("\\|"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveData(File database, ArrayList<String[]> listData) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(database))) {
            for (String[] r : listData) {
                pw.println(String.join("|", r));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static int getNextID(ArrayList<String[]> listData) {
        int nextID = 1;
        if (!listData.isEmpty()) {
            for (String[] r : listData) {
                nextID = Math.max(nextID, Integer.parseInt(r[0]) + 1);
            }
        }
        return nextID;
    }
}