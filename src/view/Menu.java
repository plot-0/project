package view;

import controller.GameController;
import model.Chessboard;

import javax.swing.*;
import java.awt.*;

public class Menu extends JFrame {
    private final int WIDTH;
    private final int HEIGTH;
    public Menu(int width,int height){
        setTitle("2023 CS109 Project Demo"); //设置标题
        this.WIDTH = width;
        this.HEIGTH = height;

        setSize(WIDTH, HEIGTH);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);

        addStartButton();
        addExitButton();
        //addAutoModeButton();
    }

    private void addAutoModeButton() {
        JButton button = new JButton("Auto Mode");
        button.setLocation(WIDTH/2-100, HEIGTH / 10 + 280);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> {
            new JTextFieldFrame();
            setVisible(false);
        });
    }



    private void addExitButton() {
        JButton button = new JButton("Exit");
        button.setLocation(WIDTH/2-100, HEIGTH / 10 + 360);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> {
            dispose();
        });
    }

    private void addStartButton() {
        JButton button = new JButton("Start");
        button.setLocation(WIDTH/2-100, HEIGTH / 10 + 200);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> {
            new JTextFieldFrame();
            setVisible(false);
        });
    }

}
