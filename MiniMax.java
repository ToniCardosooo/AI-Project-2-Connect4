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
    public int GetMove(Boolean AlphaBeta) { 
        return MiniMaxSearch(board, AlphaBeta); 
    }

    // MiniMax function that returns the best move
    private int MiniMaxSearch(Board board, Boolean AlphaBeta){
        int value = 513;
        int move = 0;
        int alpha = -513;
        int beta = 513;

        ArrayList<Integer> moves = board.getValidMoves();
        for(int i=0; i<moves.size(); i++){
            int m = moves.get(i);
            Board child = board.makeMove(m, 2);
            int score = MiniMaxSearch(child, depth, true, AlphaBeta, alpha, beta);
            if (score < value){
                value = score;
                beta = Math.min(beta, value);
                move = m;
            }
            if (AlphaBeta && value <= alpha) return move;
        }
        return move;
    }

    // MiniMax function
    private int MiniMaxSearch(Board board, int depth, Boolean MaxPlaying, Boolean AlphaBeta, int alpha, int beta){ 
        ArrayList<Integer> valid_moves = board.getValidMoves();
        
        // Winning move either from Max or Mini or depth limit has been reached 
        if(depth == 0 || Heuristics.isFinished(board)){
            return Heuristics.getScore(board);
        }
        
        // Max playing
        if(MaxPlaying){ 
            int value = -513;

            for(int i=0; i<valid_moves.size(); i++){
                Board new_board = board.makeMove(valid_moves.get(i), 1);
                int new_score = MiniMaxSearch(new_board, depth-1, false, AlphaBeta, alpha, beta);
                if(new_score > value){
                    value = new_score;
                    alpha = Math.max(alpha, value);
                }
                if (AlphaBeta && value >= beta) return value;
            }
            return value;
        }

        // Min playing
        else{
            int value = 513;

            for(int i=0; i<valid_moves.size(); i++){
                Board new_board = board.makeMove(valid_moves.get(i), 2);
                int new_score = MiniMaxSearch(new_board, depth-1, true, AlphaBeta, alpha, beta);
                if(new_score < value){
                    value = new_score;
                    beta = Math.min(beta, value);
                }
                if (AlphaBeta && value <= alpha) return value;
            }
            return value;
        }
    }
}
