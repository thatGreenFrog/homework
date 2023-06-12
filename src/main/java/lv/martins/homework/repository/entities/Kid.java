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

    private Integer age;

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
}
