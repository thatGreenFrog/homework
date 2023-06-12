package lv.martins.homework.repository.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "play_site_attractions")
public class PlaySiteAttraction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;

    private Integer size;

    @Column(name = "play_site_id")
    private Long playSiteId;

    @ManyToOne
    @JoinColumn(name = "play_site_id", insertable = false, updatable = false)
    private PlaySite playSite;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Long getPlaySiteId() {
        return playSiteId;
    }

    public void setPlaySiteId(Long playSiteId) {
        this.playSiteId = playSiteId;
    }

    public PlaySite getPlaySite() {
        return playSite;
    }

    public void setPlaySite(PlaySite playSite) {
        this.playSite = playSite;
    }
}
