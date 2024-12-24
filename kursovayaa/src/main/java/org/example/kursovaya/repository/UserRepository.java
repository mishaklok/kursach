package org.example.kursovaya.repository;

import org.example.kursovaya.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Репозиторий для работы с {@link User}.
 * Обеспечивает доступ к данным пользователей и предоставляет методы для поиска пользователей по имени или ID.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Ищет пользователя по его идентификатору (ID).
     *
     * @param id Идентификатор пользователя.
     * @return Опционально возвращает пользователя с заданным ID.
     */
    Optional<User> findById(Long id);

    /**
     * Проверяет, существует ли пользователь с заданным адресом электронной почты.
     *
     * Этот метод используется для проверки уникальности email при регистрации пользователя.
     * Возвращает {@code true}, если пользователь с таким email уже существует в базе данных,
     * и {@code false}, если email свободен.
     *
     * @param Username адрес электронной почты пользователя для проверки.
     * @return {@code true}, если email уже существует в базе данных, иначе {@code false}.
     */
    Optional<User> findByUsername(String Username);
}




