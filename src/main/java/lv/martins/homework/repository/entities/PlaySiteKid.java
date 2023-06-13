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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kid_id", updatable = false, insertable = false)
    private Kid kid;

    @OneToOne
    @JoinColumn(name = "play_site_id", updatable = false, insertable = false)
    private PlaySite playSite;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPlaySiteId() {
        return playSiteId;
    }

    public void setPlaySiteId(Long playSiteId) {
        this.playSiteId = playSiteId;
    }

    public Long getKidId() {
        return kidId;
    }

    public void setKidId(Long kidId) {
        this.kidId = kidId;
    }

    public Kid getKid() {
        return kid;
    }

    public void setKid(Kid kid) {
        this.kid = kid;
    }

    public PlaySite getPlaySite() {
        return playSite;
    }

    public void setPlaySite(PlaySite playSite) {
        this.playSite = playSite;
    }
}
