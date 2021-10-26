import javafx.scene.shape.Rectangle;

public class FroggerGame
{
    public static final int PLAYING = 0;
    public static final int DEAD = 1;
    public static final int PLAYER_WINS = 2;

    private static final int MAX_LIFE_TIME = 80;
    int status, lives;
    double startLifeTime;
    private boolean reachedMiddle;
    Frog player;
    LogLane[] logLanes;
    TurtleLane[] turtleLanes;
    CarLane[] carLanes;
    LilyPad[] lilyPads;

    public FroggerGame(){
        status = PLAYING;
        startLifeTime = 0;
        player = new Frog(380,520);
        reachedMiddle = false;
        lives = 3;

        logLanes = new LogLane[3];
        logLanes[0] = new LogLane(80,3,.5);
        logLanes[1] = new LogLane(160,3,.5);
        logLanes[2] = new LogLane(200,3,.5);

        for(LogLane x: logLanes){
            double ran1 = Math.random() * 600;
            int ranType = (int)(Math.random() * 3);
            x.setItems(new Log(ran1,x.getY(),x.getDirection(),x.getSpeed(),ranType));
            ranType = (int)(Math.random() * 3);
            x.setItems(new Log(ran1+320,x.getY(),x.getDirection(),x.getSpeed(),ranType));
            ranType = (int)(Math.random() * 3);
            x.setItems(new Log(ran1-320,x.getY(),x.getDirection(),x.getSpeed(),ranType));
        }


        turtleLanes = new TurtleLane[2];
        turtleLanes[0] = new TurtleLane(120,2,.4);
        turtleLanes[1] = new TurtleLane(240,2,.4);
        for(TurtleLane x: turtleLanes){
            double ran1 = Math.random() * 600;
            int ranType = (int)(Math.random() * 3);
            x.setItems(new Turtle(ran1,x.getY(),x.getDirection(),x.getSpeed(),ranType));
            ranType = (int)(Math.random() * 3);
            x.setItems(new Turtle(ran1+320,x.getY(),x.getDirection(),x.getSpeed(),ranType));
            ranType = (int)(Math.random() * 3);
            x.setItems(new Turtle(ran1-320,x.getY(),x.getDirection(),x.getSpeed(),ranType));
        }


        carLanes = new CarLane[5];
        for(int i = 0; i < carLanes.length; i++){
            double ranSpeed = Math.random()+.2;
            if(i % 2 == 0)
                carLanes[i] = new CarLane(i*40+320, 2,ranSpeed);
            else
                carLanes[i] = new CarLane(i*40+320, 3,ranSpeed);}
        for(CarLane x: carLanes){
            double ran1 = Math.random() * 600;
            int ranType = (int)(Math.random() * 4);
            int w = 0;
            if(ranType == 0 || ranType == 1)
                w = 40;
            else if(ranType == 2)
                w = 80;
            else
                w = 120;
            x.setItems(new Car(ran1,x.getY(),w,x.getDirection(),x.getSpeed(),ranType));
            ranType = (int)(Math.random() * 3);
            x.setItems(new Car(ran1+160,x.getY(),w,x.getDirection(),x.getSpeed(),ranType));
            ranType = (int)(Math.random() * 3);
            x.setItems(new Car(ran1-160,x.getY(),w,x.getDirection(),x.getSpeed(),ranType));
        }


        lilyPads = new LilyPad[4];
        for(int i = 0; i < lilyPads.length; i++)
            lilyPads[i] = new LilyPad(i*172+122,40);

    }

    public void update(){
        for(CarLane x : carLanes)
            x.update();
        for(LogLane x : logLanes)
            x.update();
        for(TurtleLane x : turtleLanes)
            x.update();
        updateChecks();
        if(player.getRect().getY() == 280){
            reachedMiddle = true;
        }
        for(int i = 0; i < logLanes.length; i++){
            int ranType = (int)(Math.random() * 3);
            if(logLanes[i].getItems().get(logLanes[i].getItems().size()-1).getRect().getX() > 40){
                logLanes[i].setItems(new Log(-240,logLanes[i].getY(),logLanes[i].getDirection(),logLanes[i].getSpeed(),ranType));
            }
        }

        for(int i = 0; i < turtleLanes.length; i++){
            int ranType = (int)(Math.random() * 5);
            if(turtleLanes[i].getItems().get(turtleLanes[i].getItems().size()-1).getRect().getX() < 480){
                turtleLanes[i].setItems(new Turtle(800,turtleLanes[i].getY(),turtleLanes[i].getDirection(),turtleLanes[i].getSpeed(),ranType));
            }
        }

        for(int i = 0; i < carLanes.length; i++){
            int ranType = (int) (Math.random() * 4);
            int w = 0;
            if (ranType == 0 || ranType == 1)
                w = 40;
            else if (ranType == 2)
                w = 80;
            else
                w = 120;
            if (carLanes[i].getDirection() == 2 && carLanes[i].getItems().get(carLanes[i].getItems().size() - 1).getRect().getX() < 640)
                carLanes[i].setItems(new Car(800, carLanes[i].getY(), w, carLanes[i].getDirection(), carLanes[i].getSpeed(), ranType));
            else if (carLanes[i].getDirection() == 3 && carLanes[i].getItems().get(carLanes[i].getItems().size() - 1).getRect().getX() > 40)
                carLanes[i].setItems(new Car(-120, carLanes[i].getY(), w, carLanes[i].getDirection(), carLanes[i].getSpeed(), ranType));
        }
    }

