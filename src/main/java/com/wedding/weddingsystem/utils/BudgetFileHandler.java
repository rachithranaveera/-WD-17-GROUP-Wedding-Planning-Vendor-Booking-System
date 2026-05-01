package com.wedding.weddingsystem.utils;

import com.wedding.weddingsystem.models.BudgetItem;
import com.wedding.weddingsystem.models.FixedExpense;
import com.wedding.weddingsystem.models.VariableExpense;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BudgetFileHandler {

    private static final String FILE_PATH =
            "src/main/resources/data/budget.txt";

    // CREATE - Add new budget item
    public void save(BudgetItem item) throws IOException {
        BufferedWriter writer = new BufferedWriter(
                new FileWriter(FILE_PATH, true));
        writer.write(item.toString());
        writer.newLine();
        writer.close();
    }

    // READ - Get all budget items
    public List<BudgetItem> findAll() throws IOException {
        List<BudgetItem> items = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) return items;

        BufferedReader reader = new BufferedReader(
                new FileReader(FILE_PATH));
        String line;
        while ((line = reader.readLine()) != null) {
            if (!line.trim().isEmpty()) {
                String[] parts = line.split(",");
                if (parts[6].equals("FIXED")) {
                    items.add(new FixedExpense(
                            parts[0], parts[1], parts[2],
                            Double.parseDouble(parts[3]),
                            Double.parseDouble(parts[4]),
                            Boolean.parseBoolean(parts[5])));
                } else {
                    items.add(new VariableExpense(
                            parts[0], parts[1], parts[2],
                            Double.parseDouble(parts[3]),
                            Double.parseDouble(parts[4]),
                            Boolean.parseBoolean(parts[5])));
                }
            }
        }
        reader.close();
        return items;
    }

    // UPDATE - Update budget item
    public void update(BudgetItem updated) throws IOException {
        List<BudgetItem> items = findAll();
        BufferedWriter writer = new BufferedWriter(
                new FileWriter(FILE_PATH, false));
        for (BudgetItem item : items) {
            if (item.getBudgetId().equals(updated.getBudgetId())) {
                writer.write(updated.toString());
            } else {
                writer.write(item.toString());
            }
            writer.newLine();
        }
        writer.close();
    }

    // DELETE - Delete budget item
    public void delete(String budgetId) throws IOException {
        List<BudgetItem> items = findAll();
        BufferedWriter writer = new BufferedWriter(
                new FileWriter(FILE_PATH, false));
        for (BudgetItem item : items) {
            if (!item.getBudgetId().equals(budgetId)) {
                writer.write(item.toString());
                writer.newLine();
            }
        }
        writer.close();
    }
}