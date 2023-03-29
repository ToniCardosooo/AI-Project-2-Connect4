import java.util.PriorityQueue;

/*
 * Ideas
 * 
 * - Each node has a priority queue that saves the reference to its children
 *   and orders it based on the game policy (heuristic)
 * 
 * - In the Backpropagation phase, to update the Priority Queue of the node's children
 *   we can remove and insert the head of the PQ in order to reorder it based on the
 *   backprop results
 * 
 * - In the rool-out phase we CANNOT save the roll-out states in memory. The roll-out should
 *   happen in a single varible that updates itself to each next move until the player wins, draws or loses.
 * 
 * - The class that will be developed to hold the MCTS algorithm will need an
 *   atribute that represents how many cycles of "Selection, Expantion, Roll-Out, BackProp"
 *   the algorithm will use
 * 
 */

public class MCTSState implements Comparable<MCTSState>{

    // atributes
    private Board cur_b;
    private int player;
    private MCTSState parent;
    private PriorityQueue<MCTSState> children;
    private double u, n;

    // constructor
    MCTSState(Board b, int play, MCTSState p){
        cur_b = b;
        player = play;
        parent = p;
        children = new PriorityQueue<MCTSState>();
        u = 0; n = 0;
    }

    // getters
    public Board getBoardObject(){return cur_b;}
    public int getPlayer(){return player;}
    public MCTSState getParent(){return parent;}

    public PriorityQueue<MCTSState> getChildren(){return children;}
    public PriorityQueue<MCTSState> getChildrenCopy(){
        PriorityQueue<MCTSState> copy = new PriorityQueue<>();
        for (MCTSState s : children)
            copy.add(s);
        return copy;
    }

    public double getU(){return u;}
    public double getN(){return n;}

    // modifiers
    public void addToU(int x){u += x;}
    public void addToN(int x){n += x;}


    public void expand(){
        for (int i = 0; i < 7; i++){
            Board aux = cur_b.makeMove(i, 2);
            if (aux == null) continue;
            aux.setParent(cur_b);
            MCTSState child = new MCTSState(aux, 3-player, this);
            children.add(child);
        }
    }

    // calculate Upper Confindence Bound for Trees (UCT)
    public double getUCT(){
        // hasn't been explored yet
        if (this.getN() == 0) return (double) MCTSConstants.INF;
        // else
        double uct = (
            this.getU()/this.getN() + 
            MCTSConstants.EXPLORATION * Math.sqrt(
                Math.log10(this.getN()/this.parent.getN())
            )
        );
        return uct;
    }
    
    // comparador
    @Override
    public int compareTo(MCTSState other){
        return Double.compare(this.getUCT(), other.getUCT());
    }
}