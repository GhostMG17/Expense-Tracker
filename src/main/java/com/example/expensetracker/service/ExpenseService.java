package com.example.expensetracker.service;

import com.example.expensetracker.entity.Expense;
import com.example.expensetracker.exception.BadRequestException;
import com.example.expensetracker.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ExpenseService {

    private List<Expense> expenses = new ArrayList<>();
    private final UserService userService;
    private final CategoryService categoryService;

    public ExpenseService(UserService userService, CategoryService categoryService) {
        this.userService = userService;
        this.categoryService = categoryService;
    }

    public List<Expense> getAllExpenses() {
        return expenses;
    }

    public Expense createExpense(Expense expense) {

        if (expense.getAmount() == null || expense.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException("Amount can not be zero or negative");
        }

        if (expense.getDate() == null) {
            throw new BadRequestException("Date can not be null");
        }

        if (expense.getDate().isAfter(LocalDate.now())) {
            throw new BadRequestException("Date can not be in the future");
        }

        if (expense.getUserId() == null) {
            throw new NotFoundException("No user found for this expense");
        }

        if (expense.getCategoryId() == null) {
            throw new NotFoundException("No category found for this expense");
        }

        userService.findById(expense.getUserId());
        categoryService.findById(expense.getCategoryId());

        Expense newExpense = new Expense(expenses.size() + 1L, expense.getAmount(),
                expense.getDate(), expense.getDescription(), expense.getUserId(), expense.getCategoryId());
        expenses.add(newExpense);
        return newExpense;
    }

    public List<Expense> getByUserAndPeriod(Long userId, LocalDate from, LocalDate to) {

        if (userId == null) {
            throw new BadRequestException("User id cannot be null.");
        }

        userService.findById(userId);

        if (from == null || to == null) {
            throw new BadRequestException("Date should be filled.");
        }

        if (from.isAfter(to)) {
            throw new BadRequestException("From must not be after to.");
        }

        return expenses.stream()
                .filter(u -> Objects.equals(u.getUserId(), userId)
                        && !u.getDate().isBefore(from)
                        && !u.getDate().isAfter(to))
                .collect(Collectors.toList());
    }

    public Map<Long, BigDecimal> getExpensesReport(Long userId, YearMonth month) {
        if (month == null){
            throw new BadRequestException("Month cannot be null.");
        }

        LocalDate from = month.atDay(1);
        LocalDate to = month.atEndOfMonth();
        List<Expense> expenseList = getByUserAndPeriod(userId, from, to);

        Map<Long, BigDecimal> report = new HashMap<>();

        for (Expense expense : expenseList) {
            Long catId = expense.getCategoryId();
            BigDecimal amount = expense.getAmount();

            BigDecimal currentAmount = report.get(catId);

            if (currentAmount == null) {
                currentAmount = BigDecimal.ZERO;
            }

            BigDecimal result = amount.add(currentAmount);

            report.put(catId, result);
        }

        return report;

    }

}
