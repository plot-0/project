package controller;

import model.ChessPiece;
import model.ChessboardPoint;
import model.Constant;
import view.GameFrame;

import java.util.List;
import java.util.Objects;

public class Redo {
    List<String> board;
    GameFrame frame;
    GameController controller;
    int score;
    int swaplimit;

    public Redo(List<String> board, GameFrame frame, GameController controller) {
        this.board = board;
        this.frame = frame;
        this.controller = controller;
        this.score=controller.score;
        this.swaplimit=GameController.swaplimit;
    }

    public void load(){
        controller.view.removeAllChessComponentsAtGrids();
        for (int i = 0; i< Constant.CHESSBOARD_ROW_SIZE.getNum(); i++){
            String[] line = board.get(i).split(" ");
            for (int j=0;j<Constant.CHESSBOARD_COL_SIZE.getNum();j++){
                if (line[j].equals("")){
                    controller.model.setChessPiece(new ChessboardPoint(i,j),null);
                }
                else{
                    controller.model.setChessPiece(new ChessboardPoint(i,j),new ChessPiece(Constant.colorMap3.get(Integer.parseInt(line[j]))));
                }
            }
        }
        controller.view.initiateChessComponent(controller.model);
        controller.score = score;
        controller.swaplimit = swaplimit;
        controller.scoreLabel.setText("Score:"+score);
        controller.swaplimitLabel.setText("Swap:"+swaplimit);
        frame.setSwaplimitLabel(swaplimit);
        frame.setScoreLabel(score);
        controller.view.repaint();
    }
}
