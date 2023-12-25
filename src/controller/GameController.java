package controller;

import listener.GameListener;
import model.*;
import view.CellComponent;
import view.ChessComponent;
import view.GameFrame;
import view.ChessboardComponent;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Controller is the connection between model and view,
 * when a Controller receive a request from a view, the Controller
 * analyzes and then hands over to the model for processing
 * [in this demo the request methods are onPlayerClickCell() and
 * onPlayerClickChessPiece()]
 */
public class GameController implements GameListener {
    //修改了构造方法，获取size
    private final int CHESS_SIZE;
    public Chessboard model;
    public ChessboardComponent view;
    public GameFrame frame;
    public int level;
    public static int fallstate = 1;
    public int swapstate = 1;
    public static int swaplimit;
    public static int shufflelimit = 3;
    public static int goal;
    public static boolean mode = false;
    public int
            score;
    // Record whether there is a selected piece before
    public ChessboardPoint selectedPoint;
    public ChessboardPoint selectedPoint2;
    public JLabel scoreLabel;
    JLabel swaplimitLabel;
    public JLabel initswaplimitLabel;
    private JLabel goalLabel;
    public Redo redo;


    public void setScoreLabel(JLabel scoreLabel) {
        this.scoreLabel = scoreLabel;
    }
    public void setSwaplimitLabel(JLabel SwaplimitLabel) {this.swaplimitLabel = SwaplimitLabel;}
    public void setgoalLabel(JLabel goalLabel) {this.goalLabel = goalLabel;}

    public static int getSwaplimit() {
        return swaplimit;
    }

    public static void setSwaplimit(int swaplimit) {
        GameController.swaplimit = swaplimit;
    }

    public static int getGoal() {
        return goal;
    }

    public static void setGoal(int goal) {
        GameController.goal = goal;
    }

    public GameController(int height, ChessboardComponent view, Chessboard model, GameFrame frame) {
        CHESS_SIZE = (height * 4 / 5) / 9;
        this.view = view;
        this.model = model;
        this.frame = frame;
        view.registerController(this);
        view.initiateChessComponent(model);
        view.repaint();
    }


