package com.yesmine.games.service;

import com.yesmine.games.entities.Image;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ImageService {
    Image uplaodImage(MultipartFile file) throws IOException;
    Image uplaodImageGame(MultipartFile file, Long idGame) throws IOException; // ✅
    List<Image> getImagesParGame(Long gameId);                                 // ✅
    Image getImageDetails(Long id) throws IOException;
    ResponseEntity<byte[]> getImage(Long id) throws IOException;
    void deleteImage(Long id);
}

