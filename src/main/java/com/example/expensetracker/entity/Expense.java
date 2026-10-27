package com.example.expensetracker.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Expense {
    private Long id;
    private BigDecimal amount;
    private LocalDate date;
    private String description;
    private Long userId;
    private Long categoryId;

    public Expense(Long id, BigDecimal amount, LocalDate date, String description, Long userId, Long categoryId) {
        this.id = id;
        this.amount = amount;
        this.date = date;
        this.description = description;
        this.userId = userId;
        this.categoryId = categoryId;
    }

    public Expense(){}

    public Long getId() {
        return id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getCategoryId() {
        return categoryId;
    }
}
