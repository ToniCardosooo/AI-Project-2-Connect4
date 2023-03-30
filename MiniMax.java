import java.util.*;

public class MiniMax {
    private Board board;
    private int depth;

    // Constructor
    MiniMax(Board b, int d){
        board = b;
        depth = d;
    }

    // Getter 
    public int GetMove() { 
        return MiniMaxSearch(board, depth, true); 
    }

    // Function that gets all the available moves for that particular turn
    private ArrayList<Integer> getValidMoves(Board board){
        ArrayList<Integer> moves = new ArrayList<Integer>();
        for(int i=0; i<6; i++){
            if(!board.verifyColumnFull(i)) moves.add(i);
        }
        return moves;
    }

    // MiniMax function
    private int MiniMaxSearch(Board board, int depth, Boolean MaxPlaying){ 
        ArrayList<Integer> valid_moves = this.getValidMoves(board);
        
        // Winning move either from Max or Mini or depth limit has been reached 
        if(depth == 0 || Math.abs(Heuristics.getScore(board)) == 512){
            return Heuristics.getScore(board);
        }
        
        // Max playing
        if(MaxPlaying){ 
            int value = -100000000;
            int best_column = valid_moves.get(0);

            for(int i=0; i<valid_moves.size(); i++){
                Board new_board = board.makeMove(valid_moves.get(i), 1);
                int new_score = MiniMaxSearch(new_board, depth-1, false);
                if(new_score > value){
                    value = new_score;
                    best_column = valid_moves.get(i);
                }
            }
            return best_column;
        }

        // Min playing
        else{
            int value = 100000000;
            int best_column = valid_moves.get(0);

            for(int i=0; i<valid_moves.size(); i++){
                Board new_board = board.makeMove(valid_moves.get(i), 2);
                int new_score = MiniMaxSearch(new_board, depth-1, true);
                if(new_score < value){
                    value = new_score;
                    best_column = valid_moves.get(i);
                }
            }
            return best_column;
        }
    }
}
