package com.example.expenseit.services;

import com.example.expenseit.models.Category;
import com.example.expenseit.models.Client;
import com.example.expenseit.repositories.CategoryRepository;
import com.example.expenseit.repositories.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class CategoryService {

    CategoryRepository categoryRepository;
    ClientRepository clientRepository;

    public List<Category> getAllCategories(int clientId){
        return categoryRepository.findAllByUserId_UserId(clientId);
    }

    public Category getCategory(int clientId, int categoryId){
        return categoryRepository.findByCategoryIdAndUserId_UserId(categoryId,clientId);
    }

    public Category addCategory(int clientId, String title, String description){

        Client client = clientRepository.findByUserId(clientId);
        Category category = Category.builder().title(title).description(description).userId(client).build();
        return categoryRepository.save(category);
    }

    public Category deleteCategoryWithAllTransactions(int clientId, int categoryId){
        return categoryRepository.deleteCategoryByCategoryIdAndUserId_UserId(categoryId,clientId);
    }

    public Category updateCategory(int clientId, int categoryId, String title, String description) {
        Category category = categoryRepository.findByCategoryIdAndUserId_UserId(categoryId,clientId);
        category.setDescription(description);
        category.setTitle(title);
        return categoryRepository.save(category);
    }
}
