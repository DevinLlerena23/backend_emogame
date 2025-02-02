package com.emogame.emotionsapplication.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "gametwo")
@NoArgsConstructor
@Getter
@Setter
public class GameTwo implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int timeLevelOne;
    private int timeLevelTwo;
    private int timeTotal;
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

    public int getTimeLevelOne() {
        return timeLevelOne;
    }

    public void setTimeLevelOne(int timeLevelOne) {
        this.timeLevelOne = timeLevelOne;
    }

    public int getTimeLevelTwo() {
        return timeLevelTwo;
    }

    public void setTimeLevelTwo(int timeLevelTwo) {
        this.timeLevelTwo = timeLevelTwo;
    }

    public int getTimeTotal() {
        return timeTotal;
    }

    public void setTimeTotal(int timeTotal) {
        this.timeTotal = timeTotal;
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
