package org.example.kursovaya.service;

import org.example.kursovaya.model.Category;
import org.example.kursovaya.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Сервис для работы с категориями экспонатов.
 * Обеспечивает бизнес-логику для работы с сущностью {@link Category} и взаимодействует с {@link CategoryRepository}.
 */
@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    /**
     * Получает все категории экспонатов.
     *
     * @return Список всех категорий.
     */
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    /**
     * Сохраняет новую категорию или обновляет существующую.
     *
     * @param category Категория для сохранения.
     * @return Сохраненная категория.
     */
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    /**
     * Находит категорию по ее идентификатору.
     *
     * @param id Идентификатор категории.
     * @return Категория с заданным ID, или {@code null}, если категория не найдена.
     */
    public Category findById(Long id) {
        return categoryRepository.findById(id).orElse(null); // Если категория не найдена, возвращаем null
    }
}
