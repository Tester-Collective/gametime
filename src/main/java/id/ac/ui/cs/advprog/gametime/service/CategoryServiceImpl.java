package id.ac.ui.cs.advprog.gametime.service;


import id.ac.ui.cs.advprog.gametime.model.Category;
import id.ac.ui.cs.advprog.gametime.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }
}
