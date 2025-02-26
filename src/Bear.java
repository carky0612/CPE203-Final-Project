import processing.core.PImage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Bear extends Dude {
    public Bear(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount,
                int actionPeriod, int animationPeriod, int health, int healthLimit) {
        super(id, position, images, resourceLimit, resourceCount, actionPeriod, animationPeriod, health, healthLimit);
    }
    public boolean moveTo(

            WorldModel world,
            Entity target,
            EventScheduler scheduler)
    {
        if (world.adjacent(this.position, target.position)) {
            this.resourceCount += 1;
            target.health--;

            VirtualWorld.treeList.remove(target.position);

            return true;
        }
        else {
            Point nextPos = this.nextPosition( world, target.position);

            if (!this.position.equals(nextPos)) {
                Optional<Entity> occupant = world.getOccupant(nextPos);
                if (occupant.isPresent()) {
                    scheduler.unscheduleAllEvents(occupant.get());
                }

                world.moveEntity(this, nextPos);
            }
            return false;
        }
    }
    public boolean transformNotFull(

            WorldModel world,
            EventScheduler scheduler,
            ImageStore imageStore)
    {
//        if (this.resourceCount >= this.resourceLimit) {
//            EntityActions miner = (EntityActions) world.createDudeFull(this.id,this.position
//                    , this.actionPeriod,
//                    this.animationPeriod,
//                    this.resourceLimit,
//                    this.images);

//            world.removeEntity(this);
//            scheduler.unscheduleAllEvents(this);
//
//            world.addEntity(miner);
//            miner.scheduleActions(scheduler, world, imageStore);
//
//            return true;
//        }

        return false;
    }
    public void executeActivity(

            WorldModel world,
            ImageStore imageStore,
            EventScheduler scheduler)
    {
        Optional<Entity> target =
                world.findNearest(this.position, new ArrayList<>(Arrays.asList(Flower.class, GrowFlower.class)));

        if (!target.isPresent() || !this.moveTo(world,
                target.get(),
                scheduler)
                || !this.transformNotFull(world, scheduler, imageStore))
        {
            scheduler.scheduleEvent(this,
                    this.createActivityAction(world, imageStore),
                    this.actionPeriod);
        }
    }
//    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
//        scheduler.scheduleEvent(this, this.createActivityAction(world, imageStore), this.actionPeriod);
//        scheduler.scheduleEvent(this, this.createAnimationAction( 0), this.getAnimationPeriod());
//    }
}
