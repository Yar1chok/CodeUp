package application.repository;

import application.entity.LevelsJava;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LevelsJavaRepository extends JpaRepository<LevelsJava, Long> {
    public List<LevelsJava> findAllByBlockOrderByLevel(Integer block);
}