    public int status(){
        return status;
    }

    public void setStartLifeTime(double i){startLifeTime = i;}

    public double getStartLifeTime(){return startLifeTime;}

    public Frog getPlayer(){
        return player;
    }

    public LogLane[] getLogLanes(){
        return logLanes;
    }

    public TurtleLane[] getTurtleLanes() {
        return turtleLanes;
    }

    public CarLane[] getCarLanes() {
        return carLanes;
    }

    public LilyPad[] getLilyPads() {
        return lilyPads;
    }

    public int getLives() {
        return lives;
    }

    public void playerDeath(){
        if(lives == 0)
            status = DEAD;
        else{
            status = DEAD;
            lives--;
            if(reachedMiddle)
                player = new Frog(380,280);
            else
                player = new Frog(380,520);
            startLifeTime = 0;
        }
    }

    public double getTimeLeft(){
        return MAX_LIFE_TIME - startLifeTime;
    }

    public void carCheck(){
        boolean d = false;
        for(int i = 0; i < carLanes.length; i++) {
            for (int j = 0; j < carLanes[i].getItems().size(); j++) {
                Rectangle r = carLanes[i].getItems().get(j).getRect();
                if (player.getRect().intersects(r.getX(), r.getY() + 5, r.getWidth()-5, 30))
                    d = true;

            }

        }
        if(d)
            playerDeath();
    }

    public void logCheck(){
        boolean d = true;
        for(int i = 0; i < logLanes.length; i++){
            for(int j = 0; j < logLanes[i].getItems().size(); j++){
                Rectangle r = logLanes[i].getItems().get(j).getRect();
                if(player.getRect().intersects(r.getX(),r.getY()+5,r.getWidth(),30)){
                 d = false;
                    player.getRect().setX(player.getRect().getX() + logLanes[i].getSpeed());
                }

            }
        }
        if(d)
            playerDeath();

    }

    public void turtleCheck(){
        boolean d = true;
        for(int i = 0; i < turtleLanes.length; i++){
            for(int j = 0; j < turtleLanes[i].getItems().size(); j++){
                Rectangle r = turtleLanes[i].getItems().get(j).getRect();
                if(player.getRect().intersects(r.getX(),r.getY()+5,r.getWidth(),30) && turtleLanes[i].getItems().get(j).mode() != 2){
                    d = false;
                    player.getRect().setX(player.getRect().getX() - turtleLanes[i].getSpeed());
                }
            }

        }
        if(d)
            playerDeath();
    }

    public void lilyCheck(){
        int num = 5;

        Rectangle p = player.getRect();

        for(int i = 0; i < lilyPads.length; i++)
            if(lilyPads[i].getRect().intersects(p.getX(),p.getY(),p.getWidth(),p.getHeight()))
                num = i;

        if(num == 5)
            playerDeath();
        else if(lilyPads[num].getFrog())
            player.getRect().setY(p.getY()+40);
        else{
            lilyPads[num].setFrog(true);
            player = new Frog(380,520);
            reachedMiddle = false;
        }

        boolean win = true;
        for(int i = 0; i < lilyPads.length; i++)
            if(!lilyPads[i].getFrog())
                win = false;

        if(win)
            status = PLAYER_WINS;
    }

    public void updateChecks(){
        double y = player.getRect().getY();
        for(int i = 0; i < logLanes.length; i++)
            if(y == logLanes[i].getY())
                logCheck();

        for(int i = 0; i < carLanes.length; i++)
            if(y == carLanes[i].getY() )
                carCheck();

        for(int i = 0; i < turtleLanes.length; i++)
            if(y == turtleLanes[i].getY())
                turtleCheck();

        if(y == 40)
            lilyCheck();
    }

}
