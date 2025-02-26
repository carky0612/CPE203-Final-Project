import processing.core.PImage;

import java.util.List;
import java.util.Random;

public class GrowFlower extends TranPlant{
    public static final int TREE_ANIMATION_MAX = 600;
    public static final int TREE_ANIMATION_MIN = 50;
    public static final int TREE_ACTION_MAX = 1400;
    public static final int TREE_ACTION_MIN = 1000;
    public static final int TREE_HEALTH_MAX = 3;
    public static final int TREE_HEALTH_MIN = 1;
    public static final String STUMP_KEY = "stump";
    public static final String TREE_KEY = "tree";

    public GrowFlower(String id, Point position, List<PImage> images,
                      int resourceLimit, int resourceCount, int actionPeriod, int animationPeriod,
                      int health, int healthLimit)
    {
        super(id, position, images, resourceLimit, resourceCount, actionPeriod, animationPeriod, health, healthLimit);
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        this.health++;
        if (!this.transform(world, scheduler, imageStore))
        {
            scheduler.scheduleEvent(this,
                    this.createActivityAction(world, imageStore),
                    this.actionPeriod);
        }
    }
public int getNumFromRange(int max, int min)
{
    Random rand = new Random();
    return min + rand.nextInt(
            max
                    - min);
}
    public boolean transform(
            WorldModel world,

            EventScheduler scheduler,
            ImageStore imageStore)
    {
        if (this.health <= 0) {
            Entity stump = world.createStump(this.id,
                    this.position,
                    imageStore.getImageList(STUMP_KEY));

            world.removeEntity(this);
            scheduler.unscheduleAllEvents(this);

            world.addEntity(stump);

            return true;
        }
        else if (this.health >= this.healthLimit)
        {
            EntityActions tree = (EntityActions) world.createTree("tree_" + this.id,
                    this.position,
                    getNumFromRange(TREE_ACTION_MAX, TREE_ACTION_MIN),
                    getNumFromRange(TREE_ANIMATION_MAX, TREE_ANIMATION_MIN),
                    getNumFromRange(TREE_HEALTH_MAX, TREE_HEALTH_MIN),
                    imageStore.getImageList(TREE_KEY));

            world.removeEntity(this);
            scheduler.unscheduleAllEvents(this);

            world.addEntity(tree);
            tree.scheduleActions(scheduler, world, imageStore);

            return true;
        }

        return false;
    }

//    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
//        scheduler.scheduleEvent(this, this.createActivityAction(world, imageStore), this.actionPeriod);
//        scheduler.scheduleEvent(this, this.createAnimationAction( 0), this.getAnimationPeriod());
//    }

    }

