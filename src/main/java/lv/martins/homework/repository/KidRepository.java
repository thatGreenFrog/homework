package lv.martins.homework.repository;

import lv.martins.homework.repository.entities.Kid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface KidRepository extends JpaRepository<Kid, Long> {

    Kid findByTicketNumber(String ticketNumber);

    @Query("select spotInQueue from Kid order by spotInQueue desc limit 1")
    Integer findNextSpotInQueue();

    Kid findFirstByOrderBySpotInQueueAsc();

}
