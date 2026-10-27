package com.example.expensetracker.service;

import com.example.expensetracker.entity.Expense;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExpenseService {

    private List<Expense> expenses = new ArrayList<>();
    private final UserService userService;
    private final CategoryService categoryService;

    public ExpenseService(UserService userService, CategoryService categoryService) {
        this.userService = userService;
        this.categoryService = categoryService;
    }

    public List<Expense> getAllExpenses(){
        return expenses;
    }

    public Expense createExpense(Expense expense) {

        if (expense.getAmount() == null || expense.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount can not be zero or negative");
        }

        if (expense.getDate() == null){
            throw new IllegalArgumentException("Date can not be null");
        }

        if (expense.getDate().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Date can not be in the future");
        }

        if (expense.getUserId() == null){
            throw new IllegalArgumentException("No user found for this expense");
        }

        if (expense.getCategoryId() == null){
            throw new IllegalArgumentException("No category found for this expense");
        }

        if (userService.findById(expense.getUserId()) == null){
            throw new IllegalArgumentException("User not found.");
        }

        if (categoryService.findById(expense.getCategoryId())== null){
            throw new IllegalArgumentException("Category not found");
        }






        Expense newExpense = new Expense(expenses.size() + 1L, expense.getAmount(),
                expense.getDate(), expense.getDescription(), expense.getUserId(), expense.getCategoryId());
        expenses.add(newExpense);
        return newExpense;
    }
}
