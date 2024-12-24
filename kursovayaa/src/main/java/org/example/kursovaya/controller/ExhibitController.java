package org.example.kursovaya.controller;

import org.example.kursovaya.model.Category;
import org.example.kursovaya.model.Exhibit;
import org.example.kursovaya.repository.ExhibitRepository;
import org.example.kursovaya.service.CategoryService;
import org.example.kursovaya.service.ExhibitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Контроллер для работы с экспонатами в системе.
 * <p>
 * Этот контроллер предоставляет RESTful API для управления экспонатами, включая операции добавления,
 * обновления, удаления, получения данных о экспонате, а также получения гистограммы по датам поставок.
 */
@RestController
@RequestMapping("/api/exhibits")
public class ExhibitController {

    @Autowired
    private ExhibitService exhibitService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ExhibitRepository exhibitRepository;

    /**
     * Получение списка экспонатов с возможностью фильтрации по ключевому слову.
     * <p>
     * Если параметр "keyword" не передан или пуст, возвращаются все экспонаты.
     * В противном случае выполняется поиск по ключевому слову.
     *
     * @param keyword ключевое слово для фильтрации экспонатов (необязательный параметр)
     * @return ResponseEntity, содержащий список экспонатов
     */
    @GetMapping
    public ResponseEntity<List<Exhibit>> getProducts(@RequestParam(value = "keyword", required = false) String keyword) {
        List<Exhibit> exhibits;
        if (keyword == null || keyword.isEmpty()) {
            exhibits = exhibitRepository.findAll();
        } else {
            exhibits = exhibitRepository.search(keyword);
        }
        return ResponseEntity.ok(exhibits);
    }

    /**
     * Получение данных о экспонате по его идентификатору.
     * <p>
     * Если экспонат с указанным идентификатором найден, возвращается его информация.
     * Если экспонат не найден, возвращается ошибка 404 (Not Found).
     *
     * @param id идентификатор экспоната
     * @return ResponseEntity, содержащий данные экспоната или статус 404, если экспонат не найден
     */
    @GetMapping("/{id}")
    public ResponseEntity<Exhibit> getProduct(@PathVariable Long id) {
        Exhibit exhibit = exhibitService.get(id);
        return exhibit != null ? ResponseEntity.ok(exhibit) : ResponseEntity.notFound().build();
    }

    /**
     * Добавление нового экспоната в систему.
     * <p>
     * Если у экспоната указана новая категория (без идентификатора), она сохраняется в базе данных,
     * а затем экспонат сохраняется с привязанной категорией.
     *
     * @param exhibit объект экспоната, который нужно добавить
     * @return ResponseEntity, содержащий добавленный экспонат и статус 201 (Created), или ошибку 500 при проблемах
     */
    @PostMapping
    public ResponseEntity<Exhibit> addProduct(@RequestBody Exhibit exhibit) {
        try {
            Category category = exhibit.getCategory();
            if (category != null && category.getId() == null) {
                category = categoryService.save(category);
                exhibit.setCategory(category);
            }
            Exhibit savedExhibit = exhibitService.save(exhibit);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedExhibit);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Обновление информации об экспонате.
     * <p>
     * Если экспонат с указанным идентификатором найден, его данные обновляются.
     * В противном случае возвращается ошибка 404 (Not Found).
     *
     * @param id идентификатор экспоната, который нужно обновить
     * @param exhibit объект с новыми данными для обновления
     * @return ResponseEntity, содержащий обновленный экспонат или статус 404, если экспонат не найден
     */
    @PutMapping("/{id}")
    public ResponseEntity<Exhibit> updateProduct(@PathVariable Long id, @RequestBody Exhibit exhibit) {
        Exhibit existingExhibit = exhibitService.get(id);
        if (existingExhibit == null) {
            return ResponseEntity.notFound().build();
        }

        existingExhibit.setName(exhibit.getName());
        existingExhibit.setAuthor(exhibit.getAuthor());
        existingExhibit.setDeliveryDate(exhibit.getDeliveryDate());

        if (exhibit.getCategory() != null) {
            Category category = exhibit.getCategory();
            if (category.getId() == null) {
                category = categoryService.save(category);
            } else {
                category = categoryService.findById(category.getId());
            }
            existingExhibit.setCategory(category);
        }

        exhibitService.save(existingExhibit);
        return ResponseEntity.ok(existingExhibit);
    }

    /**
     * Удаление экспоната из системы.
     * <p>
     * Продукт с указанным идентификатором удаляется из базы данных.
     * Если экспонат найден и успешно удалён, возвращается статус 204 (No Content).
     *
     * @param id идентификатор экспоната, который нужно удалить
     * @return ResponseEntity с кодом ответа 204 (No Content)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        exhibitService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Получение данных для отображения гистограммы по датам поставок экспонатов.
     * <p>
     * Возвращает количество экспонатов, поставленных в каждый день, в виде карты с датами и количеством.
     *
     * @return карта, где ключ — это дата поставки, а значение — количество экспонатов, поставленных в этот день
     */
    @GetMapping("/histogram")
    public Map<LocalDate, Long> getHistogramData() {
        return exhibitService.getExhibitCountByDeliveryDate();
    }
}
