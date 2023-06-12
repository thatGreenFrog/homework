package lv.martins.homework.repository;

import lv.martins.homework.repository.entities.PlaySiteKid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaySiteKidRepository extends JpaRepository<PlaySiteKid, Long> {
}
