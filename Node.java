import java.util.*;
/**
 * @author Zanyar Sherwani
 *
 */
public class Node{
    // Point represents an object where this node's
    // coordinates are in the grid  
    public Point p;
    // the cheapest hop for this node
    public Node bestHop;
    // the total cost of the path thusfar, starting 
    // from the bottm
    public int cost;
    // the node below this, points to this node
    // as it's best hop. Essentially the paths
    // be a doubly linked list
    private Node below;
    
    // constructor that instantiates point with the
    // passed in coordinates
    public Node(int r, int c, Node bestHop, int cost){
        p = new Point(r, c);
        this.bestHop = bestHop;
        this.cost = cost;
        this.below = null; 
    }
    

    public void setBestHop(Node bestHop){
        this.bestHop = bestHop;
    }

    public Node getBestHop(){
        return this.bestHop;
    }
    public int getCost(){
        return this.cost;
    }
    public void setCost(int cost){
        this.cost = cost;
    }
    
    public Point getPoint(){
        return this.p;
    }
     
    public void setBelow(Node b){
        this.below = b;
    }
    
    public Node getBelow(){
        return this.below;
    }


}
