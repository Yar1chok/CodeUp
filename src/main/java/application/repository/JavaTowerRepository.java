package application.repository;

import application.entity.JavaTower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JavaTowerRepository extends JpaRepository<JavaTower, Long> {
    List<JavaTower> findAllByLevel(Integer level);
}
