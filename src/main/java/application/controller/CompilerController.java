package application.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;


@RestController
public class CompilerController {
    @PostMapping("/compile")
    public String compileCode(@RequestBody String code) throws IOException, InterruptedException {
        try (FileWriter writer = new FileWriter("Main.java")) {
            writer.write(code);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ProcessBuilder processBuilder = new ProcessBuilder("javac", "Main.java");
        Process process = processBuilder.start();
        try {
            process.waitFor();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return "Ошибка при компиляции: " + e.getMessage();
        }

        if (process.exitValue() == 0) {
            ProcessBuilder executionProcessBuilder = new ProcessBuilder("java", "Main");

            Process executionProcess = executionProcessBuilder.start();

            String executionResult = new String(executionProcess.getInputStream().readAllBytes());
            executionResult += new String(executionProcess.getErrorStream().readAllBytes());
            try {
                executionProcess.waitFor();
            } catch (InterruptedException e) {
                return "Ошибка при выполнении: " + e.getMessage();
            }

            return executionResult;
        } else {
            return "Ошибка при компиляции";
        }
    }
}