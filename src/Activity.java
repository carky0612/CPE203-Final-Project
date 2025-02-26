public class Activity extends Action{
    private EntityActions entity;
    public Activity( EntityActions entity, WorldModel world, ImageStore imageStore, int repeatCount) {
        super( world, imageStore, repeatCount);
        this.entity = entity;
    }
    public void executeAction( EventScheduler scheduler) {

        this.entity.executeActivity(this.world, this.imageStore, scheduler);

    }
//    public void executeActivityAction(EventScheduler scheduler)
//    {
//        this.entity.executeActivity(this.world, this.imageStore, scheduler);
//    }
}
