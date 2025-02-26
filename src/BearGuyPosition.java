import processing.core.PImage;

import java.util.List;

public abstract class BearGuyPosition extends EntityActions{
    public BearGuyPosition(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount,
                           int actionPeriod, int animationPeriod, int health, int healthLimit) {
        super(id, position, images, resourceLimit, resourceCount, actionPeriod, animationPeriod, health, healthLimit);
    }

    public abstract Point nextPosition(WorldModel world, Point destPos);
    public abstract boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler);


}
