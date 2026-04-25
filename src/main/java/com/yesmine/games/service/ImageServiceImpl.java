package com.yesmine.games.service;

import com.yesmine.games.entities.Game;
import com.yesmine.games.entities.Image;
import com.yesmine.games.repos.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    GameService gameService;

    @Override
    public Image uplaodImage(MultipartFile file) throws IOException {
        return imageRepository.save(Image.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .image(file.getBytes())
                .build());
    }

    @Override
    public Image uplaodImageGame(MultipartFile file, Long idGame) throws IOException {
        // ✅ fetch the game by id
        Game game = gameService.getGame(idGame);

        // ✅ build and save the image linked to the game
        Image image = Image.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .image(file.getBytes())
                .game(game)              // ✅ link image to game
                .build();

        Image savedImage = imageRepository.save(image);

        // ✅ also add image to game's list and save
        game.addImage(savedImage);

        return savedImage;
    }

    @Override
    public List<Image> getImagesParGame(Long gameId) {
        return imageRepository.getImagesByGame(gameId); // ✅ matches the method above
    }

    @Override
    public Image getImageDetails(Long id) throws IOException {
        final Optional<Image> dbImage = imageRepository.findById(id);
        // ✅ throw proper exception if not found instead of get() on empty Optional
        Image img = dbImage.orElseThrow(() -> new RuntimeException("Image not found: " + id));
        return Image.builder()
                .idImage(img.getIdImage())
                .name(img.getName())
                .type(img.getType())
                .image(img.getImage())
                .build();
    }

    @Override
    public ResponseEntity<byte[]> getImage(Long id) throws IOException {
        final Optional<Image> dbImage = imageRepository.findById(id);
        // ✅ throw proper exception if not found
        Image img = dbImage.orElseThrow(() -> new RuntimeException("Image not found: " + id));
        return ResponseEntity
                .ok()
                .contentType(MediaType.valueOf(img.getType()))
                .body(img.getImage());
    }

    @Override
    public void deleteImage(Long id) {
        imageRepository.deleteById(id);
    }
}