package lv.martins.homework.repository;

import lv.martins.homework.repository.entities.PlaySiteAttraction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaySiteAttractionRepository extends JpaRepository<PlaySiteAttraction, Long> {

    void deleteByPlaySiteId(Long playSiteId);

}
