package org.example.kursovaya.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Column;

import java.time.LocalDate;

/**
 * Модель экспоната.
 * <p>
 * Этот класс представляет сущность "Exhibit", которая используется для хранения информации о экспонате в системе аптеки.
 * Каждый экспонат имеет уникальный идентификатор, название, категорию, производителя, цену, количество на складе и дату поставки.
 */
@Entity

public class Exhibit {

    private Long id; // ID экспоната
    private String name; // Название экспоната
    private Category category; // Категория экспоната (ссылка на объект категории)
    private String author; // Автор
    private LocalDate deliveryDate; // Дата поставки

    /**
     * Конструктор по умолчанию.
     * <p>
     * Создает новый объект экспоната без значений для полей.
     */
    public Exhibit() {
    }

    /**
     * Получение идентификатора экспоната.
     *
     * @return Идентификатор экспоната
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Генерация идентификатора через автоинкремент
    public Long getId() {
        return id;
    }

    /**
     * Установка идентификатора экспоната.
     *
     * @param id Идентификатор экспоната
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Получение названия экспоната.
     *
     * @return Название экспоната
     */
    @Column(nullable = false, length = 255) // Указываем обязательность и максимальную длину
    public String getName() {
        return name;
    }

    /**
     * Установка названия экспоната.
     *
     * @param name Название экспоната
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Получение категории экспоната.
     *
     * @return Категория экспоната
     */
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false) // Связь с категорией, обязательность
    public Category getCategory() {
        return category;
    }

    /**
     * Установка категории экспоната.
     *
     * @param category Категория экспоната
     */
    public void setCategory(Category category) {
        this.category = category;
    }

    /**
     * Получение производителя экспоната.
     *
     * @return Производитель экспоната
     */
    @Column(nullable = false, length = 255) // Указываем обязательность и максимальную длину
    public String getAuthor() {
        return author;
    }

    /**
     * Установка производителя экспоната.
     *
     * @param author Производитель экспоната
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Получение даты поставки экспоната.
     *
     * @return Дата поставки
     */
    @Column(nullable = false) // Указываем обязательность
    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    /**
     * Установка даты поставки экспоната.
     *
     * @param deliveryDate Дата поставки
     */
    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }
}



