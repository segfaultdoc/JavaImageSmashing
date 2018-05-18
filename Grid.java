import java.util.ArrayList;
import java.util.*;
/**
 *
 * @author Zanyar Sherwani
 *
 */
public class Grid <T>{

    public static void main(String [] args){
    Grid<Integer>g = new Grid<Integer>(new Integer [] []{{new Integer(1), new Integer(2), new Integer(3)}, {new Integer(4), new Integer(5), new Integer(6)}});
    System.out.println(g.toString());
    System.out.println(g.transpose());
    System.out.println(g.transpose().transpose());

                                             
                                                                      
    Grid <RGB> one =  new Grid<RGB>(new RGB[][]{
    {new RGB(  0, 100, 200), new RGB(  0,  80, 200), new RGB(  0, 100, 200)},
    {new RGB(100,  25, 200), new RGB(100,  15, 200), new RGB(100,  25, 200)},
    {new RGB(200,  95, 255), new RGB(200, 110, 255), new RGB(200, 100, 255)},
    {new RGB(200, 100, 255), new RGB(200,  95, 255), new RGB(200, 100, 255)},
    {new RGB(255,  70, 200), new RGB(255, 100, 200), new RGB(255, 100, 200)}
    });
    List<Point> h =P1.findHorizontalPath(one);
    System.out.println(P1.removeHorizontalPath(one, h).toString());
    System.out.println(one);
    Grid <RGB> two =  new Grid<RGB>(new RGB[][]{
    {new RGB(  0, 100, 200), new RGB(  0,  80, 200), new RGB(  0, 100, 200)},
    {new RGB(100,  25, 200), new RGB(100,  15, 200), new RGB(100,  25, 200)},
    {new RGB(200,  95, 255), new RGB(200, 110, 255), new RGB(200, 100, 255)},
    {new RGB(200, 100, 255), new RGB(200,  95, 255), new RGB(200, 100, 255)},
    {new RGB(255,  70, 200), new RGB(255, 100, 200), new RGB(255, 100, 200)}
    });
    System.out.println(one.equals(two));
    System.out.println(one.toString().equals(two.toString()));
    }
    // underlying data structure to represent the grid
    public ArrayList <ArrayList<T>> values;
    
    // overloaded constructor that excepts 
    // row and columns which correlate to
    // the width and heights, respectively,
    // of the grid
    public Grid(int num_rows, int num_cols){
        // initialize the grid to the specified
        // size
        this.values = new ArrayList<ArrayList<T>> ();
        for(int i =0; i<num_rows; i++){
            this.values.add(i, new ArrayList <T> ());
            ArrayList <T> row = this.values.get(i);
            for(int j =0; j < num_cols; j++){
                row.add(j, null);
            }

        }

    }
    
    // overloaded constructor that accepts
    // a 2d array with values and constructs
    // a 2d ArrayList<T> with these values
    public Grid(T[][] inGrid){
        this.values = new ArrayList<ArrayList<T>> ();        
        for(int i =0; i<inGrid.length; i++){
            this.values.add(new ArrayList <T> ());
            ArrayList <T> row = this.values.get(i);
            for(int j =0; j < inGrid[i].length; j++){
                row.add(j, inGrid[i][j]);
            }
        }
    }
    
    // returns the number of rows
    public int height(){
        return this.values.size();
    }
    // returns the number of columns
    public int width(){
        return this.values.get(0).size();
    }
    // returns the element at row i and column j
    public T get(int i, int j){
        ArrayList <T> row = this.values.get(i); 
        if(row != null){
            return row.get(j);
        }
        else { return null; }
    }
    // sets the position at row i and column j 
    // to the specified value
    public void set(int i, int j, T value){
        ArrayList <T> row = this.values.get(i);
        row.set(j, value);
    }
    // remove and returns the element at row i and
    // column j. If the row is empty after the remove
    // the row is removed
    public T remove(int i, int j){
        ArrayList <T> row = this.values.get(i);
        T val = row.remove(j);
        // remove the row from the grid if empty
        if(row.size() == 0){
            this.values.remove(i);
        }
        return val;

    }

    // transposes the Grid, effectively changing
    // an element at position (i, j) to (j, i)
    // returns the transposed grid
    public Grid<T> transpose(){
        // initializes the transposed grid to have the same
        // number of rows as 'this' has columns, and the same
        // number of columns as 'this' has rows
        Grid <T> newGrid = new Grid <T>(this.width(), this.height());
        for(int i=0; i<this.height(); i++){
            for(int j=0; j<this.width(); j++){
                // gets the value at this.(i, j)
                // and sets it to that.(j,i)
                T val = this.get(i,j);
                newGrid.set(j, i, val);            
            }                         
        }
        return newGrid;
    }
    // lazy tostring()
    public String toString(){
      return this.values.toString();
    }
    // @override
    public boolean equals(Object other){
        boolean bool = true;
        // if other is an instance of Grid, then move on
        // else return false
        if(other instanceof Grid){
            // casts other (type object) to type Grid<T>
            Grid <T> newOther = (Grid <T>) other;
            // if the heights and widths are equal, then proceed
            // else return false
            if(newOther.height() == this.height() && newOther.width() == this.width()){
                for(int i =0; i<this.height(); i++){
                    for(int j=0; j<this.width(); j++){
                        T valThis = this.get(i, j);
                        T valOther = newOther.get(i,j);
                        // returns false if any values are not equal
                        if(valThis != null && !(valThis.equals(valOther))){
                            bool = false;
                            return bool;
                        }
                    }
                }
            }else{
                bool = false;
                return bool;
            }
            
        }else{
            bool = false;
            return bool;
        }
    // bool is true at this point and the two are equal!
    return bool;
    }

}
