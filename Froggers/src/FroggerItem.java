import javafx.scene.shape.Rectangle;

public class FroggerItem
{
    public static final int LEFT = 2;
    public static final int RIGHT = 3;

    private int direction, type, m, t;
    private double speed;
    Rectangle rect;

   public FroggerItem(double x, double y, double width, double height, int direction,double speed, int type){
       rect = new Rectangle();
       rect.setX(x);
       rect.setY(y);
       rect.setWidth(width);
       rect.setHeight(height);
       this.direction = direction;
       this.speed = speed;
       this.type = type;
   }

   public int getType(){
       return type;
   }

   public int getDirection(){
        return direction;
   }

   public Rectangle getRect(){
        return rect;
   }

   public void setDirection(int direction){
       this.direction = direction;
   }

   public void update(){
        if(getDirection() == LEFT){
            getRect().setX(getRect().getX() - speed);
        }
        else if(getDirection() == RIGHT){
            getRect().setX(getRect().getX() + speed);
        }
       if(m != 4){
           if(time() == 0) {
               if (m == 0) {
                   time(1);
                   m = 3;
               } else if (m == 3) {
                   time(1);
                   m = 2;
               } else if (m == 2) {
                   time(1);
                   m = 1;
               } else if (m == 1) {
                   time(1);
                   m = 0;
               }
           }
       }

   }

   public void mode(int mm){
       m = mm;
   }

   public int mode(){
       return m;
   }

   public void time(int tt){
       t = tt;
   }

   public int time(){
       return t;
   }


}
