import processing.core.PImage;

import java.util.List;

public class DeadFlower extends Entity{
    public DeadFlower(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount,
                      int actionPeriod, int animationPeriod, int health, int healthLimit)
    {
        super( id, position, images, resourceLimit, resourceCount, actionPeriod, animationPeriod, health, healthLimit);
    }
//    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
//        scheduler.scheduleEvent(this, this.createActivityAction(world, imageStore), this.actionPeriod);
//        scheduler.scheduleEvent(this, this.createAnimationAction( 0), this.getAnimationPeriod());
//    }

}
