package com.emogame.emotionsapplication.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "gamethree")
@NoArgsConstructor
@Getter
@Setter
public class GameThree implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int score;
    private int timeTotal;
    private Date dayplayed;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Usuarios user;
}
