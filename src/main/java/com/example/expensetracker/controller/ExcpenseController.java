package com.example.expensetracker.controller;

import com.example.expensetracker.entity.Expense;
import com.example.expensetracker.service.ExpenseService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;

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


    @GetMapping("/expenses/report")
    public Map<Long, BigDecimal> getExpensesReport(
            @RequestParam Long userId,
            @RequestParam YearMonth month
    ) {
        return expenseService.getExpensesReport(userId, month);

    }


}

