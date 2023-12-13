package application.controller;

import application.service.CompilationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CompilationController {

    private final CompilationService compilationService;

    @Autowired
    public CompilationController(CompilationService compilationService) {
        this.compilationService = compilationService;
    }

    @PostMapping("/compile")
    public ResponseEntity<String> compileCode(@RequestBody String code) {
        String result = compilationService.compileCode(code);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
