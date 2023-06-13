package lv.martins.homework.repository.entities;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "play_site")
public class PlaySite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany
    @JoinColumn(name = "play_site_id")
    private Set<PlaySiteAttraction> playSiteAttractions;

    @OneToMany
    @JoinColumn(name = "play_site_id")
    private Set<PlaySiteKid> playSiteKids;

    public PlaySite() {
    }

    public PlaySite(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<PlaySiteAttraction> getPlaySiteAttractions() {
        return playSiteAttractions;
    }

    public void setPlaySiteAttractions(Set<PlaySiteAttraction> playSiteAttractions) {
        this.playSiteAttractions = playSiteAttractions;
    }
}
