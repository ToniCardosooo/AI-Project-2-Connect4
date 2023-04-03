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
        return MiniMaxSearch(board); 
    }

    private int MiniMaxSearch(Board board){
        long startTime = System.nanoTime();
        int value = 10000000;
        int move = 0;

        ArrayList<Integer> moves = board.getValidMoves();
        for(int i=0; i<moves.size(); i++){
            int m = moves.get(i);
            Board child = board.makeMove(m, 2);
            int score = MiniMaxSearch(child, depth, true);
            if(score < value){
                value = score;
                move = m;
            }
        }
        long endTime = System.nanoTime();
        System.out.println("Time = " + (endTime-startTime)/1000000 + "ms");
        return move;
    }

    // MiniMax function
    private int MiniMaxSearch(Board board, int depth, Boolean MaxPlaying){ 
        ArrayList<Integer> valid_moves = board.getValidMoves();
        
        // Winning move either from Max or Mini or depth limit has been reached 
        if(depth == 0 || Heuristics.isFinished(board)){
            return Heuristics.getScore(board);
        }
        
        // Max playing
        if(MaxPlaying){ 
            int value = -10000000;

            for(int i=0; i<valid_moves.size(); i++){
                Board new_board = board.makeMove(valid_moves.get(i), 1);
                int new_score = MiniMaxSearch(new_board, depth-1, false);
                if(new_score > value){
                    value = new_score;
                }
            }
            return value;
        }

        // Min playing
        else{
            int value = 10000000;

            for(int i=0; i<valid_moves.size(); i++){
                Board new_board = board.makeMove(valid_moves.get(i), 2);
                int new_score = MiniMaxSearch(new_board, depth-1, true);
                if(new_score < value){
                    value = new_score;
                }
            }
            return value;
        }
    }
}
