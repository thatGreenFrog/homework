package lv.martins.homework.repository.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "kid")
public class Kid {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Column(name = "ticket_number")
    private String ticketNumber;

    @Column(name = "play_site_id")
    private Long playSiteId;

    @Column(name = "spot_in_queue")
    private Integer spotInQueue;

    private Integer age;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "play_site_id", insertable = false, updatable = false)
    private PlaySite playSite;

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

    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Long getPlaySiteId() {
        return playSiteId;
    }

    public void setPlaySiteId(Long playSiteId) {
        this.playSiteId = playSiteId;
    }

    public Integer getSpotInQueue() {
        return spotInQueue;
    }

    public void setSpotInQueue(Integer spotInQueue) {
        this.spotInQueue = spotInQueue;
    }

    public PlaySite getPlaySite() {
        return playSite;
    }

    public void setPlaySite(PlaySite playSite) {
        this.playSite = playSite;
    }

}
