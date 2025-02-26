import processing.core.PImage;

import java.util.List;

public abstract class Dude extends BearGuyPosition {

    public Dude(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount,
                         int actionPeriod, int animationPeriod, int health, int healthLimit) {
        super(id, position, images, resourceLimit, resourceCount, actionPeriod, animationPeriod, health, healthLimit);
    }
    public Point nextPosition(WorldModel world, Point destPos)
    {
//        Point path = strategy.computePath(this.position, destPos,
//                p -> !world.isOccupied(p),
//                Dude::neighbors,
//                PathingStrategy.CARDINAL_NEIGHBORS);
        int horiz = Integer.signum(destPos.x - this.position.x);
        Point newPos = new Point(this.position.x + horiz, this.position.y);

        if (horiz == 0 || world.isOccupied(newPos) && (world.getOccupancyCell( newPos).getClass()!= DeadFlower.class)) {
            int vert = Integer.signum(destPos.y - this.position.y);
            newPos = new Point(this.position.x, this.position.y + vert);

            if (vert == 0 || world.isOccupied(newPos) &&  world.getOccupancyCell(newPos).getClass()!= DeadFlower.class) {
                newPos = this.position;
            }
        }

        return newPos;


    }

//    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
//        scheduler.scheduleEvent(this, this.createActivityAction(world, imageStore), this.actionPeriod);
//        scheduler.scheduleEvent(this, this.createAnimationAction( 0), this.getAnimationPeriod());
//    }


}
