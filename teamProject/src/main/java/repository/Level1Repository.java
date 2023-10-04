package repository;

import entity.Level1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Level1Repository extends JpaRepository<Level1, Long> {
}
