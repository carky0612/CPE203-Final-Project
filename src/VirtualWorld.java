import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import processing.core.*;

import javax.swing.*;

public final class VirtualWorld extends PApplet
{   public static ArrayList<Point> treeList =new ArrayList<>(
        Arrays.asList(new Point(0,2),new Point(5,0),
        new Point(1,15),new Point(11,13),new Point(7,5),
        new Point(18,4),new Point(1,1),new Point(16,4),
        new Point(7,3),new Point(7,0),new Point(3,0),
        new Point(1,3),new Point(10,13),new Point(2,4),
        new Point(1,2),new Point(6,3),new Point(13,15),
        new Point(6,2),new Point(18,0),new Point(19,1),
        new Point(8,1),new Point(1,14),new Point(8,3),
        new Point(17,3),new Point(15,5),new Point(10,8),
        new Point(8,11),new Point(17,4),new Point(11,14),
        new Point(5,5)));


    public static Point pressed;

    public static int beeX;
    public static int beeY;
    public static int point =0;
    public static Guy guy;

    public static final int TIMER_ACTION_PERIOD = 100;

    private static final int VIEW_WIDTH = 640;
    private static final int VIEW_HEIGHT = 480;
    private static final int TILE_WIDTH = 32;
    private static final int TILE_HEIGHT = 32;
    private static final double WORLD_WIDTH_SCALE = .5;
    private static final double WORLD_HEIGHT_SCALE = .5;

    private static final int VIEW_COLS = (VIEW_WIDTH / TILE_WIDTH)*2;
    private static final int VIEW_ROWS = (VIEW_HEIGHT / TILE_HEIGHT)*2;
    private static final int WORLD_COLS = (int)(VIEW_COLS * WORLD_WIDTH_SCALE);
    private static final int WORLD_ROWS = (int)(VIEW_ROWS * WORLD_HEIGHT_SCALE);

    private static final String IMAGE_LIST_FILE_NAME = "imagelist";
    private static final String DEFAULT_IMAGE_NAME = "background_default";
    private static final int DEFAULT_IMAGE_COLOR = 0x808080;

    private static String LOAD_FILE_NAME = "world.sav";

    private static final String FAST_FLAG = "-fast";
    private static final String FASTER_FLAG = "-faster";
    private static final String FASTEST_FLAG = "-fastest";
    private static final double FAST_SCALE = 0.5;
    private static final double FASTER_SCALE = 0.25;
    private static final double FASTEST_SCALE = 0.10;

    private static double timeScale = 1.0;

    private ImageStore imageStore;
    private WorldModel world;
    private WorldView view;
    private EventScheduler scheduler;

    private long nextTime;

    public void settings() {
        size(VIEW_WIDTH, VIEW_HEIGHT);
    }

    /*
       Processing entry point for "sketch" setup.
    */
    public void setup() {
        this.imageStore = new ImageStore(
                createImageColored(TILE_WIDTH, TILE_HEIGHT,
                                   DEFAULT_IMAGE_COLOR));
        this.world = new WorldModel(WORLD_ROWS, WORLD_COLS,
                                    createDefaultBackground(imageStore));
        this.view = new WorldView(VIEW_ROWS, VIEW_COLS, this, world, TILE_WIDTH,
                                  TILE_HEIGHT);
        this.scheduler = new EventScheduler(timeScale);

        loadImages(IMAGE_LIST_FILE_NAME, imageStore, this);
        loadWorld(world, LOAD_FILE_NAME, imageStore);

        scheduleActions(world, scheduler, imageStore);

        nextTime = System.currentTimeMillis() + TIMER_ACTION_PERIOD;
    }

    public void draw() {
        //System.out.println("start draw");
        long time = System.currentTimeMillis();
        if (time >= nextTime) {
            this.scheduler.updateOnTime( time);
            nextTime = time + TIMER_ACTION_PERIOD;
        }
        Point beePos = new Point(beeX,beeY);
        if (beeX >= 616)
            beeX -= 8;
        if(beeX <= 8)
            beeX +=8;
        if(beeY <= 8)
            beeY +=8;
        if(beeY >= 464)
            beeY -= 8;

        view.drawViewport();

        if(pressed!=null) {
            view.screen.image(imageStore.getImageList("picnic").get(0),
                    pressed.x,
                    pressed.y);
            view.screen.image(imageStore.getImageList("picnic").get(0),
                    pressed.x+32,
                    pressed.y);
            view.screen.image(imageStore.getImageList("picnic").get(0),
                    pressed.x+32,
                    pressed.y+32);
            view.screen.image(imageStore.getImageList("picnic").get(0),
                    pressed.x,
                    pressed.y+32);
            view.screen.image(imageStore.getImageList("bee").get(0),
                    beeX,
                    beeY);
//            ArrayList<Point> newTree =new ArrayList<>(
//                    Arrays.asList(
//                            new Point(pressed.x,pressed.y-32),
//                            new Point(pressed.x+32,pressed.y-32),
//                            new Point(pressed.x,pressed.y+64),
//                            new Point(pressed.x+64,pressed.y+64)));
//            for(Point spot:newTree)
//            {


            //}

        }
        //System.out.println("point");
        //Point treePos = new Point(2,4);

        for (Point treePos : treeList) {
            Optional<Entity> obj = world.getOccupant(treePos);
//            if(pressed!=null && (world.getOccupant(new Point(pressed.x, pressed.y+32)).equals(obj)
//                    ||world.getOccupant(new Point(pressed.x, pressed.y-32)).equals(obj)
//                    ||world.getOccupant(new Point(pressed.x+32, pressed.y-32)).equals(obj)
//                    ||world.getOccupant(new Point(pressed.x-32, pressed.y+32)).equals(obj)
//                    ||world.getOccupant(new Point(pressed.x-32, pressed.y+64)).equals(obj)))
//            {
//                treeList.remove(treePos);
//            }
            if ((world.getOccupancyCell(new Point(beeX / 32, beeY / 32)) != null) && (world.getOccupant(new Point(beeX / 32, beeY / 32)).equals(obj))) {
                point += 1;
                System.out.println(point);
                if (point == 20) {
                    JOptionPane.showMessageDialog(frame, "YOU WIN!");
                }
                beeX += 8;
                beeY += 8;
            }
        }

    }

