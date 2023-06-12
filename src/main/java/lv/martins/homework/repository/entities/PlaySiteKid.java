package lv.martins.homework.repository.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "play_site_kids")
public class PlaySiteKid {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "play_site_id")
    private Long playSiteId;

    @Column(name = "kid_id")
    private Long kidId;

}
