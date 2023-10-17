package springClasses;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Класс запуска программы
 */
@SpringBootApplication
public class Runner {
    /**
     * Запуск программы
     * @param args аргументы запуска
     */
    public static void main(String[] args) {
        SpringApplication.run(Runner.class, args);
    }
}