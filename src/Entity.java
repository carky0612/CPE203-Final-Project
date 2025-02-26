import java.util.*;

import processing.core.PImage;

/**
 * An entity that exists in the world. See EntityKind for the
 * different kinds of entities that exist.
 */
public abstract class Entity
{

//    public EntityKind kind;
    public String id;
    public Point position;
    public List<PImage> images;
    private int imageIndex;
    public int resourceLimit;
    public int resourceCount;
    public int actionPeriod;
    public int animationPeriod;
    public int health;
    public int healthLimit;

//    public static final int SAPLING_ACTION_ANIMATION_PERIOD = 1000;
//
//    public static final int SAPLING_HEALTH_LIMIT = 5;



    public Entity(

            String id,
            Point position,
            List<PImage> images,
            int resourceLimit,
            int resourceCount,
            int actionPeriod,
            int animationPeriod,
            int health,
            int healthLimit)
    {


        this.id = id;
        this.position = position;
        this.images = images;
        this.imageIndex = 0;
        this.resourceLimit = resourceLimit;
        this.resourceCount = resourceCount;
        this.actionPeriod = actionPeriod;
        this.animationPeriod = animationPeriod;
        this.health = health;
        this.healthLimit = healthLimit;
    }

    public int getAnimationPeriod()
    {
        return animationPeriod;
    }


    public PImage getCurrentImage() {

//         if (entity instanceof Entity) {
        return this.images.get(this.imageIndex);
//        }
//        else {
//            throw new UnsupportedOperationException(
//                    String.format("getCurrentImage not supported for %s",
//                            entity));
    }
    public void nextImage() {
        this.imageIndex = (this.imageIndex + 1) % this.images.size();
    }
    public Action createAnimationAction( int repeatCount) {
        return new Animation( this, null, null,
                repeatCount);
    }
//    public Action createActivityAction(
//            WorldModel world, ImageStore imageStore)
//    {
//        return new Activity((EntityActions) this,  world, imageStore, 0);
//    }
//   public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
////        scheduler.scheduleEvent(this, this.createActivityAction( world, imageStore), this.actionPeriod);
//        scheduler.scheduleEvent(this, this.createAnimationAction( 0), this.getAnimationPeriod());
//   }

//    public Action createActivityAction(
//            WorldModel world, ImageStore imageStore)
//    {
//        return new Activity( this,  world, imageStore, 0);
//    }
//
//    public Action createAnimationAction( int repeatCount) {
//        return new Animation( this, null, null,
//                repeatCount);
//    }
//    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
//        scheduler.scheduleEvent(this, new Activity(this, world, imageStore), this.actionPeriod);
//        scheduler.scheduleEvent(this, this.createAnimationAction( 0), this.getAnimationPeriod());
//    }
//    public abstract void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler);
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


    //public abstract void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore);


    }

//    public abstract Point nextPosition(WorldModel world, Point destPos);//only used by dude and fairy

//    public Point nextPositionFairy(
//            WorldModel world, Point destPos)
//    {
//        int horiz = Integer.signum(destPos.x - this.position.x);
//        Point newPos = new Point(this.position.x + horiz, this.position.y);
//
//        if (horiz == 0 || world.isOccupied( newPos)) {
//            int vert = Integer.signum(destPos.y - this.position.y);
//            newPos = new Point(this.position.x, this.position.y + vert);
//
//            if (vert == 0 || world.isOccupied(newPos)) {
//                newPos = this.position;
//            }
//        }
//
//        return newPos;
//    }

//    public Point nextPositionDude(
//            WorldModel world, Point destPos)
//    {
//        int horiz = Integer.signum(destPos.x - this.position.x);
//        Point newPos = new Point(this.position.x + horiz, this.position.y);
//
//        if (horiz == 0 || world.isOccupied(newPos) && world.getOccupancyCell( newPos).kind != EntityKind.STUMP) {
//            int vert = Integer.signum(destPos.y - this.position.y);
//            newPos = new Point(this.position.x, this.position.y + vert);
//
//            if (vert == 0 || world.isOccupied(newPos) &&  world.getOccupancyCell(newPos).kind != EntityKind.STUMP) {
//                newPos = this.position;
//            }
//        }
//
//        return newPos;
//    }


//    public boolean moveToFull(
//
//            WorldModel world,
//            Entity target,
//            EventScheduler scheduler)
//    {
//        if (world.adjacent(this.position, target.position)) {
//            return true;
//        }
//        else {
//            Point nextPos = this.nextPositionDude(world, target.position);
//
//            if (!this.position.equals(nextPos)) {
//                Optional<Entity> occupant = world.getOccupant(nextPos);
//                if (occupant.isPresent()) {
//                    scheduler.unscheduleAllEvents(occupant.get());
//                }
//
//                world.moveEntity(this, nextPos);
//            }
//            return false;
//        }
//    }
//    public boolean moveToNotFull(
//
//            WorldModel world,
//            Entity target,
//            EventScheduler scheduler)
//    {
//        if (world.adjacent(this.position, target.position)) {
//            this.resourceCount += 1;
//            target.health--;
//            return true;
//        }
//        else {
//            Point nextPos = this.nextPositionDude( world, target.position);
//
//            if (!this.position.equals(nextPos)) {
//                Optional<Entity> occupant = world.getOccupant(nextPos);
//                if (occupant.isPresent()) {
//                    scheduler.unscheduleAllEvents(occupant.get());
//                }
//
//                world.moveEntity(this, nextPos);
//            }
//            return false;
//        }
//    }


