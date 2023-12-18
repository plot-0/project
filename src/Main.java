import controller.GameController;
import view.GameFrame;
import view.Menu;

import javax.swing.*;
import view.JTextFieldFrame;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Menu menu = new Menu(1100,810);
            menu.setVisible(true);
        });
    }
}
