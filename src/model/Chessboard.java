package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This class store the real chess information.
 * The Chessboard has 8 * 8 cells, and each cell has a position for chess
 */
public class Chessboard {
    private Cell[][] grid;
    StringBuilder sb;

    public Chessboard() {
        sb = new StringBuilder();
        this.grid =
                new Cell[Constant.CHESSBOARD_ROW_SIZE.getNum()][Constant.CHESSBOARD_COL_SIZE.getNum()];

        initGrid();
        initPieces();
    }

    private void initGrid() {
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                grid[i][j] = new Cell();
            }
        }
    }

    private void initPieces() {
        //todo: check
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                grid[i][j].setPiece(new ChessPiece( Util.RandomPick(new String[]{"💎", "⚪", "▲", "🔶"})));
            }
        }

    }

    public ChessPiece getChessPieceAt(ChessboardPoint point) {
        return getGridAt(point).getPiece();
    }

    public Cell getGridAt(ChessboardPoint point) {
        return grid[point.getRow()][point.getCol()];
    }

    private int calculateDistance(ChessboardPoint src, ChessboardPoint dest) {
        return Math.abs(src.getRow() - dest.getRow()) + Math.abs(src.getCol() - dest.getCol());
    }

    public ChessPiece removeChessPiece(ChessboardPoint point) {
        ChessPiece chessPiece = getChessPieceAt(point);
        getGridAt(point).removePiece();
        return chessPiece;
    }

    public void setChessPiece(ChessboardPoint point, ChessPiece chessPiece) {
        getGridAt(point).setPiece(chessPiece);
    }


    public void swapChessPiece(ChessboardPoint point1, ChessboardPoint point2) {
        if (canSwap(point1,point2)){
            var p1 = getChessPieceAt(point1);
            var p2 = getChessPieceAt(point2);
            setChessPiece(point1, p2);
            setChessPiece(point2, p1);
        }
    }
    public boolean canSwap(ChessboardPoint point1,ChessboardPoint point2){
        //todo:  check
        return true;
    }


    public Cell[][] getGrid() {
        return grid;
    }


    public List<String> convertBoardToList() {
        List<String> saveLines = new ArrayList<>();
        for (int i=0;i<Constant.CHESSBOARD_ROW_SIZE.getNum();i++){
            sb.setLength(0);
            for (int j=0;j<Constant.CHESSBOARD_COL_SIZE.getNum();j++){
                ChessPiece piece = grid[i][j].getPiece();
                if (piece != null){
                    sb.append(piece.getName()).append(" ");
                }
                else{
                    sb.append("0 ");
                }
            }
            saveLines.add(sb.toString());
        }
        sb.setLength(0);
        return saveLines;
    }
}
