public class Heuristics {
    
    public static boolean isFinished(Board b){
        if (win(b) != 0) return true;
        boolean full = true;
        for (int i = 0; i < 7; i++) if (!b.verifyColumnFull(i)) full = false;
        return full;
    }

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

        //vertical
        for (int i = 3; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                if (b.getPiecesCol()[j] < 4) continue;
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
                if (bb[i][j+3] != 0 && bb[i][j+3] == bb[i-1][j+2] && bb[i][j+3] == bb[i-2][j+1] && bb[i][j+3] == bb[i-3][j]) {
                    if (bb[i][j+3] == 1) return 1;
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
            int s = win(b);
            if (s != 0) return s*512;
        }

        int[][] bb = b.getBoard();
        int count1, count2;
        int score = 0;

        //horizontal
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 4; j++) {
                count1 = 0;
                count2 = 0;
                if (bb[i][j] == 1) count1++;
                if (bb[i][j+1] == 1) count1++;
                if (bb[i][j+2] == 1) count1++;
                if (bb[i][j+3] == 1) count1++;
                if (bb[i][j] == 2) count2++;
                if (bb[i][j+1] == 2) count2++;
                if (bb[i][j+2] == 2) count2++;
                if (bb[i][j+3] == 2) count2++;

                if (count1 != 0 && count2 != 0) continue;
                if (count1 == 1) score++;
                if (count2 == 1) score--;
                if (count1 == 2) score+=10;
                if (count2 == 2) score-=10;
                if (count1 == 3) score+=50;
                if (count2 == 3) score-=50;
            }
        }

        //vertical
        for (int i = 3; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                count1 = 0;
                count2 = 0;
                if (bb[i][j] == 1) count1++;
                if (bb[i-1][j] == 1) count1++;
                if (bb[i-2][j] == 1) count1++;
                if (bb[i-3][j] == 1) count1++;
                if (bb[i][j] == 2) count2++;
                if (bb[i-1][j] == 2) count2++;
                if (bb[i-2][j] == 2) count2++;
                if (bb[i-3][j] == 2) count2++;

                if (count1 != 0 && count2 != 0) continue;
                if (count1 == 1) score++;
                if (count2 == 1) score--;
                if (count1 == 2) score+=10;
                if (count2 == 2) score-=10;
                if (count1 == 3) score+=50;
                if (count2 == 3) score-=50;
            }
        }

        // diagonals
        for (int i = 3; i < 6; i++) {
            //if col tem menos de 4 continue 
            for (int j = 0; j < 4; j++) {
                count1 = 0;
                count2 = 0;
                if (bb[i][j] == 1) count1++;
                if (bb[i-1][j+1] == 1) count1++;
                if (bb[i-2][j+2] == 1) count1++;
                if (bb[i-3][j+3] == 1) count1++;
                if (bb[i][j] == 2) count2++;
                if (bb[i-1][j+1] == 2) count2++;
                if (bb[i-2][j+2] == 2) count2++;
                if (bb[i-3][j+3] == 2) count2++;

                if (count1 != 0 && count2 != 0) continue;
                if (count1 == 1) score++;
                if (count2 == 1) score--;
                if (count1 == 2) score+=10;
                if (count2 == 2) score-=10;
                if (count1 == 3) score+=50;
                if (count2 == 3) score-=50;

                count1 = 0;
                count2 = 0;
                if (bb[i-3][j] == 1) count1++;
                if (bb[i-2][j+1] == 1) count1++;
                if (bb[i-1][j+2] == 1) count1++;
                if (bb[i][j+3] == 1) count1++;
                if (bb[i-3][j] == 2) count2++;
                if (bb[i-2][j+1] == 2) count2++;
                if (bb[i-1][j+2] == 2) count2++;
                if (bb[i][j+3] == 2) count2++;

                if (count1 != 0 && count2 != 0) continue;
                if (count1 == 1) score++;
                if (count2 == 1) score--;
                if (count1 == 2) score+=10;
                if (count2 == 2) score-=10;
                if (count1 == 3) score+=50;
                if (count2 == 3) score-=50;
            }
        }

        return score;
    }

    public static int getScore(Board b){
        return score(b);
    }

}
