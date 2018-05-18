public class RGB{

    int r, g, b;

    public RGB(int r, int g, int b){
        this.r = r;
        this.g = g;
        this.b = b;    
    }

    public boolean equals(Object other){

                
        if(other instanceof RGB){
            RGB tmp = (RGB) other;
            if(this.r == tmp.r && this.g == tmp.g && this.b == tmp.b){
                return true;
            }else{ return false; }

        }else { return false; }
    }

    public String toString(){               
        return String.format("RGB(%d,%d,%d)", this.r, this.g, this.b);
     }

}
