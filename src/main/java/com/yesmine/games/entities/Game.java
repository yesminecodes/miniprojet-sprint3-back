package com.yesmine.games.entities;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idGame;
    private String nomGame;
    private double prixGame;
    private Date dateCreation;

    @ManyToOne
    @JoinColumn(name = "type_id_type")
    private Type type;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images = new ArrayList<>();

    private String imagePath;

    public Game() {
        super();
    }

    public Game(String nomGame, double prixGame, Date dateCreation) {
        this.nomGame = nomGame;
        this.prixGame = prixGame;
        this.dateCreation = dateCreation;
    }

    public Long getIdGame() { return idGame; }
    public void setIdGame(Long idGame) { this.idGame = idGame; }

    public String getNomGame() { return nomGame; }
    public void setNomGame(String nomGame) { this.nomGame = nomGame; }

    public double getPrixGame() { return prixGame; }
    public void setPrixGame(double prixGame) { this.prixGame = prixGame; }

    public Date getDateCreation() { return dateCreation; }
    public void setDateCreation(Date dateCreation) { this.dateCreation = dateCreation; }

    public Type getType() { return type; }
    public void setType(Type type) { this.type = type; }

    public List<Image> getImages() { return images; }          // ✅ renamed to getImages
    public void setImages(List<Image> images) { this.images = images; }

    // ✅ helper to add a single image
    public void addImage(Image image) {
        images.add(image);
        image.setGame(this);
    }

    // ✅ helper to remove a single image
    public void removeImage(Image image) {
        images.remove(image);
        image.setGame(null);
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public String toString() {
        return "Game [idGame=" + idGame + ", nomGame=" + nomGame +
                ", prixGame=" + prixGame + ", dateCreation=" + dateCreation + "]";
    }
}