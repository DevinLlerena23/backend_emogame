package com.emogame.emotionsapplication.Controller;

import com.emogame.emotionsapplication.Entity.GameOne;
import com.emogame.emotionsapplication.Entity.GameThree;
import com.emogame.emotionsapplication.Entity.GameTwo;
import com.emogame.emotionsapplication.Repository.GameOneRepository;
import com.emogame.emotionsapplication.Repository.GameThreeRepository;
import com.emogame.emotionsapplication.Repository.GameTwoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/games")
@CrossOrigin(origins = "*") // Habilitar CORS para todas las peticiones
public class GamesController {
    @Autowired
    private GameOneRepository gameOneRepository;

    @Autowired
    private GameTwoRepository gameTwoRepository;

    @Autowired
    private GameThreeRepository gameThreeRepository;

    // CRUD para GameOne

    @GetMapping("/gameone")
    public List<GameOne> getAllGameOne() {
        return gameOneRepository.findAll();
    }

    @GetMapping("/gameone/{id}")
    public ResponseEntity<GameOne> getGameOneById(@PathVariable Integer id) {
        return gameOneRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/gameone")
    public GameOne createGameOne(@RequestBody GameOne gameOne) {
        return gameOneRepository.save(gameOne);
    }

    @PutMapping("/gameone/{id}")
    public ResponseEntity<GameOne> updateGameOne(@PathVariable Integer id, @RequestBody GameOne gameOne) {
        return gameOneRepository.findById(id).map(existing -> {
            existing.setScore(gameOne.getScore());
            existing.setDayplayed(gameOne.getDayplayed());
            existing.setUser(gameOne.getUser());
            return ResponseEntity.ok(gameOneRepository.save(existing));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/gameone/{id}")
    public ResponseEntity<Object> deleteGameOne(@PathVariable Integer id) {
        return gameOneRepository.findById(id).map(existing -> {
            gameOneRepository.delete(existing);
            return ResponseEntity.noContent().build();
        }).orElse(ResponseEntity.notFound().build());
    }

    // CRUD para GameTwo

    @GetMapping("/gametwo")
    public List<GameTwo> getAllGameTwo() {
        return gameTwoRepository.findAll();
    }

    @GetMapping("/gametwo/{id}")
    public ResponseEntity<GameTwo> getGameTwoById(@PathVariable Integer id) {
        return gameTwoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/gametwo")
    public GameTwo createGameTwo(@RequestBody GameTwo gameTwo) {
        return gameTwoRepository.save(gameTwo);
    }

    @PutMapping("/gametwo/{id}")
    public ResponseEntity<GameTwo> updateGameTwo(@PathVariable Integer id, @RequestBody GameTwo gameTwo) {
        return gameTwoRepository.findById(id).map(existing -> {
            existing.setTimeLevelOne(gameTwo.getTimeLevelOne());
            existing.setTimeLevelTwo(gameTwo.getTimeLevelTwo());
            existing.setTimeTotal(gameTwo.getTimeTotal());
            existing.setDayplayed(gameTwo.getDayplayed());
            existing.setUser(gameTwo.getUser());
            return ResponseEntity.ok(gameTwoRepository.save(existing));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/gametwo/{id}")
    public ResponseEntity<Object> deleteGameTwo(@PathVariable Integer id) {
        return gameTwoRepository.findById(id).map(existing -> {
            gameTwoRepository.delete(existing);
            return ResponseEntity.noContent().build();
        }).orElse(ResponseEntity.notFound().build());
    }

    // CRUD para GameThree

    @GetMapping("/gamethree")
    public List<GameThree> getAllGameThree() {
        return gameThreeRepository.findAll();
    }

    @GetMapping("/gamethree/{id}")
    public ResponseEntity<GameThree> getGameThreeById(@PathVariable Integer id) {
        return gameThreeRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/gamethree")
    public GameThree createGameThree(@RequestBody GameThree gameThree) {
        return gameThreeRepository.save(gameThree);
    }

    @PutMapping("/gamethree/{id}")
    public ResponseEntity<GameThree> updateGameThree(@PathVariable Integer id, @RequestBody GameThree gameThree) {
        return gameThreeRepository.findById(id).map(existing -> {
            existing.setScore(gameThree.getScore());
            existing.setTimeTotal(gameThree.getTimeTotal());
            existing.setDayplayed(gameThree.getDayplayed());
            existing.setUser(gameThree.getUser());
            return ResponseEntity.ok(gameThreeRepository.save(existing));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/gamethree/{id}")
    public ResponseEntity<Object> deleteGameThree(@PathVariable Integer id) {
        return gameThreeRepository.findById(id).map(existing -> {
            gameThreeRepository.delete(existing);
            return ResponseEntity.noContent().build();
        }).orElse(ResponseEntity.notFound().build());
    }

}
