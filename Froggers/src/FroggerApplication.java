import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class FroggerApplication extends Application implements Runnable
{
    private FroggerGame game;
    int updatesPerSecond = 1;
    int updateCount = 0;
    long startTime, startTime2;
    double time = 0;
    int waitToUpdate = 1000/updatesPerSecond;
    Image car1_Left, car1_Right, car2_Left, car2_Right, limo_Left, limo_Right, semi_Left, semi_Right,
    frogUp, frogDown, frogLeft, frogRight, hsTurtle, hmTurtle, hlTurtle, sTurtle, mTurtle, lTurtle,
    sLog, mLog, lLog, lilyPad, frogLife;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage){
        car1_Left = new Image("Car 1 - Left.png");
        car1_Right = new Image("Car 1 - Right.png");
        car2_Left = new Image("Car 2 - Left.png");
        car2_Right = new Image("Car 2 - Right.png");
        limo_Left = new Image("Limo - Left.png");
        limo_Right = new Image("Limo - Right.png");
        semi_Left = new Image("Semi - Left.png");
        semi_Right = new Image("Semi - Right.png");
        frogUp = new Image("Frog Up.png");
        frogDown = new Image("Frog Down.png");
        frogLeft = new Image("Frog Left.png");
        frogRight = new Image("Frog Right.png");
        hsTurtle = new Image("HS-Turtle.png");
        hmTurtle = new Image("HM-Turtle.png");
        hlTurtle = new Image("HL-Turtle.png");
        sTurtle = new Image("S-Turtle.png");
        mTurtle = new Image("M-Turtle.png");
        lTurtle = new Image("L-Turtle.png");
        sLog = new Image("S-Log.png");
        mLog = new Image("M-Log.png");
        lLog = new Image("L-Log.png");
        lilyPad = new Image("lilyPad.png");
        frogLife = new Image("Frog Life.png");

        primaryStage.setTitle("Frogger");
        Group root = new Group();
        Canvas canvas = new Canvas(800, 600);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        reset();

        canvas.setOnKeyTyped(new EventHandler<KeyEvent>()
        {
            @Override
            public void handle(KeyEvent event){
                String s = event.getCharacter();
                double x = game.getPlayer().getRect().getX();
                double y = game.getPlayer().getRect().getY();
                if(game.status() == 0){
                    if(s.equals("w") && y > 40) {
                       game.getPlayer().getRect().setY(y-40);
                        game.getPlayer().setDirection(0);
                    }
                    if(s.equals("d") && x <= 700){
                        game.getPlayer().getRect().setX(x+40);
                        game.getPlayer().setDirection(3);
                    }
                    if(s.equals("a") && x >= 40){
                      game.getPlayer().getRect().setX(x-40);
                      game.getPlayer().setDirection(2);
                    }
                    if(s.equals("s") && y <= 480){
                        game.getPlayer().getRect().setY(y+40);
                        game.getPlayer().setDirection(1);
                    }
                }
                else if(s.equals("n")){
                    reset();
                }

                draw(gc);
                game.updateChecks();
            }
        });

        new AnimationTimer(){
            @Override
            public void handle(long now)
            {
                long currentTime = System.nanoTime();
                boolean t = false;
                double u = 0;

                double updatesNeeded = (((double)(currentTime-startTime) / 1000000))/ waitToUpdate;
                double updatesNeeded2 = (((double)(currentTime-startTime2) / 1000000))/ waitToUpdate;

                update();
                if(updatesNeeded2 >= 2){
                    for(int i = 0; i < game.getTurtleLanes().length; i++){
                        for(int j = 0; j < game.getTurtleLanes()[i].getItems().size(); j++)
                            game.getTurtleLanes()[i].getItems().get(j).time(0);
                    }
                    startTime2 = System.nanoTime();
                    System.out.println("Working");
                }

                    if (game.status() == 0)
                        if (updatesNeeded <= 80) {
                            game.setStartLifeTime(updatesNeeded);
                            time = 80 - updatesNeeded;
                        } else if (updatesNeeded > 80 && updatesNeeded < 81){
                            time = (updatesNeeded - 80) * 80;
                            game.setStartLifeTime((81 - updatesNeeded)*80);
                        }
                        else {
                            game.playerDeath();
                            startTime = System.nanoTime();
                        }
                    else if (game.status() == 1) {
                            u = updatesNeeded;
                        if (time < 80 && u < u + 1) {
                            time += ((u + 1) - u) * (80 / (80 - u));
                        } else {
                            t = true;
                        }
                    }

                    if (game.status() == 1 && t) {
                        if (game.getLives() > 0)
                            game.status = game.PLAYING;
                        startTime = System.nanoTime();
                        t = false;
                    }
                    draw(gc);

            }
        }.start();

        draw(gc);
        root.getChildren().add(canvas);
        primaryStage.setScene(new Scene(root));
        canvas.requestFocus();
        primaryStage.show();
    }

    public void reset(){
        game = new FroggerGame();
        startTime = System.nanoTime();
        startTime2 = System.nanoTime();
    }

    public void draw(GraphicsContext g){
        //Background
        g.setFill(Color.BLACK);                             //Status
        g.fillRect(0,560,800,40);
        g.setFill(Color.GREEN);                             //Grass Lanes
        g.fillRect(0,0,800,40);
        g.fillRect(0,520,800,40);
        g.fillRect(0,280,800,40);
        g.fillRect(0,40,800,40);
        g.setFill(Color.BLUE);                              //River
        g.fillRect(0,80,800,200);
        for(int i = 0; i < 4; i++)
            g.fillRect(172*i+112,40,60,40);
        g.setFill(Color.GREY);                              //Road
        g.fillRect(0,320,800,200);
        g.setStroke(Color.WHITE);                           //Road Bank
        g.strokeRect(-1,320,802,200);
        g.setFill(Color.YELLOW);                            //Lane boundaries
        for(int i = 0; i < 4; i++)
            for(int j = 0; j < 10; j++)
                g.fillRect(j*80+10,i*41+357,60,4);

        g.setFill(Color.rgb(255 - (int)(game.getTimeLeft()*3),255 - (int)(game.getStartLifeTime()*3),0));
        g.setStroke(Color.WHITE);
        g.fillRect(550,570,time*2,20);
        g.strokeRect(550,570,160,20);

        for(int i = 0; i < game.getLives(); i++)
            g.drawImage(frogLife,i*30+100,575);

        for(int i = 0; i < game.getLilyPads().length; i++){
            g.drawImage(lilyPad, game.getLilyPads()[i].getRect().getX(),game.getLilyPads()[i].getRect().getY());
            if(game.getLilyPads()[i].getFrog())
                g.drawImage(frogUp, game.getLilyPads()[i].getRect().getX(),game.getLilyPads()[i].getRect().getY());
        }

       for(int i = 0; i < game.getLogLanes().length; i++){
           LogLane l = game.getLogLanes()[i];
           for(int j = 0; j < l.getItems().size(); j++){
               FroggerItem a = l.getItems().get(j);
               if(a.getType() == 0) {
                    g.drawImage(sLog, a.getRect().getX(),a.getRect().getY());
               }
               else if(a.getType() == 1){
                    g.drawImage(mLog,a.getRect().getX(),a.getRect().getY());
               }
               else{
                    g.drawImage(lLog,a.getRect().getX(),a.getRect().getY());
               }
           }
       }

        for(int i = 0; i < game.getTurtleLanes().length; i++){
            TurtleLane l = game.getTurtleLanes()[i];
            for(int j = 0; j < l.getItems().size(); j++){
                FroggerItem a = l.getItems().get(j);
                if(a.getType() == 0) {
                    if(a.mode() == 0 || a.mode() == 4)
                        g.drawImage(sTurtle, a.getRect().getX(),a.getRect().getY());
                    else if(a.mode() == 1 || a.mode() == 3)
                        g.drawImage(hsTurtle, a.getRect().getX(),a.getRect().getY());
                }
                else if(a.getType() == 1){
                    if(a.mode() == 0 || a.mode() == 4)
                        g.drawImage(mTurtle, a.getRect().getX(),a.getRect().getY());
                    else if(a.mode() == 1 || a.mode() == 3)
                        g.drawImage(hmTurtle, a.getRect().getX(),a.getRect().getY());
                }
                else{
                    if(a.mode() == 0 || a.mode() == 4)
                        g.drawImage(lTurtle, a.getRect().getX(),a.getRect().getY());
                    else if(a.mode() == 1 || a.mode() == 3)
                        g.drawImage(hlTurtle, a.getRect().getX(),a.getRect().getY());
                }
            }
        }

        for(int i = 0; i < game.getCarLanes().length; i++){
            CarLane l = game.getCarLanes()[i];
            for(int j = 0; j < l.getItems().size(); j++){
                FroggerItem a = l.getItems().get(j);
                if(a.getDirection() == a.LEFT)
                {
                    if(a.getType() == 0 && a.getRect().getWidth() == 40){
                        g.drawImage(car2_Left,a.getRect().getX(),a.getRect().getY());
                    }
                    else if(a.getType() == 1 && a.getRect().getWidth() == 40){
                        g.drawImage(car1_Left,a.getRect().getX(),a.getRect().getY());
                    }
                    else if(a.getType() == 2 && a.getRect().getWidth() == 80){
                        g.drawImage(limo_Left,a.getRect().getX(),a.getRect().getY());
                    }
                    else{
                        g.drawImage(semi_Left,a.getRect().getX(),a.getRect().getY());
                    }
                }
                else
                {
                    if(a.getType() == 0 && a.getRect().getWidth() == 40){
                        g.drawImage(car2_Right,a.getRect().getX(),a.getRect().getY());
                    }
                    else if(a.getType() == 1 && a.getRect().getWidth() == 40){
                        g.drawImage(car1_Right,a.getRect().getX(),a.getRect().getY());
                    }
                    else if(a.getType() == 2 && a.getRect().getWidth() == 80){
                        g.drawImage(limo_Right,a.getRect().getX(),a.getRect().getY());
                    }
                    else{
                        g.drawImage(semi_Right,a.getRect().getX(),a.getRect().getY());
                    }
                }
            }
        }

        if(game.status() != 1){
            if(game.getPlayer().getDirection() == game.getPlayer().UP)
                g.drawImage(frogUp,game.getPlayer().getRect().getX(),game.getPlayer().getRect().getY());
            else if(game.getPlayer().getDirection() == game.getPlayer().DOWN)
                g.drawImage(frogDown,game.getPlayer().getRect().getX(),game.getPlayer().getRect().getY());
            else if(game.getPlayer().getDirection() == game.getPlayer().LEFT)
                g.drawImage(frogLeft,game.getPlayer().getRect().getX(),game.getPlayer().getRect().getY());
            else if(game.getPlayer().getDirection() == game.getPlayer().RIGHT)
                g.drawImage(frogRight,game.getPlayer().getRect().getX(),game.getPlayer().getRect().getY());
            if(game.status() == 2){
                g.setFill(Color.GREEN);
                g.fillText("YOU WIN", 380, 585);
            }
        }

       if(game.status() == 1 && game.getLives() == 0){
           g.setFill(Color.RED);
           g.fillText("GAME OVER", 380, 585);
       }

    }

    public void update() {
        game.update();
    }

    public void run(){
    }
}
