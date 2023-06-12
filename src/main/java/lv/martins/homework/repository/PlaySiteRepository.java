package lv.martins.homework.repository;

import lv.martins.homework.repository.entities.PlaySite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaySiteRepository extends JpaRepository<PlaySite, Long> {

    PlaySite findByName(String name);

}
