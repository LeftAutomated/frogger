public class Turtle extends FroggerItem
{

    public static final int ONE_TURTLE = 0;
    public static final int TWO_TURTLE = 1;
    public static final int THREE_TURTLE = 2;
    public static final int ALWAYS_UP = 4;
    public static final int UP = 0;
    public static final int HALF_UP = 1;
    public static final int DOWN = 2;
    public static final int HALF_DOWN = 3;

    int mode;
    private int timer;

    public Turtle(double x, double y, int direction,double speed, int type){
        super(x,y,type*40,40,direction,speed,type);
        super.mode((int)(Math.random()*5));
        timer = 1;
    }

    public void update(){
        super.update();
    }
}
