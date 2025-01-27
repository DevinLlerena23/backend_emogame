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
}