//    public boolean moveToFairy(
//
//            WorldModel world,
//            Entity target,
//            EventScheduler scheduler)
//    {
//        if (world.adjacent(this.position, target.position)) {
//            world.removeEntity(target);
//            scheduler.unscheduleAllEvents(target);
//            return true;
//        }
//        else {
//            Point nextPos = this.nextPositionFairy( world, target.position);
//
//            if (!this.position.equals(nextPos)) {
//                Optional<Entity> occupant = world.getOccupant( nextPos);
//                if (occupant.isPresent()) {
//                    scheduler.unscheduleAllEvents(occupant.get());
//                }
//
//                world.moveEntity(this, nextPos);
//            }
//            return false;
//        }
//
//        }
//    public boolean transformNotFull(
//
//            WorldModel world,
//            EventScheduler scheduler,
//            ImageStore imageStore)
//    {
//        if (this.resourceCount >= this.resourceLimit) {
//            Entity miner = world.createDudeFull(this.id,this.position
//                    , this.actionPeriod,
//                    this.animationPeriod,
//                    this.resourceLimit,
//                    this.images);
//
//            world.removeEntity(this);
//            scheduler.unscheduleAllEvents(this);
//
//            world.addEntity(miner);
//            miner.scheduleActions(scheduler, world, imageStore);
//
//            return true;
//        }
//
//        return false;
//    }

//    public void transformFull(
//
//            WorldModel world,
//            EventScheduler scheduler,
//            ImageStore imageStore)
//    {
//        Entity miner = world.createDudeNotFull(this.id,this.position,
//                this.actionPeriod,
//                this.animationPeriod,
//                this.resourceLimit,
//                this.images);
//
//        world.removeEntity(this);
//        scheduler.unscheduleAllEvents(this);
//
//        world.addEntity(miner);
//        miner.scheduleActions(scheduler, world, imageStore);
//    }

//    public void executeSaplingActivity(
//
//                                       WorldModel world,
//                                       ImageStore imageStore,
//                                       EventScheduler scheduler)
//    {
//        this.health++;
//        if (!world.transformPlant(this, scheduler, imageStore))
//        {
//            scheduler.scheduleEvent(this,
//                    this.createActivityAction(world, imageStore),
//                    this.actionPeriod);
//        }
//    }

//    public void executeTreeActivity(
//
//            WorldModel world,
//            ImageStore imageStore,
//            EventScheduler scheduler)
//    {
//
//        if (!world.transformPlant(this, scheduler, imageStore)) {
//
//            scheduler.scheduleEvent(this,
//                    createActivityAction(world, imageStore),
//                    actionPeriod);
//        }
//    }

//    public void executeFairyActivity(
//
//            WorldModel world,
//            ImageStore imageStore,
//            EventScheduler scheduler)
//    {
//        Optional<Entity> fairyTarget =
//                world.findNearest( this.position, new ArrayList<>(Arrays.asList(EntityKind.STUMP)));
//
//        if (fairyTarget.isPresent()) {
//            Point tgtPos = fairyTarget.get().position;
//
//            if (this.moveToFairy(world, fairyTarget.get(), scheduler)) {
//                Entity sapling = world.createSapling("sapling_" + this.id, tgtPos,
//                        imageStore.getImageList(world.SAPLING_KEY));
//
//                world.addEntity( sapling);
//                sapling.scheduleActions(scheduler, world, imageStore);
//            }
//        }
//
//        scheduler.scheduleEvent(this,
//                this.createActivityAction(world, imageStore),
//                this.actionPeriod);
//    }

//    public void executeDudeNotFullActivity(
//
//            WorldModel world,
//            ImageStore imageStore,
//            EventScheduler scheduler)
//    {
//        Optional<Entity> target =
//                world.findNearest(this.position, new ArrayList<>(Arrays.asList(EntityKind.TREE, EntityKind.SAPLING)));
//
//        if (!target.isPresent() || !this.moveToNotFull(world,
//                target.get(),
//                scheduler)
//                || !this.transformNotFull(world, scheduler, imageStore))
//        {
//            scheduler.scheduleEvent(this,
//                    this.createActivityAction(world, imageStore),
//                    this.actionPeriod);
//        }
//    }

//    public void executeDudeFullActivity(
//
//            WorldModel world,
//            ImageStore imageStore,
//            EventScheduler scheduler)
//    {
//        Optional<Entity> fullTarget =
//                world.findNearest(this.position, new ArrayList<>(Arrays.asList(EntityKind.HOUSE)));
//
//        if (fullTarget.isPresent() && this.moveToFull( world,
//                fullTarget.get(), scheduler))
//        {
//            this.transformFull(world, scheduler, imageStore);
//        }
//        else {
//            scheduler.scheduleEvent(this,
//                    this.createActivityAction(world, imageStore),
//                    this.actionPeriod);
//        }
//    }



















