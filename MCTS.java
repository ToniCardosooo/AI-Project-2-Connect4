import java.util.LinkedList;
import java.util.PriorityQueue;

public class MCTS{
    private MCTSState root;
    private MCTSState cur;

    MCTS(Board r){
        root = new MCTSState(r, 2, null);
    }

    private MCTSState selection(){
        cur = root;
        // while it's not a leaf
        while (cur.getChildren().size() > 0){
            PriorityQueue<MCTSState> cur_children = cur.getChildrenCopy();
            LinkedList<MCTSState> max_nodes = new LinkedList<MCTSState>();
            double max_value = cur_children.peek().getUCT();

            // gather in the list all the best nodes
            while (cur_children.peek().getUCT() == max_value)
                max_nodes.addFirst(cur_children.poll());
            
            // choose a random best node
            int random_node = (int)(Math.random() * max_nodes.size());
            for (int i = 0; i < random_node; i++)
                max_nodes.removeFirst();
            cur = max_nodes.removeFirst();

            // if it hasn't been explored yet
            if (cur.getN() == 0) return cur;
        }

        // cur is a leaf
        return cur;
    }

    private int rollout(MCTSState leaf){
        // choose a random child node
        int rand_child = (int)(Math.random() * leaf.getChildren().size());
        PriorityQueue<MCTSState> children = leaf.getChildrenCopy();
        for (int i = 0; i < rand_child; i++)
            children.poll();
        MCTSState child = children.poll();

        // simulate
        Board cur = child.getBoardObject();
        int player = child.getPlayer();
        int score = Heuristics.getScore(cur);

        // randomly play
        while (score != 0 && score != 512 && score != -512){
            int rand_col = (int)(Math.random() * 7);
            Board new_b = cur.makeMove(rand_col, player);
            while (new_b == null){
                rand_col = (int)(Math.random() * 7);
                new_b = cur.makeMove(rand_col, player);
            }
            cur = new_b;
            player = 3-player;
            score = Heuristics.getScore(cur);
        }
        // return the player that won
        return 3-player;
    }

    private void backpropagate(MCTSState leaf){

    }

    public int playMCTS(){
        MCTSState leaf = selection();
        leaf.expand();
        int rollout_value = rollout(leaf);
        // backpropagation

        return 0;
    }

}
