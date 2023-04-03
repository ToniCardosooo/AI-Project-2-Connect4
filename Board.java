import java.util.ArrayList;
import java.util.Collections;

public class Board{
    private int[][] b;
    private Board parent;
    private int[] pieces_column;
    private int lastMove;

    Board(int[][] in, int lm){
        b = new int[6][7];
        pieces_column = new int[7];
        for (int i = 0; i < 6; i++){
            for (int j = 0; j < 7; j++){
                b[i][j] = in[i][j];

                if (b[i][j] == 1 || b[i][j] == 2)
                    pieces_column[j]++;
            }
        }
        lastMove = lm;
        parent = null;
    }

    public boolean verifyColumnFull(int col){
        // if column 'col' is not full, return false
        // else return true
        if (col < 0 || col > 6) return true;
        return (!(pieces_column[col] < 6));
    }

    //getters
    public int[][] getBoard(){
        int[][] bb = new int[6][7];
        for (int i = 0; i < 6; i++){
            for (int j = 0; j < 7; j++){
                bb[i][j] = b[i][j];
            }
        }
        return bb;
    }

    public Board getParent(){ return parent; }

    public int[] getPiecesCol(){ return pieces_column; }

    public int getLastMove(){ return lastMove; }

    // setters
    public void setParent(Board p){ parent = p; }


    public Board makeMove(int col, int player){
        if (verifyColumnFull(col)) return null;
        int n = pieces_column[col];
        int[][] newboard = getBoard();
        newboard[5-n][col] = player;

        return new Board(newboard, col);
    }

    // function that gets all the available moves for that particular turn
    public ArrayList<Integer> getValidMoves(){
        ArrayList<Integer> moves = new ArrayList<Integer>();
        for(int i=0; i<7; i++){
            if(!verifyColumnFull(i)) moves.add(i);
        }
        Collections.shuffle(moves);
        return moves;
    }

    // to check if boards are equal
    @Override
    public boolean equals(Object obj) {
        Board other = (Board) obj;
        int[][] other_b = other.getBoard();
        for (int i = 0; i < 6; i++){
            for (int j = 0; j < 7; j++){
                if (this.b[i][j] != other_b[i][j]) return false;
            }
        }
        return true;
    }


    // print
    @Override
    public String toString(){
        String s = "";
        s += "# # # # # # # # # # # # # # #\n";
        for (int i = 0; i < 6; i++){
            s += "#";
            for (int j = 0; j < 7; j++){
                if (b[i][j] == 0) s += "   ";
                else if (b[i][j] == 1) s += " X ";
                else if (b[i][j] == 2) s += " O ";
                if (j!=6) s += "|";
            }
            s += "#\n";
        }
        s += "# # # # # # # # # # # # # # #\n";
        s += "  1   2   3   4   5   6   7\n";
        return s;
    }


}
