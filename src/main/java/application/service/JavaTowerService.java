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

    public List<JavaTower> findAllByBlockAndLevel(Integer block, Integer level){
        return javaTowerRepository.findAllByBlockAndLevel(block, level);
    }

    public List<JavaTower> getShuffled(Integer block, Integer level){
        List<JavaTower> javaTowers = javaTowerRepository.findAllByBlockAndLevel(block, level);
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

    public boolean existByBlockAndLevel(Integer block, Integer level){
        return javaTowerRepository.existsByBlockAndLevel(block, level);
    }
    public boolean existByBlock(Integer block){
        return javaTowerRepository.existsByBlock(block);
    }

}
