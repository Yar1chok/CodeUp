package application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
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
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    Process executionProcess;

    @PostMapping("/compile")
    @SendTo("/topic/greetings")
    public void compileCode(@RequestBody String code) throws IOException, InterruptedException {
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
            messagingTemplate.convertAndSend("/topic/greetings", "Ошибка компиляции\n");
        }

        if (process.exitValue() == 0) {
            ProcessBuilder executionProcessBuilder = new ProcessBuilder("java", "Main");

            processBuilder.redirectErrorStream(true);

            executionProcess = executionProcessBuilder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(executionProcess.getInputStream()));
            int charRead;
            char[] buffer = new char[10];

            while ((charRead = reader.read(buffer)) != -1) {
                String chunk = new String(buffer, 0, charRead);
                messagingTemplate.convertAndSend("/topic/greetings", chunk);
            }   
            try {
                executionProcess.waitFor();
            } catch (InterruptedException e) {
                messagingTemplate.convertAndSend("/topic/greetings", "Ошибка при выполнении: " + e.getMessage());
            }
        } else {
            messagingTemplate.convertAndSend("/topic/greetings", "Ошибка компиляции\n");
        }
    }
    @PostMapping("/input")
    public void Input(@RequestBody String input) throws IOException {
        OutputStream outputStream = executionProcess.getOutputStream();
        outputStream.write(input.getBytes());
        outputStream.flush();

        outputStream.close();
    }
}
