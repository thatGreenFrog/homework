package lv.martins.homework.repository;

import lv.martins.homework.repository.entities.Kid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KidRepository extends JpaRepository<Kid, Long> {
}
