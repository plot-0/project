package view;

import controller.GameController;

import javax.swing.*;
import java.awt.*;

/**
 * 这个类表示游戏过程中的整个游戏界面，是一切的载体
 */
public class GameFrame extends JFrame {
    //    public final Dimension FRAME_SIZE ;
    private final int WIDTH;
    private final int HEIGTH;

    private final int ONE_CHESS_SIZE;

    private GameController gameController;

    private ChessboardComponent chessboardComponent;

    private JLabel scoreLabel;
    private JLabel swaplimitLabel;
    private JLabel goalLabel;
    private Menu menu = new Menu(1100,810);
    public GameFrame(int width, int height) {
        setTitle("2023 CS109 Project Demo"); //设置标题
        this.WIDTH = width;
        this.HEIGTH = height;
        this.ONE_CHESS_SIZE = (HEIGTH * 4 / 5) / 9;

        setSize(WIDTH, HEIGTH);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);


        addChessboard();
        addscoreLabel();
        addswapLimitLabel();
        addgoalLabel();
        addRestartButton();
        addSwapConfirmButton();
        addNextStepButton();
        addLoadButton();
        addSaveButton();
        addMenuButton();
    }

    public ChessboardComponent getChessboardComponent() {
        return chessboardComponent;
    }

    public void setChessboardComponent(ChessboardComponent chessboardComponent) {
        this.chessboardComponent = chessboardComponent;
    }

    public GameController getGameController() {
        return gameController;
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    /**
     * 在游戏面板中添加棋盘
     */
    private void addChessboard() {
        chessboardComponent = new ChessboardComponent(ONE_CHESS_SIZE);
        chessboardComponent.setLocation(HEIGTH / 5, HEIGTH / 10);
        add(chessboardComponent);
    }

    /**
     * 在游戏面板中添加标签
     */
    private void addscoreLabel() {
        this.scoreLabel = new JLabel("Score:0");
        scoreLabel.setLocation(HEIGTH, HEIGTH / 10);
        scoreLabel.setSize(200, 60);
        scoreLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(scoreLabel);
    }

    public JLabel getScoreLabel() {
        return scoreLabel;
    }

    private void addswapLimitLabel() {
        this.swaplimitLabel = new JLabel("Swap:"+JTextFieldFrame.getLimit());
        swaplimitLabel.setLocation(HEIGTH, HEIGTH / 10+30);
        swaplimitLabel.setSize(200, 60);
        swaplimitLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(swaplimitLabel);
    }

    public JLabel getSwaplimitLabel() {
        return swaplimitLabel;
    }

    public JLabel getGoalLabel() {
        return goalLabel;
    }

    private void addgoalLabel() {
        this.goalLabel = new JLabel("Goal:"+JTextFieldFrame.getGoal());
        goalLabel.setLocation(HEIGTH, HEIGTH / 10+60);
        goalLabel.setSize(200, 60);
        goalLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(goalLabel);
    }



    private void addRestartButton() {
        JButton button = new JButton("Restart");
        button.addActionListener(e -> {
            //JOptionPane.showMessageDialog(this, "Show hello world");
            gameController.initialize();
        });
        button.setLocation(HEIGTH, HEIGTH / 10 + 120);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }

    private void addSwapConfirmButton() {
        JButton button = new JButton("Confirm Swap");
        button.addActionListener((e) -> chessboardComponent.swapChess());
        button.setLocation(HEIGTH, HEIGTH / 10 + 200);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }

    private void addNextStepButton() {
        JButton button = new JButton("Next Step");
        button.addActionListener((e) -> {
            chessboardComponent.nextStep();
        });
        button.setLocation(HEIGTH, HEIGTH / 10 + 280);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }

    private void addLoadButton() {
        JButton button = new JButton("Load");
        button.setLocation(HEIGTH, HEIGTH / 10 + 360);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> {
            System.out.println("Click load");
            String path = JOptionPane.showInputDialog(this, "Input Path here");
            String type = path.substring(path.lastIndexOf("."));
            if (type.equals(".txt")){
                System.out.println(path);
                gameController.loadGameFromFile(path);
            }
            else{
                System.out.println("101");
                JOptionPane.showMessageDialog(this,"101");
            }
        });
    }
    private void addSaveButton() {
        JButton button = new JButton("Save");
        button.setLocation(HEIGTH, HEIGTH / 10 + 440);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> {
            System.out.println("Click save");
            String path = JOptionPane.showInputDialog(this, "Input Path here");
            System.out.println(path);
            gameController.SaveGameToFile(path);
        });
    }

    private void addMenuButton(){
        JButton button = new JButton("Menu");
        button.setLocation(HEIGTH, HEIGTH / 10 + 520);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> {
            dispose();
            menu.setVisible(true);
        });
    }
    public void errorDialog1(){
        JOptionPane.showConfirmDialog(this,"101");
    }

}
