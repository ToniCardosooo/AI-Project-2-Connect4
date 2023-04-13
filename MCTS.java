import java.util.ArrayList;
import java.util.Collections;

public class MCTS{
    private MCTSState root;

    MCTS(Board r){
        root = new MCTSState(r, 2, null);
    }

    private MCTSState select(){
        MCTSState cur = root;

        // while it's not a leaf
        while (cur.getChildren().size() > 0){
            ArrayList<MCTSState> cur_children = cur.getChildren();
            Collections.sort(cur_children);

            // gather in a list all the best nodes
            ArrayList<MCTSState> max_nodes = new ArrayList<MCTSState>();
            double max_value = cur_children.get(0).getUCT();
            for (int i = 0; i < cur_children.size(); i++){
                if (cur_children.get(i).getUCT() < max_value) break;
                max_nodes.add(cur_children.get(i));
            }
            
            // choose a random best node
            Collections.shuffle(max_nodes);
            cur = max_nodes.get(0);

            // if it hasn't been explored yet
            if (cur.getN() == 0) return cur;
        }

        // cur is a leaf
        return cur;
    }

    private MCTSState expand(MCTSState leaf){
        if (Heuristics.isFinished(leaf.getBoardObject()))
            return leaf;

        Board leaf_b = leaf.getBoardObject();
        int player = leaf.getPlayer();
        ArrayList<MCTSState> leaf_children = leaf.getChildren();

        // create children
        for (int i : leaf_b.getValidMoves()){
            Board aux = leaf_b.makeMove(i, player);
            aux.setParent(leaf_b);
            MCTSState child = new MCTSState(aux, 3-player, leaf);
            leaf_children.add(child);
        }

        // randomly choose and return a child node
        Collections.shuffle(leaf_children);
        return leaf_children.get(0);
    }   

    private int rollout(MCTSState child){
        // simulate
        Board cur = child.getBoardObject();
        int player = child.getPlayer();

        // randomly play
        while (!Heuristics.isFinished(cur)){
            int rand_col = cur.getValidMoves().get(0);
            cur = cur.makeMove(rand_col, player);
            player = 3-player;
        }
        
        // tie
        if (Math.abs(Heuristics.getScore(cur)) != 512) return 0;

        // return the player that won
        return 3-player;
    }

    private void backpropagate(MCTSState child, int outcome){
        int reward = 1;
        if (outcome == child.getPlayer()) reward = 0;

        while (child != null){
            child.addToN(1);
            child.addToU(reward);

            // reorder the children list on the child's parent
            if (child.getParent() != null)
                Collections.sort(child.getParent().getChildren());
                
            child = child.getParent();
            // if it is a draw, count as loss for every state
            if (outcome == 0) reward = 0;
            else reward = 1-reward;
        }
    }

    public int playMCTS(){
        for (int i = 0; i < MCTSConstants.MCTS_ITERATIONS; i++){
            MCTSState leaf = select();
            MCTSState child = expand(leaf);
            int rollout_value = rollout(child);
            backpropagate(child, rollout_value);
        }

        // move with highest number of rollouts
        MCTSState result = null;
        double top_number_rollouts = 0.0;
        for (MCTSState child : root.getChildren()){
            if (child.getN() > top_number_rollouts){
                top_number_rollouts = child.getN();
                result = child;
            }
        }
        return result.getBoardObject().getLastMove();
    }
}