    public void initialize() {
            model.initPieces();
            view.removeAllChessComponentsAtGrids();
            view.initiateChessComponent(model);
            view.repaint();
    }
    public void eliminate(int[][] search){
        score += 10*model.eliminateNum(model.getGrid());
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                if (search[i][j]==1){
                    model.removeChessPiece(new ChessboardPoint(i,j));
                    view.removeChessComponentAtGrid(new ChessboardPoint(i,j));
                    view.repaint();
                }
            }
        }
        fallstate = 1;
        swapstate = 0;
        this.scoreLabel.setText("Score:" + score);
        if (!mode){
            detectSucceed();
        }
    }

    //降落
    public void fall(){
        Cell[][] reverse = model.reverse(model.getGrid());
        for (int j = 0; j <Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
            for (int e=0;e< model.nullInCol(reverse[j]);e++){
                for (int i = Constant.CHESSBOARD_ROW_SIZE.getNum()-2; i >=0 ; i--) {
                    compare(new ChessboardPoint(i,j));
                }
                try {
                    Thread.sleep(20);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }
    //检测到下方一格为空白时降落一格
    public void compare(ChessboardPoint point){
        if (model.getChessPieceAt(point)!=null && model.getChessPieceAt(new ChessboardPoint(point.getRow()+1, point.getCol()))==null){
            ChessComponent chess1 = view.removeChessComponentAtGrid(point);
            model.swapChessPiece(point,new ChessboardPoint(point.getRow()+1, point.getCol()));
            view.setChessComponentAtGrid(new ChessboardPoint(point.getRow()+1, point.getCol()),chess1);
            chess1.paintImmediately(0,0,frame.getWidth(),frame.getHeight());
            view.getGridComponentAt(point).paintImmediately(0,0,frame.getWidth(),frame.getHeight());
            view.paintImmediately(0,0,frame.getWidth(),frame.getHeight());

        }
    }
    public void regenerate(Cell[][] grid){
        ArrayList<ChessboardPoint> points = model.nullPoints(grid);
        for (int e=0;e<points.size();e++){
            ChessPiece piece = new ChessPiece(Util.RandomPick(new String[]{"💎", "⚪", "▲", "🔶"}));
            ChessComponent chess = new ChessComponent(CHESS_SIZE,piece);
            model.getGridAt(points.get(e)).setPiece(piece);
            view.initiateChessComponent(model);
            view.getChessComponentAtGrid(points.get(e)).repaint();
        }
    }
    // click an empty cell
    @Override
    public void onPlayerClickCell(ChessboardPoint point, CellComponent component) {
    }

    @Override
    public void onPlayerSwapChess() {
        playSound("resource/swap.wav");
        this.redo = new Redo(model.convertBoardToList(),frame,this);
        System.out.println("swapstate:"+swapstate);
        if(selectedPoint!=null && selectedPoint2!=null && swaplimit>0 ){
            model.swapChessPiece(selectedPoint,selectedPoint2);
            ChessComponent chess1 = view.removeChessComponentAtGrid(selectedPoint);
            ChessComponent chess2 = view.removeChessComponentAtGrid(selectedPoint2);
            view.setChessComponentAtGrid(selectedPoint,chess2);
            view.setChessComponentAtGrid(selectedPoint2,chess1);
            chess1.paintImmediately(0,0,frame.getWidth(),frame.getHeight());
            chess2.paintImmediately(0,0,frame.getWidth(),frame.getHeight());
            view.paintImmediately(0,0,frame.getWidth(),frame.getHeight());
        }
        if (!model.canSwap(selectedPoint,selectedPoint2, model.getGrid())){
            model.swapChessPiece(selectedPoint,selectedPoint2);
            ChessComponent chess1 = view.removeChessComponentAtGrid(selectedPoint);
            ChessComponent chess2 = view.removeChessComponentAtGrid(selectedPoint2);
            view.setChessComponentAtGrid(selectedPoint,chess2);
            view.setChessComponentAtGrid(selectedPoint2,chess1);
            chess1.repaint();
            chess2.repaint();
            selectedPoint = null;
            selectedPoint2 = null;
            System.out.println("can't swap");
            JOptionPane.showMessageDialog(frame,"can't swap");
        }
        else if(swapstate == 1){
            selectedPoint = null;
            selectedPoint2 = null;
            swaplimit -= 1;
            this.swaplimitLabel.setText("Swap:"+swaplimit);
            while (mode && (model.eliminateNum(model.getGrid())!=0 || model.nullPoints(model.getGrid()).size()>0)){
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {

                }
                onPlayerNextStep();
                if (detectSucceed()){
                    break;
                }
                if (detectFail()){
                    break;
                }

                view.paintImmediately(0,0,frame.getWidth(),frame.getHeight());
            }
        }
    }
    public static Future<?> playSound(String path) {
        return Executors.newSingleThreadExecutor().submit(() -> {
            new AudioPlayer().play(path);
        });
    }

    @Override
    public void onPlayerNextStep() {
        if (model.eliminateNum(model.getGrid())!=0){
            eliminate(model.search());
            playSound("resource/swapsound.wav");
        }
        else if (fallstate == 1){
            fall();
            System.out.println("fall");
            fallstate = 0;
            playSound("resource/falll.wav");

        }
        else{
            regenerate(model.getGrid());
            System.out.println("regenerate");
            view.repaint();
            swapstate = 1;
            if (!mode){
                detectFail();
            }
        }
    }

    // click a cell with a chess
    @Override
    public void onPlayerClickChessPiece(ChessboardPoint point, ChessComponent component) {
        if (selectedPoint2 != null) {
            var distance2point1 = Math.abs(selectedPoint.getCol() - point.getCol()) + Math.abs(selectedPoint.getRow() - point.getRow());
            var distance2point2 = Math.abs(selectedPoint2.getCol() - point.getCol()) + Math.abs(selectedPoint2.getRow() - point.getRow());
            var point1 = (ChessComponent) view.getGridComponentAt(selectedPoint).getComponent(0);
            var point2 = (ChessComponent) view.getGridComponentAt(selectedPoint2).getComponent(0);
            if (distance2point1 == 0 && point1 != null) {
                point1.setSelected(false);
                point1.repaint();
                selectedPoint = selectedPoint2;
                selectedPoint2 = null;
            } else if (distance2point2 == 0 && point2 != null) {
                point2.setSelected(false);
                point2.repaint();
                selectedPoint2 = null;
            } else if (distance2point1 == 1 && point2 != null) {
                point2.setSelected(false);
                point2.repaint();
                selectedPoint2 = point;
                component.setSelected(true);
                component.repaint();
            } else if (distance2point2 == 1 && point1 != null) {
                point1.setSelected(false);
                point1.repaint();
                selectedPoint = selectedPoint2;
                selectedPoint2 = point;
                component.setSelected(true);
                component.repaint();
            }
            return;
        }


        if (selectedPoint == null) {
            selectedPoint = point;
            component.setSelected(true);
            component.repaint();
            return;
        }

        var distance2point1 = Math.abs(selectedPoint.getCol() - point.getCol()) + Math.abs(selectedPoint.getRow() - point.getRow());

        if (distance2point1 == 0) {
            selectedPoint = null;
            component.setSelected(false);
            component.repaint();
            return;
        }

        if (distance2point1 == 1) {
            selectedPoint2 = point;
            component.setSelected(true);
            component.repaint();
        } else {
            selectedPoint2 = null;

            var grid = (ChessComponent) view.getGridComponentAt(selectedPoint).getComponent(0);
            if (grid == null) return;
            grid.setSelected(false);
            grid.repaint();

            selectedPoint = point;
            component.setSelected(true);
            component.repaint();
        }


    }

    public void SaveGameToFile(String path) {
        List<String> saveLines = model.convertBoardToList();
        Save file = new Save();
        file.setSaveLines(saveLines);
        file.setScore(score);
        file.setSwapstate(swapstate);
        file.setFallstate(fallstate);
        file.setSwaplimit(swaplimit);
        file.setGoal(goal);
        file.level = level;
        file.shufflelimit = shufflelimit;
        try {
            Files.write(Path.of(path),file.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadGameFromFile(String path) {
        StringBuilder sb = new StringBuilder();
        String file = null;
        try {
            file = Files.readString(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String fl[] = file.split("\n");

        try {
            for (int i =0;i<Constant.CHESSBOARD_ROW_SIZE.getNum();i++){
                String[] line = fl[i].split(" ");
                for (int j=0;j<Constant.CHESSBOARD_COL_SIZE.getNum();j++){
                    if (Objects.equals(line[j], "")){
                        model.setChessPiece(new ChessboardPoint(i,j),null);
                    }
                    else{
                        model.setChessPiece(new ChessboardPoint(i,j),new ChessPiece(Constant.idMap3.get(Integer.parseInt(line[j]))));
                    }
                }
            }
            score = Integer.parseInt(fl[Constant.CHESSBOARD_ROW_SIZE.getNum()].split("\r")[0]);
            swaplimit = Integer.parseInt(fl[Constant.CHESSBOARD_ROW_SIZE.getNum()+1].split("\r")[0]);
            fallstate = Integer.parseInt(fl[Constant.CHESSBOARD_ROW_SIZE.getNum()+2].split("\r")[0]);
            swapstate = Integer.parseInt(fl[Constant.CHESSBOARD_ROW_SIZE.getNum()+3].split("\r")[0]);
            goal = Integer.parseInt(fl[Constant.CHESSBOARD_ROW_SIZE.getNum()+4].split("\r")[0]);
            level = Integer.parseInt(fl[Constant.CHESSBOARD_ROW_SIZE.getNum()+5].split("\r")[0]);
            shufflelimit = Integer.parseInt(fl[Constant.CHESSBOARD_ROW_SIZE.getNum()+6].split("\r")[0]);
            view.removeAllChessComponentsAtGrids();
            view.initiateChessComponent(model);
            view.repaint();
            this.scoreLabel.setText("Score:" + score);
            this.swaplimitLabel.setText("Swap" + swaplimit);
            this.goalLabel.setText("goal:" + goal);
            frame.shuffle.setText("Shuffle limit:"+shufflelimit);

        }
            catch (NumberFormatException e){
            System.out.println("102");
            JOptionPane.showMessageDialog(frame,"102","存档损坏",JOptionPane.ERROR_MESSAGE);
        }
            catch (ArrayIndexOutOfBoundsException e){
                System.out.println("102");
                JOptionPane.showMessageDialog(frame,"102","存档损坏",JOptionPane.ERROR_MESSAGE);
        }
            catch (NullPointerException e){
            System.out.println("103");
            JOptionPane.showMessageDialog(frame,"103","存档损坏",JOptionPane.ERROR_MESSAGE);
        }
    }

    private void enterNextLevel(int limit,int goal){
        frame.dispose();
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
        gameController.level = level+1;
        mainFrame.setVisible(true);
    }
    private boolean detectSucceed(){
        if (level<=2 && score>=goal){
            playSound("resource/nextlevelsound.wav");
            Object[] option = {"进入下一关","返回标题"};
            int op = JOptionPane.showOptionDialog(frame,"succeed","结果",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,option,option[0]);
            if (op==0 && level==1){
                enterNextLevel(6,300);
                return true;
            }
            else if (op==0 && level==2){
                enterNextLevel(9,900);
                return true;
            }
            else if (op==1){
                frame.clickMenu();
                return true;
            }
        }
        else if (score>=goal){
            JOptionPane.showMessageDialog(frame,"Succeed");
            frame.clickMenu();
            return true;
        }
        return false;
    }
    private boolean detectFail(){
        if (swaplimit==0 && model.eliminateNum(model.getGrid())==0 && model.nullPoints(model.getGrid()).isEmpty() && score<goal){
            playSound("resource/fail.wav");
            Object[] option = {"再来一局","返回标题"};
            int op = JOptionPane.showOptionDialog(frame,"fail","结果",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,option,option[0]);
            if (op==0 && level==1){
                frame.clickRestart();
                return true;
            }
            else if (op==0 && level==2){
                frame.clickRestart();
                return true;
            }
            else if (op==0 && level==3){
                frame.clickRestart();
                return true;
            }
            else if (op==0 && level==4){
                frame.clickRestart();
                return true;
            }
            else if (op==1){
                frame.clickMenu();
                return true;
            }
        }
        return false;
    }
}
