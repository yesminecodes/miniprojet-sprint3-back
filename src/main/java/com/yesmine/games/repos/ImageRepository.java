package com.yesmine.games.repos;

import com.yesmine.games.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {

    @Query("SELECT i FROM Image i WHERE i.game.idGame = :idGame")
    List<Image> getImagesByGame(@Param("idGame") Long idGame);
}