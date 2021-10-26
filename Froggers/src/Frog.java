public class Frog extends FroggerItem {

    public static final int UP = 0;
    public static final int DOWN = 1;

    public Frog(double x, double y){
        super(x,y,40,40,UP,40,0);
    }

    public int getWidth(){
        return 40;
    }
}
