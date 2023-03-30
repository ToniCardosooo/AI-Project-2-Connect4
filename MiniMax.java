public class MiniMax {
    private Board board;
    private int depth;

    // Constructor
    MiniMax(Board b){
        board = b;
        depth = 3;
    }

    // Getter 
    public int GetMove() { return MiniMaxSearch(board, depth, true); }

    // Function that gets all the valid moves for this board
    private int[] getValidMoves(Board board){
        int count = 0;
        for(int i=0; i<6; i++){
            if(!board.verifyColumnFull(i)) count++; 
        }

        int[] valid_moves = new int[count];
        int pos = 0;

        for(int j=0; j<6; j++){
            if(!board.verifyColumnFull(j)) valid_moves[pos++] = j;
        }
        return valid_moves;
    }    

    // MiniMax function
    private int MiniMaxSearch(Board board, int depth, Boolean MaxPlaying){ 
        int[] valid_moves = this.getValidMoves(board);
        
        // Winning move either from Max or Mini or depth limit has been reached 
        if(depth == 0 || Math.abs(Heuristics.getScore(board)) == 512){
            return Heuristics.getScore(board);
        }
        
        // Max playing
        if(MaxPlaying){ 
            int value = -100000000;
            int best_column = valid_moves[0];

            for(int i=0; i<valid_moves.length; i++){
                Board new_board = board.makeMove(valid_moves[i], 2);
                int new_score = MiniMaxSearch(new_board, depth-1, false);
                if(new_score > value){
                    value = new_score;
                    best_column = valid_moves[i];
                }
            }
            return best_column;
        }

        // Min playing
        else{
            int value = 100000000;
            int best_column = valid_moves[0];

            for(int i=0; i<valid_moves.length; i++){
                Board new_board = board.makeMove(valid_moves[i], 1);
                int new_score = MiniMaxSearch(new_board, depth-1, true);
                if(new_score < value){
                    value = new_score;
                    best_column = valid_moves[i];
                }
            }
            return best_column;
        }
    }
}
