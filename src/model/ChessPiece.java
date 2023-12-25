package model;


import view.Cat;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ChessPiece {
    // Diamond, Circle, ...
    private String name;

    private Color color;
    private int id;
    private Image cat;

    public ChessPiece(String name) {
        this.name = name;
        this.color = Constant.colorMap.get(name);
        this.id = Constant.idMap2.get(name);
        this.cat = Cat.getCats().get(id-1);
    }

    public String getName() {
        return name;
    }

    public Color getColor(){return color;}
    public Integer getId(){
        return id;
    }

    public Image getCat() {
        return cat;
    }
}
