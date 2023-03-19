public class Heuristics {

    private static int win(Board b){
        int[][] bb = b.getBoard();

        //horizontal
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 4; j++) {
                if (bb[i][j] != 0 && bb[i][j] == bb[i][j+1] && bb[i][j] == bb[i][j+2] && bb[i][j] == bb[i][j+3]) {
                    if (bb[i][j] == 1) return 1;
                    else return -1;
                }
            }
        }

        for (int i = 0; i < 7; i++) {
            if (b.getPiecesCol()[i] < 4) return 0;
        }

        //vertical
        for (int i = 3; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                if (bb[i][j] != 0 && bb[i][j] == bb[i-1][j] && bb[i][j] == bb[i-2][j] && bb[i][j] == bb[i-3][j]) {
                    if (bb[i][j] == 1) return 1;
                    else return -1;
                }
            }
        }

        // diagonals
        for (int i = 3; i < 6; i++) {
            //if col tem menos de 4 continue 
            for (int j = 0; j < 4; j++) {
                if (bb[i][j] != 0 && bb[i][j] == bb[i-1][j+1] && bb[i][j] == bb[i-2][j+2] && bb[i][j] == bb[i-3][j+3]) {
                    
                    if (bb[i][j] == 1) return 1;
                    else return -1;
                }
                if (bb[i][j+3] != 0 && bb[i][j+3] == bb[i-1][j+2] && bb[i][j+3] == bb[i-2][j+1] && bb[i-1][j+3] == bb[i-3][j]) {
                    if (bb[i][j] == 1) return 1;
                    else return -1;
                }
            }
        }

        return 0;
    }

    private static int score(Board b){
        int count = 0;
        for (int i = 0; i < 7; i++) {
            count += b.getPiecesCol()[i];
        }
        if (count > 6) {
            if (win(b) != 0) return win(b)*512;
        }



        return 0;
    }

    public static int getScore(Board b){
        return score(b);
    }

}
