import java.util.ArrayList;

public class Lane
{

    private int direction;
    private double speed, y;
    private ArrayList<FroggerItem> items;
    private ArrayList<Integer> modes;

    public Lane(double y, int direction, double speed){
        items = new ArrayList<FroggerItem>();
        modes = new ArrayList<Integer>();
        this.direction = direction;
        this.speed = speed;
        this.y = y;
    }

    public double getY(){
        return y;
    }

    public double getSpeed(){
        return speed;
    }

    public int getDirection(){
        return direction;
    }

    public void update(){
        for(FroggerItem x: items){
            x.update();
        }

    }

    public void setItems(FroggerItem l){items.add(l);}

    public ArrayList<FroggerItem> getItems(){
        return items;
    }

}
