package org.example.kursovaya.controller;

import org.example.kursovaya.model.Category;
import org.example.kursovaya.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Контроллер для обработки запросов к API, связанных с категориями продуктов.
 * <p>
 * Этот контроллер предоставляет RESTful API для получения всех категорий из базы данных.
 * Запросы на эндпоинт "/api/categories" возвращают список всех категорий.
 */
@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    /**
     * Получает все категории из базы данных.
     *
     * @return список всех категорий, представленных в базе данных
     */
    @GetMapping
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();  // Возвращаем все категории из базы
    }
}