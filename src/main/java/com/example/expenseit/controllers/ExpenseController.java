package com.example.expenseit.controllers;

import com.example.expenseit.models.Expense;
import com.example.expenseit.services.ExpenseService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/categories/{categoryId}/expenses")
public class ExpenseController {

    private ExpenseService expenseService;

    @PostMapping
    public ResponseEntity<Void> addNewExpense(@PathVariable int categoryId,
                                                 @RequestBody Map<String,String> map,
                                                 HttpServletRequest request){
        int clientId = (int)request.getAttribute("clientId");
        expenseService.addNewExpense(categoryId,clientId,Double.parseDouble(map.get("amount")),map.get("note"));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Expense>> getAllExpenses(@PathVariable int categoryId,
                                                        HttpServletRequest request){
        int clientId = (int)request.getAttribute("clientId");
        List<Expense> expenses = expenseService.getAllExpenses(categoryId,clientId);
        return new ResponseEntity<>(expenses,HttpStatus.OK);
    }

    @GetMapping("/{expenseId}")
    public ResponseEntity<Expense> getExpense(@PathVariable int categoryId,
                                              @PathVariable int expenseId,
                                              HttpServletRequest request){
        int clientId = (int)request.getAttribute("clientId");
        Expense expense = expenseService.getExpense(categoryId,clientId,expenseId);
        return new ResponseEntity<>(expense, HttpStatus.OK);
    }

    @DeleteMapping("/{expenseId}")
    public ResponseEntity<Void> deleteExpense(@PathVariable int categoryId,
                                                 @PathVariable int expenseId,
                                                 HttpServletRequest request){
        int clientId = (int)request.getAttribute("clientId");
        expenseService.deleteExpense(categoryId,clientId,expenseId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{expenseId}")
    public ResponseEntity<Void> updateExpense(@PathVariable int categoryId,
                                                 @PathVariable int expenseId,
                                                 HttpServletRequest request,
                                                 @RequestBody Map<String,String> map) {
        int clientId = (int)request.getAttribute("clientId");
        expenseService.updateExpense(categoryId,clientId,expenseId,
                Double.parseDouble(map.get("amount")),map.get("note"));
        return new ResponseEntity<>(HttpStatus.OK);

    }
}
