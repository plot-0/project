package view;


import controller.GameController;
import model.ChessPiece;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;


/**
 * This is the equivalent of the ChessPiece class,
 * but this class only cares how to draw Chess on ChessboardComponent
 */
public class ChessComponent extends JComponent {

    private boolean selected;
    private boolean entered;
    private boolean exited;

    public ChessPiece chessPiece;



    public ChessComponent(int size, ChessPiece chessPiece) {
        this.selected = false;
        setSize(size - 4, size - 4);
        setLocation(2, 2);
        setVisible(true);
        this.chessPiece = chessPiece;
        enableEvents(AWTEvent.MOUSE_EVENT_MASK);
    }

    public boolean isSelected() {
        return selected;
    }
    public boolean isExited() {
        return exited;
    }

    public boolean isEntered() {
        return entered;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
    public void setExited(boolean exited) {
        this.exited=exited;
    }

    public void setEntered(boolean entered) {
        this.entered = entered;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        //Font font = new Font("Helvetica", Font.PLAIN, getWidth() / 2);
        //g2.setFont(font);
        //g2.setColor(this.chessPiece.getColor());
        //g2.drawString(this.chessPiece.getName() , getWidth() / 4, getHeight() * 5 / 8); // FIXME: Use library to find the correct offset.
        g2.drawImage(this.chessPiece.getCat(), 0, 0, getWidth(), getHeight(), this);

        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.orange);
            ((Graphics2D) g).setStroke(new BasicStroke(5));
            g.drawOval(0, 0, getWidth(), getHeight());
        }
        if (isEntered()) {
            g.setColor(Color.yellow);
            ((Graphics2D) g).setStroke(new BasicStroke(18));
            g.drawRect(0, 0, getWidth(), getHeight());
        }

    }
    protected void processMouseEvent(MouseEvent e) {
        if (e.getID() == MouseEvent.MOUSE_ENTERED){
            this.setEntered(true);
            this.setExited(false);
            this.paintImmediately(0,0,getWidth(),getHeight());
        }
        if (e.getID() == MouseEvent.MOUSE_EXITED){
            this.setEntered(false);
            this.setExited(true);
            this.paintImmediately(0,0,getWidth(),getHeight());
        }
        else{
            disableEvents(AWTEvent.MOUSE_EVENT_MASK);
            this.setEntered(false);
            this.setExited(true);
            this.paintImmediately(0,0,getWidth(),getHeight());
        }
    }

}
