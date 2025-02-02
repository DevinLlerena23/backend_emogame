package com.emogame.emotionsapplication.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "gameone")
@NoArgsConstructor
@Getter
@Setter
public class GameOne implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int score;
    private Date dayplayed;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Usuarios user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Date getDayplayed() {
        return dayplayed;
    }

    public void setDayplayed(Date dayplayed) {
        this.dayplayed = dayplayed;
    }

    public Usuarios getUser() {
        return user;
    }

    public void setUser(Usuarios user) {
        this.user = user;
    }
}
