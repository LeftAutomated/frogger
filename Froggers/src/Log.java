public class Log extends FroggerItem {

    public static final int SHORT = 0;
    public static final int MEDIUM = 1;
    public static final int LONG = 2;

    public Log(double x, double y, int direction, double speed, int type){

        super(x,y,type*40+80,40,direction,speed,type);
    }
}
