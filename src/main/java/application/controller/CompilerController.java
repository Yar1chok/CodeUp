package application.controller;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PathVariable;
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
    private final SimpMessagingTemplate messagingTemplate;
    private Process executionProcess;

    private final String[] notAllowedLibs = {"java.lang.Runtime", "java.io.", "java.lang.ProcessBuilder", "java.util.Scanner", "java.util.zip", "import javax", "import jakarta", "java.nio.file"};

    public CompilerController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @PostMapping("/compile/{username}")
    public void compileCode(@RequestBody String code, @PathVariable String username) throws IOException {
        for (String lib: notAllowedLibs){
            if (code.contains(lib)){
                messagingTemplate.convertAndSend("/topic/compilerOutput/" + username, "Недопустимая библиотека\n");
                return;
            }
        }
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
            messagingTemplate.convertAndSend("/topic/compilerOutput/" + username, "Ошибка компиляции\n");
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
                messagingTemplate.convertAndSend("/topic/compilerOutput/" + username, chunk);
            }   
            try {
                executionProcess.waitFor();
            } catch (InterruptedException e) {
                messagingTemplate.convertAndSend("/topic/compilerOutput/" + username, "Ошибка при выполнении: " + e.getMessage());
            }
        } else {
            messagingTemplate.convertAndSend("/topic/compilerOutput/" + username, "Ошибка компиляции\n");
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
