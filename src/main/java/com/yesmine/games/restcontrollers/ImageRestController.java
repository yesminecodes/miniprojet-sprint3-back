package com.yesmine.games.restcontrollers;

import com.yesmine.games.entities.Game;
import com.yesmine.games.entities.Image;
import com.yesmine.games.service.GameService;
import com.yesmine.games.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/image")
public class ImageRestController {
    @Autowired
    ImageService imageService;
    @Autowired
    private GameService gameService;

    @RequestMapping(value = "/upload",method = RequestMethod.POST )
    public Image uploadImage(@RequestParam("image")MultipartFile file) throws IOException{
        return imageService.uplaodImage(file);
    }

    @RequestMapping(value = "/get/info/{id}",method = RequestMethod.GET)
    public Image getImageDetails(@PathVariable("id")Long id) throws IOException{
        return imageService.getImageDetails(id);
    }

    @RequestMapping(value = "/load/{id}",method = RequestMethod.GET)
    public ResponseEntity<byte[]> getImage(@PathVariable("id")Long id) throws IOException{
        return imageService.getImage(id);
    }

    @PostMapping(value = "/uploadImageGame/{idGame}")
    public Image uploadMultiImages(@RequestParam("image")MultipartFile file,@PathVariable("idGame")Long idGame) throws IOException{
        return imageService.uplaodImageGame(file,idGame);
    }

    @RequestMapping(value = "/getImagesGame/{idGame}",method = RequestMethod.GET)
    public List<Image> getImagesGame(@PathVariable("idGame")Long idGame)throws IOException{
        return imageService.getImagesParGame(idGame);
    }

    @RequestMapping(value = "/uploadFS/{id}", method = RequestMethod.POST)
    public ResponseEntity<Void> uploadImageFS(
            @RequestParam("image") MultipartFile file,
            @PathVariable("id") Long id) throws IOException {

        Game g = gameService.getGame(id);
        if (g == null) {
            return ResponseEntity.notFound().build();
        }

        String filename = id + ".jpg";
        g.setImagePath(filename);

        Path imagesDir = Paths.get(System.getProperty("user.home"), "images");
        Files.createDirectories(imagesDir);
        Files.write(imagesDir.resolve(filename), file.getBytes());

        gameService.saveGame(g);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/loadfromFS/{id}",
            method = RequestMethod.GET,
            produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getImageFS(@PathVariable("id") Long id) throws IOException {

        Game g = gameService.getGame(id);

        if (g.getImagePath() == null || g.getImagePath().isBlank()) {
            return ResponseEntity.notFound().build();
        }

        Path filePath = Paths.get(System.getProperty("user.home"), "images", g.getImagePath());

        if (!Files.exists(filePath)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(Files.readAllBytes(filePath));
    }

}
