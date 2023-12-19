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
        addAutoModeButton();
    }

    private void addAutoModeButton() {
        JButton button = new JButton("Auto Mode");
        button.setLocation(WIDTH/2-100, HEIGTH / 10 + 280);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> {
            Object[] difficulty ={"LEVEL 1","LEVEL 2","LEVEL 3","CUSTOM"};
            int op = JOptionPane.showOptionDialog(this,"难度选择","difficulty",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,difficulty,difficulty[0]);
            if (op==0){
                enterLevel(3,90,true,1);//LEVEL1
                setVisible(false);
            }
            else if(op==1){
                enterLevel(6,300,true,2);//LEVEL2
                setVisible(false);
            }
            else if (op==2){
                enterLevel(9,900,true,3);//LEVEL3
                setVisible(false);
            }
            else if (op==3){
                new JTextFieldFrame(true);
                setVisible(false);
            }
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
            System.exit(0);
        });
    }

    private void addStartButton() {
        JButton button = new JButton("Start");
        button.setLocation(WIDTH/2-100, HEIGTH / 10 + 200);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> {
            Object[] difficulty ={"LEVEL 1","LEVEL 2","LEVEL 3","CUSTOM"};
            int op = JOptionPane.showOptionDialog(this,"难度选择","difficulty",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,difficulty,difficulty[0]);
            if (op==0){
                enterLevel(3,90,false,1);//LEVEL1
                setVisible(false);
            }
            else if(op==1){
                enterLevel(6,300,false,2);//LEVEL2
                setVisible(false);
            }
            else if (op==2){
                enterLevel(9,900,false,3);//LEVEL3
                setVisible(false);
            }
            else if (op==3){
                new JTextFieldFrame(false);
                setVisible(false);
            }
        });
    }
    private void enterLevel(int limit,int goal,boolean mode,int level){
        GameFrame mainFrame = new GameFrame(1100, 810, mode);
        GameController gameController = new GameController((810 * 4 / 5) / 9, mainFrame.getChessboardComponent(), new Chessboard(),mainFrame);
        mainFrame.setGameController(gameController);
        mainFrame.setSwaplimitLabel(limit);
        mainFrame.setGoalLabel(goal);
        gameController.setScoreLabel(mainFrame.getScoreLabel());
        gameController.setSwaplimitLabel(mainFrame.getSwaplimitLabel());
        gameController.setgoalLabel(mainFrame.getGoalLabel());
        GameController.setSwaplimit(limit);
        gameController.initswaplimitLabel = new JLabel(String.valueOf(limit));
        GameController.setGoal(goal);
        gameController.level = level;
        mainFrame.setVisible(true);
        if (!mode){
            GameController.mode = false;
            mainFrame.mode = false;
        }
        else{
            GameController.mode = true;
            mainFrame.mode = true;
        }
    }
}
