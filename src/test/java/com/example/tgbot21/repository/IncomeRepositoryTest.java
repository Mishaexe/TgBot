package com.example.tgbot21.repository;

import com.example.tgbot21.repository.IncomeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
// Hibernate создаст таблицу income
// Этот SQL выполнится ПОСЛЕ создания схемы Hibernate
@Sql(statements = "INSERT INTO income (id, chat_id, income) VALUES (12345, 2, 3000)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(statements = "DELETE FROM income", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD) // Очистка
class IncomeRepositoryTest {

    @Autowired
    private IncomeRepository repository;

    @Test
    void testDataScripts() {
        // Проверяем, что запись существует
        boolean exists = repository.existsById(12345L);
        assertTrue(exists); // Теперь должно быть true
    }
}