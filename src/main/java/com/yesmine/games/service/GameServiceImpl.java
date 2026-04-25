package com.yesmine.games.service;

import com.yesmine.games.entities.Game;
import com.yesmine.games.entities.Image;
import com.yesmine.games.entities.Type;
import com.yesmine.games.repos.GameRepository;
import com.yesmine.games.repos.ImageRepository;
import com.yesmine.games.repos.TypeRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameServiceImpl implements GameService {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private TypeRepository typeRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Override
    public Game saveGame(Game game) {
        if (game.getType() != null && game.getType().getIdType() != null) {
            Type managedType = typeRepository.findById(game.getType().getIdType())
                    .orElseThrow(() -> new RuntimeException("Type not found"));
            game.setType(managedType);
        }
        return gameRepository.save(game);
    }

    @Override
    public Game updateGame(Game game) {
        Game existingGame = gameRepository.findById(game.getIdGame())
                .orElseThrow(() -> new RuntimeException("Game not found"));

        existingGame.setNomGame(game.getNomGame());
        existingGame.setPrixGame(game.getPrixGame());
        existingGame.setDateCreation(game.getDateCreation());

        // ✅ update type
        if (game.getType() != null && game.getType().getIdType() != null) {
            Type managedType = typeRepository.findById(game.getType().getIdType())
                    .orElseThrow(() -> new RuntimeException("Type not found"));
            existingGame.setType(managedType);
        } else {
            existingGame.setType(null);
        }

        // ✅ no image update here — images are managed separately via ImageService
        // images are linked to game via @ManyToOne in Image entity

        return gameRepository.save(existingGame);
    }

    @Override
    public void deleteGame(Game game) {
        gameRepository.delete(game);
    }

    @Override
    public Game getGame(Long id) {
        return gameRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Game not found"));
    }

    @Override
    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    @Override
    public List<Game> findByNomGame(String nom) {
        return gameRepository.findByNomGame(nom);
    }

    @Override
    public List<Game> findByNomGameContains(String nom) {
        return gameRepository.findByNomGameContains(nom);
    }

    @Override
    public List<Game> findByNomPrix(String nom, Double prix) {
        return gameRepository.findByNomPrix(nom, prix);
    }

    @Override
    public List<Game> findByType(Type type) {
        return gameRepository.findByType(type);
    }

    @Override
    public List<Game> findByTypeIdType(Long id) {
        return gameRepository.findByTypeIdType(id);
    }

    @Override
    public List<Game> findByOrderByNomGameAsc() {
        return gameRepository.findByOrderByNomGameAsc();
    }

    @Override
    public List<Game> trierGamesNomsPrix() {
        return gameRepository.trierGamesNomsPrix();
    }

    @Override
    public void deleteGameById(Long id) {
        Game game = gameRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Game not found"));

        // delete image file from filesystem before deleting the game
        if (game.getImagePath() != null) {
            try {
                Files.delete(Paths.get(System.getProperty("user.home"), "images", game.getImagePath()));
            } catch (IOException e) {
                // log but don't block deletion if file is missing
                System.err.println("Could not delete image file: " + e.getMessage());
            }
        }

        gameRepository.delete(game); // cascade handles DB images
    }
}