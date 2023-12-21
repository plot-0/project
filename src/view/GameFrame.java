package view;

import controller.GameController;
import controller.Hint;
import controller.Redo;
import model.Chessboard;
import model.ChessboardPoint;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

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
    public Hint hint;
    public JButton shuffle;
    private JLabel scoreLabel;
    private JLabel swaplimitLabel;
    private JLabel goalLabel;
    private Menu menu = new Menu(1100,810);
    public static boolean mode = false;
    public GameFrame(int width, int height,boolean mode) {
        setTitle("2023 CS109 Project Demo"); //设置标题
        this.WIDTH = width;
        this.HEIGTH = height;
        this.ONE_CHESS_SIZE = (HEIGTH * 4 / 5) / 9;
        GameFrame.mode = mode;

        setSize(WIDTH, HEIGTH);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);


        addChessboard();
        addscoreLabel();
        addswapLimitLabel();
        addgoalLabel();
        addShuffleButton();
        addRestartButton();
        if (!mode){
            addSwapConfirmButton();
            addNextStepButton();
        }
        addLoadButton();
        addSaveButton();
        addMenuButton();
        addHintButton();
        addRedoButton();
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

    public void setSwaplimitLabel(int limit){
        swaplimitLabel.setText("Swap:"+limit);
    }
    public void setGoalLabel(int goal){
        goalLabel.setText("Goal:"+goal);
    }

    public void setScoreLabel(int score) {
        scoreLabel.setText("Score:"+score);
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



    private void addShuffleButton() {
        JButton button = new JButton("Shuffle limit:"+GameController.shufflelimit);
        button.addActionListener(e -> {
            if (gameController.shufflelimit>0){
                gameController.initialize();
                gameController.shufflelimit -= 1;
                this.shuffle.setText("Shuffle limit:"+GameController.shufflelimit);
                this.shuffle.revalidate();
                this.shuffle.repaint();
            }
            else{
                JOptionPane.showMessageDialog(this,"重置次数已用尽");
            }
        });
        button.setLocation(HEIGTH, HEIGTH / 10 + 120);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        shuffle = button;
        add(button);
    }

    private void addSwapConfirmButton() {
        JButton button = new JButton("Confirm Swap");
        button.addActionListener(e -> {
            //this.redo = new Redo(gameController.model.convertBoardToList(),this,gameController);
            chessboardComponent.swapChess();
        });
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
                JOptionPane.showMessageDialog(this,"101","存档损坏",JOptionPane.ERROR_MESSAGE);
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
            clickMenu();
        });
    }
    public void addRestartButton(){
        JButton button = new JButton("Restart");
        button.setLocation(HEIGTH,HEIGTH/10 + 600);
        button.setSize(200,60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> {
            clickRestart();
        });
    }
    public void addHintButton(){
        JButton button = new JButton("Hint");
        button.setLocation(HEIGTH-260,HEIGTH/10 + 600);
        button.setSize(200,60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> {
            Hint hint = new Hint(gameController.model);
            ArrayList<ChessboardPoint> points = hint.clickHint();
            if (!points.isEmpty()){
                ChessboardPoint point1 = points.get(0);
                ChessboardPoint point2 = points.get(1);
                chessboardComponent.getChessComponentAtGrid(point1).setSelected(true);
                chessboardComponent.getChessComponentAtGrid(point2).setSelected(true);
                chessboardComponent.getChessComponentAtGrid(point1).paintImmediately(0,0,getWidth(),getHeight());
                chessboardComponent.getChessComponentAtGrid(point2).paintImmediately(0,0,getWidth(),getHeight());
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
                chessboardComponent.getChessComponentAtGrid(point1).setSelected(false);
                chessboardComponent.getChessComponentAtGrid(point2).setSelected(false);
                repaint();
            }
            else{
                JOptionPane.showMessageDialog(this,"没有可交换的点");
            }
        });
    }
    public void addRedoButton(){
        JButton button = new JButton("Redo");
        button.setLocation(HEIGTH-480,HEIGTH/10 + 600);
        button.setSize(200,60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> {
            if (gameController.redo!=null){
                gameController.redo.load();
            }
            else{
                JOptionPane.showMessageDialog(this,"无法撤销");
            }
        });
    }
    public void clickMenu(){
        dispose();
        menu.setVisible(true);
    }
    public void clickRestart(){
        gameController.initialize();
        gameController.score = 0;
        scoreLabel.setText("Score:"+ gameController.score);
        gameController.setScoreLabel(scoreLabel);
        swaplimitLabel.setText("Swap:" + gameController.initswaplimitLabel.getText());
        gameController.setSwaplimitLabel(swaplimitLabel);
        GameController.swaplimit = Integer.parseInt(gameController.initswaplimitLabel.getText());
        gameController.swapstate = 1;
        GameController.shufflelimit = 3;
        shuffle.setText("Shuffle limit:"+GameController.shufflelimit);
    }
}