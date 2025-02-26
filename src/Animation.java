public class Animation extends Action{
    private Entity entity;
    public Animation( Entity entity, WorldModel world, ImageStore imageStore, int repeatCount) {
        super( world, imageStore, repeatCount);
        this.entity = entity;
    }
    public void executeAction( EventScheduler scheduler) {



        this.entity.nextImage();

        if (this.repeatCount != 1) {
            scheduler.scheduleEvent(this.entity,
                    this.entity.createAnimationAction(
                            Math.max(this.repeatCount - 1,
                                    0)),
                    this.entity.getAnimationPeriod());
        }
    }
}
