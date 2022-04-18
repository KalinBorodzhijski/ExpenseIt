package com.example.expenseit.repositories;

import com.example.expenseit.models.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense,Integer> {

    void deleteByExpenseId(int expenseId);
}
