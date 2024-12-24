package org.example.kursovaya.repository;

import org.example.kursovaya.model.Exhibit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Репозиторий для работы с {@link Exhibit}.
 * Предоставляет стандартные CRUD-операции и метод для поиска продуктов по ключевому слову.
 */
public interface ExhibitRepository extends JpaRepository<Exhibit, Long> {

    /**
     * Ищет продукты по ключевому слову в полях: название, категория, производитель, дата поставки.
     *
     * @param keyword Ключевое слово для поиска.
     * @return Список найденных продуктов.
     */
    @Query("SELECT p FROM Exhibit p WHERE CONCAT(p.name, ' ', p.category.name, ' ', p.author, ' ', p.deliveryDate) LIKE %?1%")
    List<Exhibit> search(String keyword);
}






