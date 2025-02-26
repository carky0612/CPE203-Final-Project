/**
 * An action that can be taken by an entity
 */
public abstract class Action
{

    //public Entity entity;
    public WorldModel world;
    public ImageStore imageStore;
    public int repeatCount;

    public Action(


            WorldModel world,
            ImageStore imageStore,
            int repeatCount)
    {

        //this.entity = entity;
        this.world = world;
        this.imageStore = imageStore;
        this.repeatCount = repeatCount;
    }

    public abstract void executeAction( EventScheduler scheduler);


//    public boolean adjacent(Point p1, Point p2) {
//        return (p1.x == p2.x && Math.abs(p1.y - p2.y) == 1) || (p1.y == p2.y
//                && Math.abs(p1.x - p2.x) == 1);
//    }

//    public void executeAnimationAction(
//             EventScheduler scheduler)
//    {
//        this.entity.nextImage();
//
//        if (this.repeatCount != 1) {
//            scheduler.scheduleEvent(this.entity,
//                    this.entity.createAnimationAction(
//                            Math.max(this.repeatCount - 1,
//                                    0)),
//                    this.entity.getAnimationPeriod());
//        }
//    }

//    public void executeActivityAction(EventScheduler scheduler)
//    {
//                this.entity.executeActivity(this.world, this.imageStore, scheduler);
//    }


    //{
//
//        this.executeActivityAction( scheduler);
//        this.executeAnimationAction(scheduler);
//
//        }
    }










