package org.example.kursovaya.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

/**
 * Модель пользователя системы.
 * <p>
 * Этот класс представляет сущность "User", которая используется для хранения информации о пользователях в системе выставочного центра.
 * Пользователь может иметь несколько ролей, таких как ADMIN или USER, которые определяют его доступ к различным функциям системы.
 */
@Data
@Entity
@Table(name = "user_model")
public class User {

    /**
     * Идентификатор пользователя.
     * <p>
     * Идентификатор генерируется автоматически с использованием автоинкремента.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Имя пользователя (логин) для входа в систему.
     * <p>
     * Имя пользователя должно быть уникальным и не может быть пустым.
     */
    @Column(nullable = false, unique = true)
    private String username;

    /**
     * Пароль пользователя.
     * <p>
     * Пароль не может быть пустым.
     */
    @Column(nullable = false)
    private String password;

    /**
     * Роли, присвоенные пользователю.
     * <p>
     * Пользователь может иметь несколько ролей. Роли определяют права доступа пользователя в системе.
     * Связь с ролями реализована через таблицу `user_roles`, в которой каждый пользователь может иметь несколько ролей.
     * Связь с ролями загружается немедленно (EAGER), и используется каскадирование при сохранении или обновлении пользователя.
     */
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();
}
