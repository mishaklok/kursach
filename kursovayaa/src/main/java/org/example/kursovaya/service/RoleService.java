package org.example.kursovaya.service;

import org.example.kursovaya.model.Role;
import org.example.kursovaya.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Сервис для работы с ролями пользователей.
 * Предоставляет методы для получения информации о ролях.
 */
@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    /**
     * Получает роль по идентификатору.
     *
     * @param id Идентификатор роли.
     * @return Роль с заданным идентификатором.
     */
    public Role get(Long id) {
        return roleRepository.findById(id).get();
    }
}

