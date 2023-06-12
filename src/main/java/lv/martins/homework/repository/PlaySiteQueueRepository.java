package lv.martins.homework.repository;

import lv.martins.homework.repository.entities.PlaySiteQueue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaySiteQueueRepository extends JpaRepository<PlaySiteQueue, Long> {
}
