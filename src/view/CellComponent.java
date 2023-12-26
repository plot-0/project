package view;

import javax.swing.*;
import java.awt.*;

/**
 * This is the equivalent of the Cell class,
 * but this class only cares how to draw Cells on ChessboardComponent
 */

public class CellComponent extends JPanel {
    private Color background;

    public CellComponent(Color background, Point location, int size) {
        setLayout(null);
        setLocation(location);
        setSize(size, size);
        this.background = Color.pink;
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(Color.white);
        g.fillRect(1, 1, this.getWidth()-2, this.getHeight()-2);
        super.paintComponents(g);
    }
}
