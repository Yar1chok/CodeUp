package application.service;

import application.entity.JavaTower;
import application.repository.JavaTowerRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class JavaTowerService {
    private final JavaTowerRepository javaTowerRepository;

    public JavaTowerService(JavaTowerRepository javaTowerRepository) {
        this.javaTowerRepository = javaTowerRepository;
    }

    public List<JavaTower> findByLevel(Integer level){
        return javaTowerRepository.findAllByLevel(level);
    }

    public List<JavaTower> getShuffled(Integer level){
        List<JavaTower> javaTowers = javaTowerRepository.findAllByLevel(level);
        javaTowers.forEach(javaTower -> {
            List<String> answers = Arrays.asList(
                    javaTower.getRightAnswer(),
                    javaTower.getWrongAnswer1(),
                    javaTower.getWrongAnswer2(),
                    javaTower.getWrongAnswer3()
            );
            Collections.shuffle(answers);
            javaTower.setShuffledAnswers(answers);
        });
        return javaTowers;
    }

}
