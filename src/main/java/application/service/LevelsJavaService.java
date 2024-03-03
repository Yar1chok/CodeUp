package application.service;

import application.entity.LevelsJava;
import application.repository.LevelsJavaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LevelsJavaService {
    private final LevelsJavaRepository levelsJavaRepository;

    public LevelsJavaService(LevelsJavaRepository levelsJavaRepository) {
        this.levelsJavaRepository = levelsJavaRepository;
    }

    public List<LevelsJava> findAllByBlock(Integer block){
        return levelsJavaRepository.findAllByBlockOrderByLevel(block);
    }
}
