public class Car extends FroggerItem {

    public static final int SEMI = 3;
    public static final int LIMO = 2;
    public static final int CAR_1 = 1;
    public static final int CAR_2 = 0;

    public Car(double x, double y, int width, int direction, double speed, int type)
    {
        super(x,y,width,40,direction,speed,type);
    }

}
