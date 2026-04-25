package com.yesmine.games.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idImage ;
    private String name ;
    private String type ;
    @Column( name = "IMAGE" , columnDefinition = "LONGBLOB" )
    @Lob
    private byte[] image;
    @ManyToOne
    @JoinColumn(name = "GAME_ID")
    @JsonIgnore
    private Game game;

}
