/**
 *
 * @author Zanyar Sherwani
 *
 */
public class Point{
    
    public int x,y;
    public Point(int xx, int yy){
        this.x = xx;
        this.y = yy;

    }

    public Point invert(){
        Point newPoint = new Point(this.y, this.x);
        return newPoint;
    }

    public String toString(){

        return String.format("(%d,%d)", this.x, this.y);

    }
    public boolean equals(Object other){
        if(other instanceof Point){
            Point tmp = (Point) other;
            if(this.x == tmp.x && this.y == tmp.y){
                return true;
            }else{ return false; }
       }else { return false; }

   }
}
