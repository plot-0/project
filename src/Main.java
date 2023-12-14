import controller.GameController;
import model.Chessboard;
import view.ChessGameFrame;
import view.Menu;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Menu menu = new Menu(1100,810);
            menu.setVisible(true);
        });
    }
}
