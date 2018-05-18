/**
 *
 * @author Zanyar Sherwani
 *
 */
import java.util.*;
import java.io.*;
import java.lang.*;

public class P1{
    
    // This method returns the energy at the given point
    // in the grid. This method calculates an energy at 
    // a given point by using the energies at the points
    // above, below, right and left. If a point is located
    // on an edge then the above, below, left or right may
    // wrap around
    public static int energyAt(Grid <RGB> grid, int r, int c){
        int energy, h_energy, v_energy = 0;
        // these variables are just to get the proper coordinates
        // there is some wasted stack space here because not this
        // many vars are needed but too late!
        int R_left, R_right, R_above, R_below = 0;
        int G_left, G_right, G_above, G_below = 0;
        int B_left, B_right, B_above, B_below = 0;
        // first corner case is if the point is the
        // very first element in the grid
        // in this case the above would wrap around to be
        // the last element in this element's column and the
        // left would wrap around to the element that is the
        // very last in the same row
        if(r == 0 && c ==0){
            B_above = G_above = R_above = grid.height()-1;
            B_left = G_left = R_left = grid.width()-1;
            B_below = G_below = R_below = r+1;
            B_right = G_right = R_right = c+1;
        }
        // if a point is the very last element in the top row
        // then the above and right points would wrap around
        else if(r == 0 && c == grid.width()-1){
            B_above = G_above = R_above = grid.height()-1;
            B_right = G_right = R_right = 0;
            B_below = G_below = R_below = r+1;
            B_left = G_left = R_left = c-1;
        }
        // if a point is the very first in the last row then 
        // the below and left would wrap around
        else if(r == grid.height()-1 && c == 0){
            B_left = G_left = R_left = grid.width()-1;
            R_below = G_below = B_below = 0;
            R_right = G_right = B_right = c+1;
            R_above = G_above = B_above = r-1;
        }
        // if the point is the very last in the last row
        // then the below and right would wrap around
        else if(r == grid.height()-1 && c == grid.width()-1){
            B_right = G_right = R_right = 0;
            B_below = G_below = R_below = 0;
            B_above = G_above = R_above = r-1;
            B_left = G_left = R_left = c-1;
        }
        // for elements along the top edge, then their aboves
        // would wrap around
        else if(r == 0){
            B_above = G_above = R_above = grid.height()-1;
            B_below = G_below = R_below = r+1;
            B_left = G_left = R_left = c-1;
            B_right = G_right = R_right = c+1;
        }
        // elements along the bottom edge would have wrapped belows
        else if(r == grid.height()-1){
            R_below = G_below = B_below = 0;
            R_above = G_above = B_above = r-1;
            R_left = G_left = B_left = c-1;
            R_right = G_right = B_right = c+1;
        }
        // elements along the left edge would have lefts wrapped around
        else if(c == 0){
            B_left = G_left = R_left = grid.width()-1;
            R_right = G_right = B_right = c+1;
            R_above = G_above = B_above = r-1;
            R_below = G_below = B_below =r+1;
        }
        // elements along the right edge have rights wrapped around
        else if(c == grid.width()-1){
            B_right = G_right = R_right = 0;
            R_above = G_above = B_above = r-1;
            R_below = G_below = B_below =r+1;
            R_left = G_left = B_left = c-1;

        }
        else{
            R_right = G_right = B_right = c+1;
            R_above = G_above = B_above = r-1;
            R_below = G_below = B_below =r+1;
            R_left = G_left = B_left = c-1;
        }
        RGB right = grid.get(r, R_right);
        R_right = right.r;
        B_right = right.b;
        G_right = right.g;
        RGB left = grid.get(r, R_left);
        R_left = left.r;
        B_left = left.b;
        G_left = left.g;
        RGB above = grid.get(R_above,  c);
        R_above = above.r;
        G_above = above.g;
        B_above = above.b;
        RGB below = grid.get(R_below, c);
        R_below = below.r;
        G_below = below.g;
        B_below = below.b;
        h_energy = (R_left-R_right)*(R_left-R_right);
        h_energy += (G_left-G_right)*(G_left-G_right);
        h_energy += (B_left-B_right)*(B_left-B_right);
        v_energy = (R_above-R_below)*(R_above-R_below);
        v_energy += (G_above-G_below)*(G_above-G_below);
        v_energy += (B_above-B_below)*(B_above-B_below);
        energy = h_energy+v_energy; 
               
      return energy;
    }
    // returns a grid of energies calling the energyAt() method
    public static Grid<Integer> energy(Grid<RGB> grid){
        int rows = grid.height();
        int cols = grid.width();
        // creates a grid of Integers to return the energies
        Grid<Integer> newGrid = new Grid<Integer>(rows, cols);
        // iterates through the passed in grid and gets the
        // energy at each point to return
        for(int i=0; i < rows; i++){            
            for(int j =0; j<cols; j++){
                int energy = P1.energyAt(grid, i, j);
                newGrid.set(i, j, energy);    
            }
        }
        return newGrid;
    }

