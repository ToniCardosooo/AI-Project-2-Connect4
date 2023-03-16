import java.util.Scanner;

public class Game {
    private static Board cur_b;
    private static int player;
    private static int alg;

    Game(int a){
        int[][] b = {
            {0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0}
        };
        cur_b = new Board(b);
        player = 1;
        alg = a;
    }

    // getters
    public Board getBoard(){return cur_b;}
    public int getPlayer(){return player;}
    public int getAlg(){return alg;}

    // setters
    public void setBoard(Board b){cur_b = b;}
    public void switchPlayer(){
        if (player == 1) player = 2;
        else player = 1;
    }


    private static boolean isFinished(){return false;}

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        Game game = null;
        switch (args[0]) {
            case "MiniMax":
                game = new Game(1);
                break;

            case "AlphaBeta":
                game = new Game(2);
                break;

            case "MCTS":
                game = new Game(3);
                break;

            default:
                break;
        }

        if (game == null){
            System.out.println("Invalid input");
            in.close();
            return;
        }

        while (!isFinished()){
            if (game.getPlayer() == 1){
                System.out.println(game.getBoard());
                System.out.println("It is now X's turn.");
                System.out.println("Make a move by choosing your coordinated to play (1 to 7).");

                int col = in.nextInt() - 1;
                Board b = game.getBoard();
                game.setBoard(b.makeMove(col, player));
                game.switchPlayer();
            }

            else{
                System.out.println(game.getBoard());
                System.out.println("It is now O's turn.");

                int col = -1;
                if (game.getAlg() == 1){
                    // col = minimax
                }
                else if (game.getAlg() == 2){
                    // col = alphabeta
                }
                else if (game.getAlg() == 3){
                    // col = mcts
                }

                Board b = game.getBoard();
                game.setBoard(b.makeMove(col, player));
                game.switchPlayer();
            }
        }

        /*
         * 
         * if score == 0:
         *      print "draw"
         * if score == +512:
         *      print "Player X won"
         * if score == -512:
         *      print "Player O won"
         * 
         */
        
        in.close();
    }
}
 