public class MiniMaxState implements Comparable<MiniMaxState> {
    private Board cur;
    private int score;
    private int level;

    // Construtor
    MiniMaxState(Board b, int l){
        cur = b;
        score = 0;
        level = l;
    }

    // Getters
    public Board getBoardObject(){return cur;}
    public int getScore(){return score;}
    public int getLevel(){return level;}

    // Setter
    public void setScore(int x){score = x;}


    //comparator
    public int compareTo(MiniMaxState other){
        int other_s = other.getScore();
        int cur_s = getScore();        
        if (cur_s != other_s) {
            return Integer.compare(cur_s, other_s);
        }

        Board other_board = other.getBoardObject();
        Board cur_board = cur;
        return cur_board.toString().compareTo(other_board.toString());
    }
    
}
