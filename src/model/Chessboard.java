package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    public void setGrid(Cell[][] grid) {
        this.grid = grid;
    }

    private void initGrid() {
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                grid[i][j] = new Cell();
            }
        }
    }

    public void initPieces() {
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                grid[i][j].setPiece(new ChessPiece( Util.RandomPick(new String[]{"💎", "⚪", "▲", "🔶"})));
            }
        }
        //todo: check
        while(eliminateNum(grid)!=0){
            for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
                for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                    grid[i][j].setPiece(new ChessPiece( Util.RandomPick(new String[]{"💎", "⚪", "▲", "🔶"})));
                }
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
        //todo:check?
            var p1 = getChessPieceAt(point1);
            var p2 = getChessPieceAt(point2);
            setChessPiece(point1, p2);
            setChessPiece(point2, p1);
    }
    public boolean canSwap(ChessboardPoint point1,ChessboardPoint point2,Cell[][] grid){
        int [][] eliminate = search();
        if (eliminate[point1.getRow()][point1.getCol()]==1){
            return true;
        }
        if (eliminate[point2.getRow()][point2.getCol()]==1){
            return true;
        }
        else{
            return false;
        }
    }
    public Cell[][] reverse(Cell[][] grid){
        Cell[][] reverse = new Cell[Constant.CHESSBOARD_COL_SIZE.getNum()][Constant.CHESSBOARD_ROW_SIZE.getNum()];
        for (int j = 0; j <Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
            for (int i = 0;i<Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
                reverse[j][i] = getGridAt(new ChessboardPoint(i,j));
            }
        }
        return reverse;
    }
    public int[][] search(){
        int sum = 0;
        //打墙,-1表示外层的墙
        String[][] check= new String[Constant.CHESSBOARD_ROW_SIZE.getNum()+4][Constant.CHESSBOARD_COL_SIZE.getNum()+4];
        for (int i=0;i<Constant.CHESSBOARD_ROW_SIZE.getNum()+2;i++){
            for (int j =0;j<Constant.CHESSBOARD_COL_SIZE.getNum()+2;j++){
                check[i][j]="-1";
            }
        }
        for (int i=2;i<Constant.CHESSBOARD_ROW_SIZE.getNum()+2;i++){
            for (int j =2;j<Constant.CHESSBOARD_COL_SIZE.getNum()+2;j++){
                if (grid[i-2][j-2].getPiece()==null){
                    check[i][j]="-1";
                    continue;
                }
                check[i][j] = grid[i-2][j-2].getPiece().getName();
                sum++;
            }
        }
        //用无墙数组标记需要清除的点，0无需清除，1需要清除
        int[][] eliminate = new int[Constant.CHESSBOARD_ROW_SIZE.getNum()][Constant.CHESSBOARD_COL_SIZE.getNum()];
        for (int i=2;i<Constant.CHESSBOARD_ROW_SIZE.getNum()+2;i++){
            for (int j =2;j<Constant.CHESSBOARD_COL_SIZE.getNum()+2;j++){
                //无效图(空图)
                if (sum==0){
                    break;
                }
                if (Objects.equals(check[i][j], "-1")){
                    eliminate[i-2][j-2] = 0;
                    continue;
                }
                //向上
                if (check[i][j].equals(check[i-1][j]) && check[i-1][j].equals(check[i-2][j])){
                    eliminate[i-2][j-2] = 1;
                }
                //向下
                if (check[i][j].equals(check[i+1][j]) && check[i+1][j].equals(check[i+2][j])){
                    eliminate[i-2][j-2] = 1;
                }
                //向右
                if (check[i][j].equals(check[i][j+1]) && check[i][j+1].equals(check[i][j+2])){
                    eliminate[i-2][j-2] = 1;
                }
                //向左
                if (check[i][j].equals(check[i][j-1]) && check[i][j-1].equals(check[i][j-2])){
                    eliminate[i-2][j-2] = 1;
                }
                //十字上下
                if (check[i][j].equals(check[i-1][j]) && check[i][j].equals(check[i+1][j])){
                    eliminate[i-2][j-2] = 1;
                }
                //十字左右
                if (check[i][j].equals(check[i][j-1]) && check[i][j].equals(check[i][j+1])){
                    eliminate[i-2][j-2] = 1;
                }
            }
            if (sum==0){
                break;
            }
        }
        return eliminate;
    }
    //检测图是否有效
    public int eliminateNum(Cell[][] grid){
        int num = 0;
        int[][] eliminate = search();
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                if (eliminate[i][j]==1){
                    num++;
                }
            }
        }
        return num;
    }
    public int nullInCol(Cell[] col){
        int num =0;
        for (int i =0;i<col.length;i++){
            if (col[i].getPiece()==null){
                num++;
            }
        }
        return num;
    }
    public ArrayList<ChessboardPoint> nullPointInCol(Cell[] colcomponent, int col){
        ArrayList<ChessboardPoint> points = new ArrayList<ChessboardPoint>();
        for (int i =0;i<colcomponent.length;i++){
            if (colcomponent[i] == null){
                points.add(new ChessboardPoint(i,col));
            }
        }
        return points;
    }
    public ArrayList<ChessboardPoint> nullPoints(Cell[][] grid){
        ArrayList<ChessboardPoint> points = new ArrayList<ChessboardPoint>();
        for (int i =0;i<Constant.CHESSBOARD_ROW_SIZE.getNum();i++){
            for (int j =0;j<Constant.CHESSBOARD_COL_SIZE.getNum();j++){
                if (grid[i][j].getPiece() == null){
                    points.add(new ChessboardPoint(i,j));
                }
            }
        }
        return points;
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
                    sb.append(piece.getId());
                    sb.append(" ");
                }
                else{
                    sb.append(" ");
                }
            }
           saveLines.add(sb.toString());
        }
        sb.setLength(0);
        return saveLines;
    }

}
