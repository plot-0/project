package model;


import java.awt.*;

public class ChessPiece {
    // Diamond, Circle, ...
    private String name;

    private Color color;
    private int id;

    public ChessPiece(String name) {
        this.name = name;
        this.color = Constant.colorMap.get(name);
        this.id = Constant.colorMap2.get(name);
    }

    public String getName() {
        return name;
    }

    public Color getColor(){return color;}
    public Integer getId(){
        return id;
    }

}
