package org.example.kursovaya;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Главный класс приложения для старта Spring Boot приложения.
 * Включает аннотацию {@link SpringBootApplication}, которая активирует автоконфигурацию Spring Boot
 * и сканирование компонентов.
 */
@SpringBootApplication
public class Kursach {

    /**
     * Точка входа в приложение.
     * Метод запускает Spring Boot приложение.
     *
     * @param args аргументы командной строки, передаваемые при запуске приложения
     */
    public static void main(String[] args) {
        SpringApplication.run(Kursach.class, args);
    }
}

