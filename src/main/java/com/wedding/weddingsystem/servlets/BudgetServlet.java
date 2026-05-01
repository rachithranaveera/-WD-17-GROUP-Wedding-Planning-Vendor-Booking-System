package com.wedding.weddingsystem.servlets;

import com.wedding.weddingsystem.models.BudgetItem;
import com.wedding.weddingsystem.models.FixedExpense;
import com.wedding.weddingsystem.models.VariableExpense;
import com.wedding.weddingsystem.utils.BudgetFileHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/budget")
public class BudgetServlet {

    private BudgetFileHandler fileHandler = new BudgetFileHandler();

    // READ - View all budget items
    @GetMapping
    public String viewBudget(Model model) throws IOException {
        List<BudgetItem> items = fileHandler.findAll();
        double totalEstimated = items.stream()
                .mapToDouble(BudgetItem::getEstimatedCost).sum();
        double totalActual = items.stream()
                .mapToDouble(BudgetItem::getActualCost).sum();
        model.addAttribute("items", items);
        model.addAttribute("totalEstimated", totalEstimated);
        model.addAttribute("totalActual", totalActual);
        return "budget-summary";
    }

    // CREATE - Show add form
    @GetMapping("/add")
    public String showAddForm() {
        return "add-budget";
    }

    // CREATE - Save new item
    @PostMapping("/add")
    public String addBudgetItem(
            @RequestParam String category,
            @RequestParam double estimatedCost,
            @RequestParam double actualCost,
            @RequestParam boolean isPaid,
            @RequestParam String type) throws IOException {

        String budgetId = UUID.randomUUID().toString()
                .substring(0, 8);
        String coupleId = "1"; // dummy for now

        BudgetItem item;
        if (type.equals("FIXED")) {
            item = new FixedExpense(budgetId, coupleId,
                    category, estimatedCost, actualCost, isPaid);
        } else {
            item = new VariableExpense(budgetId, coupleId,
                    category, estimatedCost, actualCost, isPaid);
        }
        fileHandler.save(item);
        return "redirect:/budget";
    }

    // UPDATE - Show edit form
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable String id,
                               Model model) throws IOException {
        List<BudgetItem> items = fileHandler.findAll();
        BudgetItem found = items.stream()
                .filter(i -> i.getBudgetId().equals(id))
                .findFirst().orElse(null);
        model.addAttribute("item", found);
        return "budget-edit";
    }

    // UPDATE - Save edited item
    @PostMapping("/edit/{id}")
    public String updateBudgetItem(
            @PathVariable String id,
            @RequestParam String category,
            @RequestParam double estimatedCost,
            @RequestParam double actualCost,
            @RequestParam boolean isPaid,
            @RequestParam String type) throws IOException {

        BudgetItem updated;
        if (type.equals("FIXED")) {
            updated = new FixedExpense(id, "1",
                    category, estimatedCost, actualCost, isPaid);
        } else {
            updated = new VariableExpense(id, "1",
                    category, estimatedCost, actualCost, isPaid);
        }
        fileHandler.update(updated);
        return "redirect:/budget";
    }

    // DELETE
    @GetMapping("/delete/{id}")
    public String deleteBudgetItem(
            @PathVariable String id) throws IOException {
        fileHandler.delete(id);
        return "redirect:/budget";
    }
}