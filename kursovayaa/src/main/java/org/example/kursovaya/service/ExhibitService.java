package org.example.kursovaya.service;

import org.example.kursovaya.model.Exhibit;
import org.example.kursovaya.repository.ExhibitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Сервис для управления экспонатами.
 * Обеспечивает операции сохранения, получения, удаления экспонатов,
 * а также вычисления статистики по количеству экспонатов по датам поставок.
 */
@Service
public class ExhibitService {

    @Autowired
    private ExhibitRepository repo;

    /**
     * Сохраняет экспонат в базе данных.
     *
     * @param exhibit экспонат для сохранения.
     * @return сохраненный экспонат.
     */
    public Exhibit save(Exhibit exhibit) {
        return repo.save(exhibit);
    }

    /**
     * Получает экспонат по его идентификатору.
     *
     * @param id идентификатор экспоната.
     * @return экспонат с указанным идентификатором, или null, если экспонат не найден.
     */
    public Exhibit get(Long id) {
        return repo.findById(id).orElse(null);
    }

    /**
     * Удаляет экспонат по его идентификатору.
     *
     * @param id идентификатор экспоната, который нужно удалить.
     */
    public void delete(Long id) {
        repo.deleteById(id);
    }

    /**
     * Получает статистику по количеству экспонатов, поставленных в последние 14 дней.
     *
     * @return карта, где ключом является дата поставки, а значением - количество экспонатов, поставленных в эту дату.
     *         Продукты учитываются только за последние 14 дней.
     */
    public Map<LocalDate, Long> getExhibitCountByDeliveryDate() {
        List<Exhibit> allExhibits = repo.findAll();
        Map<LocalDate, Long> countMap = new HashMap<>();

        Set<String> authors = new HashSet<>();

        LocalDate today = LocalDate.now();
        LocalDate startDate = today.minusDays(14);

        for (Exhibit exhibit : allExhibits) {
            LocalDate deliveryDate = exhibit.getDeliveryDate();

            // Учитываем только те экспонаты, которые были поставлены за последние 14 дней
            if (deliveryDate != null && !deliveryDate.isBefore(startDate) && !deliveryDate.isAfter(today)) {
                // Добавляем количество "В наличии" (quantity) к соответствующей дате поставки
                authors.add(exhibit.getAuthor());
                countMap.put(deliveryDate, countMap.getOrDefault(deliveryDate, 0L) + authors.size());
            }
        }

        // Сортировка по дате в порядке возрастания
        return countMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }

}



