package com.example.expenseit.services;

import com.example.expenseit.models.Category;
import com.example.expenseit.models.Expense;
import com.example.expenseit.repositories.CategoryRepository;
import com.example.expenseit.repositories.ExpenseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class ExpenseService {

    private CategoryRepository categoryRepository;
    private ExpenseRepository expenseRepository;

    public void addNewExpense(int categoryId, int userId, double amount, String note){
        Expense expense = Expense.builder().amount(amount).note(note).transactionDate(LocalDate.now()).build();
        Category category = categoryRepository.findByCategoryIdAndUserId_UserId(categoryId,userId);
        category.getExpenses().add(expense);
        categoryRepository.save(category);
    }

    public List<Expense> getAllExpenses(int categoryId, int userId){
        Category category = categoryRepository.findByCategoryIdAndUserId_UserId(categoryId,userId);
        return category.getExpenses();
    }

    public Expense getExpense(int categoryId, int userId, int expenseId){
        Category category = categoryRepository.findByCategoryIdAndUserId_UserId(categoryId,userId);
        return findExpenseById(category,expenseId);
    }

    public void updateExpense(int categoryId, int userId, int expenseId, double amount, String note){
        Category category = categoryRepository.findByCategoryIdAndUserId_UserId(categoryId,userId);
        Expense expense = findExpenseById(category,expenseId);
        expense.setAmount(amount);
        expense.setNote(note);
        categoryRepository.save(category);
    }

    public void deleteExpense(int categoryId, int userId, int expenseId){
        Category category = categoryRepository.findByCategoryIdAndUserId_UserId(categoryId,userId);
        List<Expense> expenses = category.getExpenses();
        expenses.removeIf(expense -> expense.getExpenseId() == expenseId);
        expenseRepository.deleteByExpenseId(expenseId);
        categoryRepository.save(category);
    }

    private Expense findExpenseById(Category category,int expenseId){
        List<Expense> expenses = category.getExpenses();
        Optional<Expense> matchingObject = expenses.stream().filter(expense -> expense.getExpenseId() == expenseId).findFirst();
        return matchingObject.orElse(null);
    }
}
