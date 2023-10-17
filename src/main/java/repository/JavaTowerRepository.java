package repository;

import entity.JavaTower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JavaTowerRepository extends JpaRepository<JavaTower, Long> {
}
