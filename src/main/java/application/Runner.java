package application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

/**
 * Класс запуска программы
 */
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class Runner {
    /**
     * Запуск программы
     * @param args аргументы запуска
     */
    public static void main(String[] args) {
        SpringApplication.run(Runner.class, args);
    }
}