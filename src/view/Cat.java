package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class Cat {
    public static Image cat1;
    public static Image cat2;
    public static Image cat3;
    public static Image cat4;
    public static ArrayList<Image> cats = new ArrayList<Image>();

    public static ArrayList<Image> getCats() {
        return cats;
    }

    {
        cat1 = new ImageIcon("resource/cat1.jpg").getImage();
        cats.add(cat1);
        cat2 = new ImageIcon("resource/cat2.jpg").getImage();
        cats.add(cat2);
        cat3 = new ImageIcon("resource/cat3.jpg").getImage();
        cats.add(cat3);
        cat4 = new ImageIcon("resource/cat4.jpg").getImage();
        cats.add(cat4);
    }
}
