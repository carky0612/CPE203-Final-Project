import processing.core.PImage;

import java.util.List;

public abstract class TranPlant extends EntityActions {
    public TranPlant(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount,
                             int actionPeriod, int animationPeriod, int health, int healthLimit) {
        super(id, position, images, resourceLimit, resourceCount, actionPeriod, animationPeriod, health, healthLimit);
    }
    public abstract boolean transform( WorldModel world, EventScheduler scheduler, ImageStore imageStore);
//    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
//    {
//        this.health++;
//        if (!this.transform(world, scheduler, imageStore))
//        {
//            scheduler.scheduleEvent(this,
//                    this.createActivityAction(world, imageStore),
//                    this.actionPeriod);
//        }
//    }
    public int getHealth()
    {
        return this.health;
    }
}

