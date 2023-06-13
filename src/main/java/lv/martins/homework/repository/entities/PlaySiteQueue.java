package lv.martins.homework.repository.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "play_site_queue")
public class PlaySiteQueue {

    @Id
    @GeneratedValue
    public Long id;

    @Column(name = "kid_id")
    public Long kidId;

    @Column(name = "play_site_id")
    public Long playSiteId;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getKidId() {
        return kidId;
    }

    public void setKidId(Long kidId) {
        this.kidId = kidId;
    }

    public Long getPlaySiteId() {
        return playSiteId;
    }

    public void setPlaySiteId(Long playSiteId) {
        this.playSiteId = playSiteId;
    }
}
