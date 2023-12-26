package listener;

import model.ChessboardPoint;
import view.CellComponent;
import view.ChessComponent;

import java.awt.*;

public interface GameListener {

    void onPlayerClickCell(ChessboardPoint point, CellComponent component);


    void onPlayerClickChessPiece(ChessboardPoint point, ChessComponent component);
    void onPlayerEnteredChessPiece(ChessboardPoint point, ChessComponent component);
//    void onPlayerExitedChessPiece(ChessboardPoint point, ChessComponent component);


    public void onPlayerSwapChess();

    public void onPlayerNextStep();

}
