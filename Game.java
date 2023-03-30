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
        cur_b = new Board(b, -1);
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

        while (!Heuristics.isFinished(cur_b)){
            if (game.getPlayer() == 1){
                System.out.println(game.getBoard());
                System.out.println("It is now X's turn.");
                System.out.println("Make a move by choosing your coordinate to play (1 to 7).");

                int col = in.nextInt() - 1;
                Board b = game.getBoard();
                while (b.makeMove(col, player) == null) {
                    System.out.println("Invalid move.");
                    System.out.println("Please choose your coordinate to play (1 to 7) on an available column.");
                    col = in.nextInt() - 1;
                    b = game.getBoard();
                }
                game.setBoard(b.makeMove(col, player));
                game.switchPlayer();
            }

            else{
                System.out.println(game.getBoard());
                System.out.println("It is now O's turn.");

                int col = -1;
                if (game.getAlg() == 1){
                    MiniMax minimax = new MiniMax(cur_b, 7);
                    col = minimax.GetMove();
                }
                else if (game.getAlg() == 2){
                    // col = alphabeta
                }
                else if (game.getAlg() == 3){
                    MCTS carlotes = new MCTS(cur_b);
                    col = carlotes.playMCTS();
                }

                Board b = game.getBoard();
                game.setBoard(b.makeMove(col, player));
                game.switchPlayer();

            }
        }
    
        System.out.println(game.getBoard());
        if (Heuristics.getScore(cur_b) == 0)
                System.out.println("It's a draw!");
        else if (Heuristics.getScore(cur_b) == 512)
                System.out.println("Player X won!");
        else if (Heuristics.getScore(cur_b) == -512)
                System.out.println("Player O won!");
        
        in.close();
    }
}
 
