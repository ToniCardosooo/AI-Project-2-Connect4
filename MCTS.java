import java.util.LinkedList;
import java.util.PriorityQueue;

public class MCTS{
    private MCTSState root;

    MCTS(Board r){
        root = new MCTSState(r, 2, null);
    }

    private MCTSState selection(){
        MCTSState cur = root;
        // while it's not a leaf
        while (cur.getChildren().size() > 0){
            PriorityQueue<MCTSState> cur_children = cur.getChildrenCopy();
            LinkedList<MCTSState> max_nodes = new LinkedList<MCTSState>();
            double max_value = cur_children.peek().getUCT();

            // gather in the list all the best nodes
            while (cur_children.peek() != null && cur_children.peek().getUCT() == max_value)
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

    private MCTSState expand(MCTSState leaf){
        if (Heuristics.isFinished(leaf.getBoardObject()))
            return null;

        Board leaf_b = leaf.getBoardObject();
        int player = leaf.getPlayer();
        PriorityQueue<MCTSState> leaf_children = leaf.getChildren();

        // create children
        for (int i = 0; i < 7; i++){
            Board aux = leaf_b.makeMove(i, player);
            if (aux == null) continue;
            aux.setParent(leaf_b);
            MCTSState child = new MCTSState(aux, 3-player, leaf);
            leaf_children.add(child);
        }

        // randomly choose and return a child node
        int rand_child = (int)(Math.random() * leaf.getChildren().size());
        PriorityQueue<MCTSState> children = leaf.getChildrenCopy();
        for (int i = 0; i < rand_child; i++)
            children.poll();
        return children.poll();
    }   

    private int rollout(MCTSState child){
        // simulate
        Board cur = child.getBoardObject();
        int player = child.getPlayer();

        // randomly play
        while (!Heuristics.isFinished(cur)){
            int rand_col = (int)(Math.random() * 7);
            Board new_b = cur.makeMove(rand_col, player);
            while (new_b == null){
                rand_col = (int)(Math.random() * 7);
                new_b = cur.makeMove(rand_col, player);
            }
            cur = new_b;
            player = 3-player;
        }

        // return the player that won
        return 3-player;
    }

    // function to reorder the children priority queue after node's backpropagation
    private PriorityQueue<MCTSState> reorderChildren(PriorityQueue<MCTSState> children){
        PriorityQueue<MCTSState> ordered = new PriorityQueue<MCTSState>();

        while (children.size() > 0){
            ordered.add(children.poll());
        }

        return  ordered;
    }

    private void backpropagate(MCTSState child, int outcome){
        int reward = 1;
        if (outcome == child.getPlayer()) reward = 0;

        while (child != null){
            child.addToN(1);
            child.addToU(reward);

            // reorder the priority queue on the child's parent
            if (child.getParent() != null){
                child.getParent().setChildren(
                    reorderChildren(child.getParent().getChildren())
                );
            }
                
            child = child.getParent();
            // if it is a draw, count as loss for every state
            if (outcome == 0) reward = 0;
            else reward = 1-reward;
        }

    }

    public int playMCTS(){
        for (int i = 0; i < MCTSConstants.MCTS_ITERATIONS; i++){
            MCTSState leaf = selection();
            
            // initialize with a value in case the leaf is terminal
            // leaf does have a child
            if (!Heuristics.isFinished(leaf.getBoardObject())){
                MCTSState child = expand(leaf);
                int rollout_value = rollout(child);
                backpropagate(child, rollout_value);
            }
            else{
                // leaf is a final state
                int outcome = 0; // board full and tie
                if (Heuristics.getScore(leaf.getBoardObject()) == +512) outcome = 1; // X won
                else if (Heuristics.getScore(leaf.getBoardObject()) == -512) outcome = 2; // O won
                backpropagate(leaf, outcome);
            }   
        }

        MCTSState result = root.getChildren().peek();
        
        return result.getBoardObject().getLastMove();
    }

}
