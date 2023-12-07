import controller.GameController;
import model.Chessboard;
import view.ChessGameFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ChessGameFrame mainFrame = new ChessGameFrame(1100, 810);
            GameController gameController = new GameController((810 * 4 / 5) / 9, mainFrame.getChessboardComponent(), new Chessboard());
            mainFrame.setGameController(gameController);
            gameController.setStatusLabel(mainFrame.getStatusLabel());
            mainFrame.setVisible(true);
        });
    }
}
