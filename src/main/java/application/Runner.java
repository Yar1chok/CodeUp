package application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Класс запуска программы
 */
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@EnableJpaRepositories(value = "application.repository")
public class Runner {
    /**
     * Запуск программы
     *
     * @param args аргументы запуска
     */
    public static void main(String[] args) {
        SpringApplication.run(Runner.class, args);
    }
}