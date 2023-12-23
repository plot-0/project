package controller;

import model.Cell;
import model.Chessboard;
import model.ChessboardPoint;
import model.Constant;
import view.ChessComponent;
import view.GameFrame;

import java.util.ArrayList;

public class Hint {
    public static Chessboard model;
    public static ArrayList<ChessboardPoint> points;//提示的交换点
    public static GameController controller;
    public static Redo redo;
    public Hint(GameController controller) {
        Hint.controller = controller;
        Hint.model = controller.model;
    }
    //寻找图中所有的可交换点
    public static ArrayList<ChessboardPoint> canSwapChesses(){
        ArrayList<ChessboardPoint> points = new ArrayList<ChessboardPoint>();
        for (int i=0;i<Constant.CHESSBOARD_ROW_SIZE.getNum();i++){
            for (int j=0;j<Constant.CHESSBOARD_COL_SIZE.getNum()-1;j++){
                //当前棋子与右一格交换
                model.swapChessPiece(new ChessboardPoint(i,j),new ChessboardPoint(i,j+1));
                if (model.canSwap(new ChessboardPoint(i,j),new ChessboardPoint(i,j+1), model.getGrid())){
                    points.add(new ChessboardPoint(i,j));
                    points.add(new ChessboardPoint(i,j+1));
                }
                model.swapChessPiece(new ChessboardPoint(i,j),new ChessboardPoint(i,j+1));
            }
        }
        for (int i=0;i<Constant.CHESSBOARD_ROW_SIZE.getNum()-1;i++){
            for (int j=0;j<Constant.CHESSBOARD_COL_SIZE.getNum();j++){
                //当前棋子与下一格交换
                model.swapChessPiece(new ChessboardPoint(i,j),new ChessboardPoint(i+1,j));
                if (model.canSwap(new ChessboardPoint(i,j),new ChessboardPoint(i+1,j), model.getGrid())){
                    points.add(new ChessboardPoint(i,j));
                    points.add(new ChessboardPoint(i+1,j));
                }
                model.swapChessPiece(new ChessboardPoint(i,j),new ChessboardPoint(i+1,j));
            }
        }
        return points;
    }
    public int eliminateScore(ChessboardPoint point1,ChessboardPoint point2){
        int score = 0;
        redo = new Redo(model.convertBoardToList(),controller.frame,controller);
        Chessboard board = model;
        board.swapChessPiece(point1,point2);
        while (board.eliminateNum(board.getGrid())!=0 || !board.nullPoints(board.getGrid()).isEmpty()){
            score += 10*board.eliminateNum(board.getGrid());
            int[][] eliminate = board.search();
            //清除
            for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
                for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                    if (eliminate[i][j]==1){
                        board.removeChessPiece(new ChessboardPoint(i,j));
                    }
                }
            }
            //降落
            Cell[][] reverse = board.reverse(board.getGrid());
            for (int j = 0; j <Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                for (int e=0;e< board.nullInCol(reverse[j]);e++){
                    for (int i = Constant.CHESSBOARD_ROW_SIZE.getNum()-2; i >=0 ; i--) {
                        compare(new ChessboardPoint(i,j));
                    }
                }
            }
            if (board.eliminateNum(board.getGrid())==0){
                break;
            }
        }
        redo.load();
        return score;
    }
    public void compare(ChessboardPoint point){
        if (model.getChessPieceAt(point)!=null && model.getChessPieceAt(new ChessboardPoint(point.getRow()+1, point.getCol()))==null){
            model.swapChessPiece(point,new ChessboardPoint(point.getRow()+1, point.getCol()));
        }
    }
    public ArrayList<ChessboardPoint> hintPoints(ArrayList<ChessboardPoint> chesses){
        points = new ArrayList<ChessboardPoint>();
        if (chesses.isEmpty()){
            return points;
        }
        points.add(new ChessboardPoint(0,0));
        points.add(new ChessboardPoint(0,0));
        int score = 0;
        for (int i=0;i<chesses.size()-1;i+=2){
            int eliscore = eliminateScore(chesses.get(i),chesses.get(i+1));
            if (eliscore>score){
                score = eliscore;
                points.set(0,chesses.get(i));
                points.set(1,chesses.get(i+1));
            }
        }
        return points;
    }
    public ArrayList<ChessboardPoint> clickHint(){
        return hintPoints(canSwapChesses());
    }
}
