package com.example.expensetracker.controller;

import com.example.expensetracker.entity.Expense;
import com.example.expensetracker.service.ExpenseService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
public class ExcpenseController {
    private ExpenseService expenseService;

    public ExcpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping("/expenses")
    public List<Expense> getAllExpenses() {
        return expenseService.getAllExpenses();
    }

    @PostMapping("/expenses")
    public Expense createExpense(@RequestBody Expense expense) {
        return expenseService.createExpense(expense);
    }

    @GetMapping("/expenses/period")
    public List<Expense> getByUserAndPeriod(
            @RequestParam Long userId,
            @RequestParam LocalDate from,
            @RequestParam LocalDate to) {

        return expenseService.getByUserAndPeriod(userId, from, to);

    }

}

