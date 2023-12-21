import controller.AudioFilePlayer;
import controller.GameController;
import view.GameFrame;
import view.Menu;

import javax.swing.*;
import view.JTextFieldFrame;
import controller.AudioPlayer;
import controller.AudioFilePlayer;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Menu menu = new Menu(1100,810);
            menu.setVisible(true);
            AudioPlayer.playBgm("resource/bgm.wav");
        });
    }
}