    public static List<Point> findVerticalPath(Grid<RGB> grid){
        Grid <Node> nodeGrid = new Grid<Node>(grid.height(), grid.width());
        // initializes a node grid and sets the cost to their respective energies
        for(int i= 0; i < nodeGrid.height(); i++){
            for(int j=0; j <nodeGrid.width(); j++){
                int energy =P1.energyAt(grid, i, j);
                nodeGrid.set(i, j, new Node(i, j, null, energy));
               
            }
        }
        // starts at the bottom row and sets the best hop of each node to
        // the lowest cost node above and left/right diagonal
        // before setting the best hop the node checks to see if there is
        // a lower cost path already pointing to that node. if there is
        // then the node does not point to it. the bestHop then points 'below' to
        // the current node
        // this is a greedy algorithm
        for(int i=(nodeGrid.height()-1); i > 0; i--){ 
            for(int j=0; j<nodeGrid.width(); j++){
                Node n = nodeGrid.get(i,j);
                Node bestHop = null;
                Node trash = null;
                if((i == nodeGrid.height()-1) || (i != nodeGrid.height()-1 && n.getBelow() !=null)){
                    if(j==0){
                        Node above = nodeGrid.get(i-1, j);
                        Node right = nodeGrid.get(i-1, j+1);
                        bestHop = above.getCost() <= right.getCost() ? above : right;
                    }else if(j == (nodeGrid.width()-1)){
                        Node above = nodeGrid.get(i-1, j);
                        Node left = nodeGrid.get(i-1, j-1);
                        bestHop = above.getCost() < left.getCost() ? above : left;
                    }else{
                        Node above = nodeGrid.get(i-1, j);
                        Node right = nodeGrid.get(i-1, j+1);
                        bestHop = nodeGrid.get(i-1, j-1);
                        if(P1.energyAt(grid, i-1, j) <= P1.energyAt(grid, i-1, j+1)){
                            if(P1.energyAt(grid, i-1, j) < P1.energyAt(grid, i-1, j-1)){
                                bestHop = above;
                            }
                        }
                        else if(P1.energyAt(grid,i-1,j+1) < P1.energyAt(grid, i-1, j-1)){
                            bestHop = right;
                        }
                    }
                    // if the best hop is currently not
                    // part of a path then make it a part
                    // of this path
                    int total = n.getCost() + P1.energyAt(grid, bestHop.p.x, bestHop.p.y);
                    if(bestHop.getBelow() == null){
                        bestHop.setCost(total);
                        n.setBestHop(bestHop);
                        bestHop.setBelow(n);
                    }
                    // else if the bestHop is part of a path then
                    // make sure its current path is not cheaper
                    // than this one
                    else if(bestHop.getBelow().getCost() > n.getCost()){
                        bestHop.setCost(total);
                        n.setBestHop(bestHop);
                        bestHop.setBelow(n);
                    }
                }
            }
        }

        int bestCost = 9999999;
        Node bestPath = null;
        // iterates thru the top row to get the bestPath node
        for(int i=0; i < nodeGrid.width(); i++){
            if(nodeGrid.get(0,i).getBelow() != null){
                if(bestPath == null){
                    bestPath = nodeGrid.get(0,i);
                    bestCost = bestPath.getCost();
                }
                else if(bestCost >= nodeGrid.get(0,i).getCost()){
                    bestPath = nodeGrid.get(0, i);
                    bestCost = bestPath.getCost();
                }
            }  

        }
        // goes down the path and adds each node to the list
        Node next = bestPath;
        ArrayList <Point> path = new ArrayList<Point>();
        while(next != null){
            path.add(next.getPoint());
            next = next.getBelow();
        }
        return path;

    }

    public static List<Point> findHorizontalPath(Grid<RGB> grid1){
        Grid<RGB>grid = grid1.transpose();
        ArrayList <Point> path = (ArrayList <Point>) P1.findVerticalPath(grid);
        ArrayList <Point> newPath = new ArrayList<Point>();
        for(Point p: path){
            newPath.add(p.invert());
        }
       
        return newPath;
    }    
    
    public static Grid<RGB> removeVerticalPath(Grid<RGB> grid, List<Point> path){
         for(Point p: path){
            grid.remove(p.x, p.y);
         }
         return grid;   
        

    }


    public static Grid<RGB> removeHorizontalPath(Grid<RGB> grid, List<Point> path){
         Grid<RGB> grid2 = grid.transpose();
         for(Point p: path){
            grid2.remove(p.y, p.x);
         }
         grid = grid2.transpose();
         return grid;   
        

    }

    
    public static void grid2ppm(Grid <RGB> grid, String filename){
        
        
    }
    public static Grid<RGB> ppm2grid(String filename){
        //Open the indicated file, which is assumed to be in the PPM P3 format.
        //Read its contents to create and return the grid.
        Scanner s = null;
        int cols;
        int rows;
        try{
            s = new Scanner(new File (filename));
        }
        catch(FileNotFoundException e){
            System.out.println("File DNE");
        }
        s.next();
        cols = s.nextInt();
        rows = s.nextInt();
        s.next();
        Grid<RGB> g=new Grid<>(rows,cols);
        for(int i=0;i<rows;i++){
            for(int j=0;j<cols;j++){
                 RGB tmp = new RGB(0,0,0);
                 tmp.r=s.nextInt();
                 tmp.g=s.nextInt();
                 tmp.b=s.nextInt();
                 g.set(i,j,tmp);
            } 
        }
       return g;
    }
}