    // Just for debugging and for P5
    // Be sure to refactor this method as appropriate
    public void mousePressed() {
        pressed = mouseToPoint(mouseX, mouseY);
        beeX=pressed.x+16;
        beeY=pressed.y+16;
//        for (Point treePos : treeList) {
//            Optional<Entity> obj = world.getOccupant(treePos);
//            if(world.getOccupant(new Point(pressed.x, pressed.y+32)).equals(obj)
//                    ||world.getOccupant(new Point(pressed.x, pressed.y-32)).equals(obj)
//                    ||world.getOccupant(new Point(pressed.x+32, pressed.y-32)).equals(obj)
//                    ||world.getOccupant(new Point(pressed.x-32, pressed.y+32)).equals(obj)
//                    ||world.getOccupant(new Point(pressed.x-32, pressed.y+64)).equals(obj))
//            {
//                treeList.remove(treePos);
//            }
        }


//        Guy guy = new Guy("guy", new Point(pressed.x, pressed.y),imageStore.getImageList("guy"),0, 0,WorldModel.GUY_ACTION_PERIOD, WorldModel.GUY_ANIMATION_PERIOD, 0,0);
//        world.addEntity(guy);
//        guy.scheduleActions(scheduler, world,imageStore);




    private Point mouseToPoint(int x, int y)
    {
        return view.viewport.viewportToWorld(x, y);
    }
    public void keyPressed() {
        if (key == CODED) {
            int dx = 0;
            int dy = 0;

            switch (keyCode) {
                case UP:
                    dy = -6;
                    break;
                case DOWN:
                    dy = 6;
                    break;
                case LEFT:
                    dx = -6;
                    break;
                case RIGHT:
                    dx = 6;
                    break;
            }
            beeX+=dx;
            beeY+=dy;
//            Bee.pos = new Point(Bee.startPosition.x+dx, Bee.startPosition.y+dy);
//            Point viewPoint = view.viewport.worldToViewport(Bee.pos.x, Bee.pos.y);

            //view.shiftView(dx,dy);
        }



    }

    public static Background createDefaultBackground(ImageStore imageStore) {
        return new Background(DEFAULT_IMAGE_NAME,
                              imageStore.getImageList(
                                                     DEFAULT_IMAGE_NAME));
    }

    public static PImage createImageColored(int width, int height, int color) {
        PImage img = new PImage(width, height, RGB);
        img.loadPixels();
        for (int i = 0; i < img.pixels.length; i++) {
            img.pixels[i] = color;
        }
        img.updatePixels();
        return img;
    }

    static void loadImages(
            String filename, ImageStore imageStore, PApplet screen)
    {
        try {
            Scanner in = new Scanner(new File(filename));
            ImageStore.loadImages(in, imageStore, screen);
        }
        catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void loadWorld(
            WorldModel world, String filename, ImageStore imageStore)
    {
        try {
            Scanner in = new Scanner(new File(filename));
            world.load(in, imageStore);
        }
        catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void scheduleActions(
            WorldModel world, EventScheduler scheduler, ImageStore imageStore)
    {
        //System.out.println("start schedActions");
        for (Entity entity : world.entities) {

            if(entity instanceof EntityActions){
                ((EntityActions)entity).scheduleActions(scheduler,world, imageStore);}

            else if(entity.getClass() == Obstacle.class){
                ((Obstacle)entity).scheduleActions(scheduler,world, imageStore);}

            //System.out.println("loop");

        }
       // System.out.println("end schedActions");
    }

    public static void parseCommandLine(String[] args) {
        if (args.length > 1)
        {
            if (args[0].equals("file"))
            {

            }
        }
        for (String arg : args) {
            switch (arg) {
                case FAST_FLAG:
                    timeScale = Math.min(FAST_SCALE, timeScale);
                    break;
                case FASTER_FLAG:
                    timeScale = Math.min(FASTER_SCALE, timeScale);
                    break;
                case FASTEST_FLAG:
                    timeScale = Math.min(FASTEST_SCALE, timeScale);
                    break;
            }
        }
    }

    public static void main(String[] args) {
        parseCommandLine(args);
        PApplet.main(VirtualWorld.class);
    }
}
