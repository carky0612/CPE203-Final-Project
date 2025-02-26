import processing.core.PImage;

import java.util.List;

public abstract class EntityActions extends Entity{
    public EntityActions(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, int actionPeriod, int animationPeriod, int health, int healthLimit) {
        super(id, position, images, resourceLimit, resourceCount, actionPeriod, animationPeriod, health, healthLimit);
    }
    public abstract void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler);
        public Action createActivityAction(
            WorldModel world, ImageStore imageStore)
    {
        return new Activity( this,  world, imageStore, 0);
    }

    public Action createAnimationAction( int repeatCount) {
        return new Animation( this, null, null,
                repeatCount);
    }
    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(this, this.createActivityAction( world, imageStore), this.actionPeriod);
        scheduler.scheduleEvent(this, this.createAnimationAction( 0), this.getAnimationPeriod());
    }

}
