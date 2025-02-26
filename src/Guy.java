import processing.core.PImage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Guy extends BearGuyPosition {
    private PathingStrategy strategy = new AStarPathingStrategy();
    public Guy(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, int actionPeriod,
               int animationPeriod, int health, int healthLimit) {
        super(id, position, images, resourceLimit, resourceCount, actionPeriod,
                animationPeriod, health, healthLimit);

    }


    public Point nextPosition(WorldModel world, Point destPos) {
                Point path = strategy.computePath(this.position, destPos,
                p -> !world.isOccupied(p),
                Guy::neighbors,
                PathingStrategy.CARDINAL_NEIGHBORS);
                return path;
    }
    private static boolean neighbors(Point p1, Point p2) {
        return p1.x + 1 == p2.x && p1.y == p2.y ||
                p1.x - 1 == p2.x && p1.y == p2.y ||
                p1.x == p2.x && p1.y + 1 == p2.y ||
                p1.x == p2.x && p1.y - 1 == p2.y;
    }

    public boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler)
    {
        if (world.adjacent(this.position, target.position)) {
            world.removeEntity(target);
            scheduler.unscheduleAllEvents(target);
            return true;
        } else {
            Point nextPos = this.nextPosition(world, target.position);

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

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {   Optional<Entity> fairyTarget =
            world.findNearest(this.position, new ArrayList<>(Arrays.asList(DeadFlower.class)));

        if (fairyTarget.isPresent()) {
            Point tgtPos = fairyTarget.get().position;

            if (this.moveTo(world, fairyTarget.get(), scheduler)) {
                EntityActions sapling = (EntityActions) world.createSapling("sapling_" + this.id, tgtPos,
                        imageStore.getImageList(world.SAPLING_KEY));

                world.addEntity(sapling);
                sapling.scheduleActions(scheduler, world, imageStore);
                VirtualWorld.treeList.add(sapling.position);
            }
        }

        scheduler.scheduleEvent(this,
                this.createActivityAction(world, imageStore),
                this.actionPeriod);
    }

//    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
//        scheduler.scheduleEvent(this, this.createActivityAction(world, imageStore), this.actionPeriod);
//        scheduler.scheduleEvent(this, this.createAnimationAction( 0), this.getAnimationPeriod());
//    }
}
