package repository;

import entity.Gamer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GamerRepository extends JpaRepository<Gamer, Long> {
    Gamer findGamerByEmail(String email);
}
