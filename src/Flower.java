import processing.core.PImage;

import java.util.List;

public  class Flower extends TranPlant{
    public static final String STUMP_KEY = "stump";
    public Flower(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount,
                  int actionPeriod, int animationPeriod, int health, int healthLimit)
    {
        super( id, position, images, resourceLimit, resourceCount, actionPeriod, animationPeriod, health, healthLimit);

    }
    public void executeActivity(

            WorldModel world,
            ImageStore imageStore,
            EventScheduler scheduler)
    {

        if (!this.transform(world, scheduler, imageStore)) {

            scheduler.scheduleEvent(this,
                    createActivityAction(world, imageStore),
                    actionPeriod);
        }
    }
    public Point getPosition()
    {
        return position;
    }
//    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
//        scheduler.scheduleEvent(this, this.createActivityAction(world, imageStore), this.actionPeriod);
//        scheduler.scheduleEvent(this, this.createAnimationAction( 0), this.getAnimationPeriod());
//    }
    public boolean transform(
            WorldModel world,
            EventScheduler scheduler,
            ImageStore imageStore)
    {
        if (this.health <= 0) {
            Entity stump = world.createStump(this.id,
                    this.position,
                    imageStore.getImageList( STUMP_KEY));

            world.removeEntity(this);
            scheduler.unscheduleAllEvents(this);

            world.addEntity(stump);

            return true;
        }

        return false;
    }
//    public int getAnimationPeriod()
//    {
//        return animationPeriod;
//    }
//    public boolean transformPlant( WorldModel world,
//
//                                   EventScheduler scheduler,
//                                   ImageStore imageStore)
//    {
//        if (Entity.class == EntityKind.TREE)
//        {
//            return world.transformTree(this, scheduler, imageStore);
//        }
//        else if (this.kind == EntityKind.SAPLING)
//        {
//            return world.transformSapling(this,  scheduler, imageStore);
//        }
//        else
//        {
//            throw new UnsupportedOperationException(
//                    String.format("transformPlant not supported for %s", this));
//        }
//    }

}
