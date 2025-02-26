import processing.core.PImage;

import java.util.*;

/**
 * Represents the 2D World in which this simulation is running.
 * Keeps track of the size of the world, the background image for each
 * location in the world, and the entities that populate the world.
 */
public final class WorldModel
{
    public static final String SAPLING_KEY = "sapling";
    public static final int SAPLING_HEALTH_LIMIT = 5;
    public static final int SAPLING_ACTION_ANIMATION_PERIOD = 1000; // have to be in sync since grows and gains health at same time
    public static final int SAPLING_NUM_PROPERTIES = 4;
    public static final int SAPLING_ID = 1;
    public static final int SAPLING_COL = 2;
    public static final int SAPLING_ROW = 3;
    public static final int SAPLING_HEALTH = 4;

    public static final String BGND_KEY = "background";

    public static final String OBSTACLE_KEY = "obstacle";
    public static final int OBSTACLE_NUM_PROPERTIES = 5;
    public static final int OBSTACLE_ID = 1;
    public static final int OBSTACLE_COL = 2;
    public static final int OBSTACLE_ROW = 3;
    public static final int OBSTACLE_ANIMATION_PERIOD = 4;

    public static final String DUDE_KEY = "dude";
    public static final int DUDE_NUM_PROPERTIES = 7;
    public static final int DUDE_ID = 1;
    public static final int DUDE_COL = 2;
    public static final int DUDE_ROW = 3;
    public static final int DUDE_LIMIT = 4;
    public static final int DUDE_ACTION_PERIOD = 5;
    public static final int DUDE_ANIMATION_PERIOD = 6;

    public static final String HOUSE_KEY = "house";
    public static final int HOUSE_NUM_PROPERTIES = 4;
    public static final int HOUSE_ID = 1;
    public static final int HOUSE_COL = 2;
    public static final int HOUSE_ROW = 3;

    public static final String GUY_KEY = "guy";
    public static final int GUY_NUM_PROPERTIES = 6;
    public static final int GUY_ID = 1;
    public static final int GUY_COL = 2;
    public static final int GUY_ROW = 3;
    public static final int GUY_ANIMATION_PERIOD = 2;
    public static final int GUY_ACTION_PERIOD = 5;

//    public static final String STUMP_KEY = "stump";

    public static final String TREE_KEY = "tree";
    public static final int TREE_NUM_PROPERTIES = 7;
    public static final int TREE_ID = 1;
    public static final int TREE_COL = 2;
    public static final int TREE_ROW = 3;
    public static final int TREE_ANIMATION_PERIOD = 4;
    public static final int TREE_ACTION_PERIOD = 5;
    public static final int TREE_HEALTH = 6;

//    public static final int TREE_ANIMATION_MAX = 600;
//    public static final int TREE_ANIMATION_MIN = 50;
//    public static final int TREE_ACTION_MAX = 1400;
//    public static final int TREE_ACTION_MIN = 1000;
//    public static final int TREE_HEALTH_MAX = 3;
//    public static final int TREE_HEALTH_MIN = 1;

    public int numRows;
    public int numCols;
    private Background background[][];
    private Entity occupancy[][];
    public Set<Entity> entities;
    private static final int BGND_NUM_PROPERTIES = 4;
    private static final int BGND_ID = 1;
    private static final int BGND_COL = 2;
    private static final int BGND_ROW = 3;

    private static final int PROPERTY_KEY = 0;

    private static final List<String> PATH_KEYS = new ArrayList<>(Arrays.asList("bridge", "dirt", "dirt_horiz", "dirt_vert_left", "dirt_vert_right",
            "dirt_bot_left_corner", "dirt_bot_right_up", "dirt_vert_left_bot"));


//    public static final String SAPLING_KEY = "sapling";
//
//    public static final String OBSTACLE_KEY = "obstacle";
//    public static final String DUDE_KEY = "dude";
//    public static final String HOUSE_KEY = "house";
//
//    public static final String FAIRY_KEY = "fairy";
//
//    public static final String TREE_KEY = "tree";

//    public int getNumFromRange(int max, int min)
//    {
//        Random rand = new Random();
//        return min + rand.nextInt(
//                max
//                        - min);
//    }

    public boolean adjacent(Point p1, Point p2) {
        return (p1.x == p2.x && Math.abs(p1.y - p2.y) == 1) || (p1.y == p2.y
                && Math.abs(p1.x - p2.x) == 1);
    }

    public WorldModel(int numRows, int numCols, Background defaultBackground) {
        this.numRows = numRows;
        this.numCols = numCols;
        this.background = new Background[numRows][numCols];
        this.occupancy = new Entity[numRows][numCols];
        this.entities = new HashSet<>();

        for (int row = 0; row < numRows; row++) {
            Arrays.fill(this.background[row], defaultBackground);
        }
    }

    public boolean withinBounds( Point pos) {
        return pos.y >= 0 && pos.y < this.numRows && pos.x >= 0
                && pos.x < this.numCols;
    }

    public void setBackgroundCell(
            Point pos, Background background)
    {
        this.background[pos.y][pos.x] = background;
    }

    public void setBackground(
            Point pos, Background background)
    {
        if (this.withinBounds(pos)) {
            this.setBackgroundCell(pos, background);
        }
    }
    public Entity getOccupancyCell( Point pos) {
        return this.occupancy[pos.y][pos.x];
    }

    public Background getBackgroundCell( Point pos) {
        return this.background[pos.y][pos.x];
    }

    public void setOccupancyCell(
            Point pos, Entity entity)
    {
        this.occupancy[pos.y][pos.x] = entity;
    }

    public boolean isOccupied( Point pos) {
        return this.withinBounds( pos) && this.getOccupancyCell( pos) != null;
    }

    public  Optional<Entity> getOccupant( Point pos) {
        if (this.isOccupied(pos)) {
            return Optional.of(this.getOccupancyCell(pos));
        }
        else {
            return Optional.empty();
        }
    }

    public Optional<PImage> getBackgroundImage(
            Point pos)
    {
        if (this.withinBounds( pos)) {
            return Optional.of(getBackgroundCell(pos).getCurrentImage());
        }
        else {
            return Optional.empty();
        }
    }

    public void addEntity( Entity entity) {
        if (this.withinBounds( entity.position)) {
            this.setOccupancyCell(entity.position, entity);
            this.entities.add(entity);
        }
    }

    public void tryAddEntity( Entity entity) {
        if (this.isOccupied(entity.position)) {
            // arguably the wrong type of exception, but we are not
            // defining our own exceptions yet
            throw new IllegalArgumentException("position occupied");
        }

        this.addEntity(entity);
    }
    public void removeEntityAt(Point pos) {
        if (this.withinBounds( pos) && this.getOccupancyCell( pos) != null) {
            Entity entity = this.getOccupancyCell(pos);

            /* This moves the entity just outside of the grid for
             * debugging purposes. */
            entity.position = new Point(-1, -1);
            this.entities.remove(entity);
            this.setOccupancyCell(pos, null);
        }
    }
    public  void removeEntity( Entity entity) {
        this.removeEntityAt( entity.position);
    }

    public void moveEntity( Entity entity, Point pos) {
        Point oldPos = entity.position;
        if (this.withinBounds( pos) && !pos.equals(oldPos)) {
            this.setOccupancyCell(oldPos, null);
            this.removeEntityAt(pos);
            this.setOccupancyCell(pos, entity);
            entity.position = pos;
        }
    }

    public boolean parseBackground(
            String[] properties, ImageStore imageStore)
    {
        if (properties.length == BGND_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(properties[BGND_COL]),
                    Integer.parseInt(properties[BGND_ROW]));
            String id = properties[BGND_ID];
            this.setBackground(pt,
                    new Background(id, imageStore.getImageList(id)));
        }

        return properties.length == BGND_NUM_PROPERTIES;
    }

//    public Entity createDudeFull(
//            String id,
//            Point position,
//            int actionPeriod,
//            int animationPeriod,
//            int resourceLimit,
//            List<PImage> images) {
//        return new DUDE_FULL(id, position, images, resourceLimit, 0,
//                actionPeriod, animationPeriod, 0, 0);
//    }

    public Entity createDudeNotFull(
            String id,
            Point position,
            int actionPeriod,
            int animationPeriod,
            int resourceLimit,
            List<PImage> images)
    {
        return new Bear(id, position, images, resourceLimit, 0,
                actionPeriod, animationPeriod, 0, 0);
    }

    public Entity createObstacle(
            String id, Point position, int animationPeriod, List<PImage> images)
    {
        return new Obstacle( id, position, images, 0, 0, 0,
                animationPeriod, 0, 0);
    }

    public Entity createTree(
            String id,
            Point position,
            int actionPeriod,
            int animationPeriod,
            int health,
            List<PImage> images)
    {
        return new Flower( id, position, images, 0, 0,
                actionPeriod, animationPeriod, health, 0);
    }

    public Entity createSapling(
            String id,
            Point position,
            List<PImage> images)
    {
        return new GrowFlower( id, position, images, 0, 0,
                SAPLING_ACTION_ANIMATION_PERIOD, SAPLING_ACTION_ANIMATION_PERIOD, 0, SAPLING_HEALTH_LIMIT);
    }

    public Entity createStump(
            String id,
            Point position,
            List<PImage> images)
    {
        return new DeadFlower( id, position, images, 0, 0,
                0, 0, 0, 0);
    }


    public static Guy createGuy(
            String id,
            Point position,
            int actionPeriod,
            int animationPeriod,
            List<PImage> images)
    {
        return new Guy( id, position, images, 0, 0,
                actionPeriod, animationPeriod, 0, 0);
    }

//    public Entity createHouse(
//            String id, Point position, List<PImage> images)
//    {
//        return new House( id, position, images, 0, 0, 0,
//                0, 0, 0);
//    }

    public boolean parseSapling(
            String[] properties,  ImageStore imageStore)
    {
        if (properties.length == SAPLING_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(properties[SAPLING_COL]),
                    Integer.parseInt(properties[SAPLING_ROW]));
            String id = properties[SAPLING_ID];
            int health = Integer.parseInt(properties[SAPLING_HEALTH]);
            Entity entity = new GrowFlower( id, pt, imageStore.getImageList(SAPLING_KEY), 0, 0,
                    SAPLING_ACTION_ANIMATION_PERIOD, SAPLING_ACTION_ANIMATION_PERIOD, health, SAPLING_HEALTH_LIMIT);
            this.tryAddEntity(entity);
        }

        return properties.length == SAPLING_NUM_PROPERTIES;
    }

    public boolean parseDude(
            String[] properties, ImageStore imageStore)
    {
        if (properties.length == DUDE_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(properties[DUDE_COL]),
                    Integer.parseInt(properties[DUDE_ROW]));
            Entity entity = this.createDudeNotFull(properties[DUDE_ID],
                    pt,
                    Integer.parseInt(properties[DUDE_ACTION_PERIOD]),
                    Integer.parseInt(properties[DUDE_ANIMATION_PERIOD]),
                    Integer.parseInt(properties[DUDE_LIMIT]),
                    imageStore.getImageList( DUDE_KEY));
            this.tryAddEntity( entity);
        }

        return properties.length == DUDE_NUM_PROPERTIES;
    }

    public boolean parseGuy(
            String[] properties,  ImageStore imageStore)
    {
        if (properties.length == GUY_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(properties[GUY_COL]),
                    Integer.parseInt(properties[GUY_ROW]));
            Entity entity = createGuy(properties[GUY_ID],
                    pt,
                    Integer.parseInt(properties[GUY_ACTION_PERIOD]),
                    Integer.parseInt(properties[GUY_ANIMATION_PERIOD]),
                    imageStore.getImageList(GUY_KEY));
            this.tryAddEntity(entity);
        }

        return properties.length == GUY_NUM_PROPERTIES;
    }

    public boolean parseTree(
            String[] properties, ImageStore imageStore)
    {
        if (properties.length == TREE_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(properties[TREE_COL]),
                    Integer.parseInt(properties[TREE_ROW]));
            Entity entity = createTree(properties[TREE_ID],
                    pt,
                    Integer.parseInt(properties[TREE_ACTION_PERIOD]),
                    Integer.parseInt(properties[TREE_ANIMATION_PERIOD]),
                    Integer.parseInt(properties[TREE_HEALTH]),
                    imageStore.getImageList(TREE_KEY));
            this.tryAddEntity(entity);
        }

        return properties.length == TREE_NUM_PROPERTIES;
    }

    public boolean parseObstacle(
            String[] properties, ImageStore imageStore)
    {
        if (properties.length == OBSTACLE_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(properties[OBSTACLE_COL]),
                    Integer.parseInt(properties[OBSTACLE_ROW]));
            Entity entity = createObstacle(properties[OBSTACLE_ID], pt,
                    Integer.parseInt(properties[OBSTACLE_ANIMATION_PERIOD]),
                    imageStore.getImageList(
                            OBSTACLE_KEY));
            this.tryAddEntity(entity);
        }

        return properties.length == OBSTACLE_NUM_PROPERTIES;
    }


//    public boolean parseHouse(
//            String[] properties,  ImageStore imageStore)
//    {
//        if (properties.length == HOUSE_NUM_PROPERTIES) {
//            Point pt = new Point(Integer.parseInt(properties[HOUSE_COL]),
//                    Integer.parseInt(properties[HOUSE_ROW]));
//            Entity entity = createHouse(properties[HOUSE_ID],pt,
//                    imageStore.getImageList(
//                            HOUSE_KEY));
//            this.tryAddEntity( entity);
//        }
//
//        return properties.length == HOUSE_NUM_PROPERTIES;
//    }

    public  Optional<Entity> findNearest(
            Point pos, List<Class> kinds)
    {
        List<Entity> ofType = new LinkedList<>();
        for (Class type: kinds)
        {
            for (Entity entity : this.entities)

                if (type.isInstance(entity)) {
                    ofType.add(entity);
                }

        }

        return nearestEntity(ofType, pos);
    }

    public int distanceSquared(Point p1, Point p2) {
        int deltaX = p1.x - p2.x;
        int deltaY = p1.y - p2.y;

        return deltaX * deltaX + deltaY * deltaY;
    }
    public Optional<Entity> nearestEntity(
            List<Entity> entities, Point pos)
    {
        if (entities.isEmpty()) {
            return Optional.empty();
        }
        else {
            Entity nearest = entities.get(0);
            int nearestDistance = distanceSquared(nearest.position, pos);

            for (Entity other : entities) {
                int otherDistance = distanceSquared(other.position, pos);

                if (otherDistance < nearestDistance) {
                    nearest = other;
                    nearestDistance = otherDistance;
                }
            }

            return Optional.of(nearest);
        }
    }


//    public boolean transformTree(
//            Entity entity,
//            EventScheduler scheduler,
//            ImageStore imageStore)
//    {
//        if (entity.health <= 0) {
//            Entity stump = this.createStump(entity.id,
//                    entity.position,
//                    imageStore.getImageList( STUMP_KEY));
//
//            this.removeEntity(entity);
//            scheduler.unscheduleAllEvents(entity);
//
//           this.addEntity(stump);
//
//            return true;
//        }
//
//        return false;
//    }

//    public boolean transformPlant( Entity entity,
//
//                                          EventScheduler scheduler,
//                                          ImageStore imageStore)
//    {
//        if (entity.kind == EntityKind.TREE)
//        {
//            return this.transformTree(entity, scheduler, imageStore);
//        }
//        else if (entity.kind == EntityKind.SAPLING)
//        {
//            return this.transformSapling(entity,  scheduler, imageStore);
//        }
//        else
//        {
//            throw new UnsupportedOperationException(
//                    String.format("transformPlant not supported for %s", entity));
//        }
//    }

//    public boolean transformSapling(
//            Entity entity,
//
//            EventScheduler scheduler,
//            ImageStore imageStore)
//    {
//        if (entity.health <= 0) {
//            Entity stump = this.createStump(entity.id,
//                    entity.position,
//                    imageStore.getImageList(STUMP_KEY));
//
//            this.removeEntity(entity);
//            scheduler.unscheduleAllEvents(entity);
//
//            this.addEntity(stump);
//
//            return true;
//        }
//        else if (entity.health >= entity.healthLimit)
//        {
//            Entity tree = this.createTree("tree_" + entity.id,
//                    entity.position,
//                    getNumFromRange(TREE_ACTION_MAX, TREE_ACTION_MIN),
//                    getNumFromRange(TREE_ANIMATION_MAX, TREE_ANIMATION_MIN),
//                    getNumFromRange(TREE_HEALTH_MAX, TREE_HEALTH_MIN),
//                    imageStore.getImageList(TREE_KEY));
//
//            this.removeEntity(entity);
//            scheduler.unscheduleAllEvents(entity);
//
//            this.addEntity(tree);
//            entity.scheduleActions(scheduler, this, imageStore);
//
//            return true;
//        }
//
//        return false;
//    }

    public void load(
            Scanner in, ImageStore imageStore)
    {
        int lineNumber = 0;
        while (in.hasNextLine()) {
            try {
                if (!this.processLine(in.nextLine(), imageStore)) {
                    System.err.println(String.format("invalid entry on line %d",
                            lineNumber));
                }
            }
            catch (NumberFormatException e) {
                System.err.println(
                        String.format("invalid entry on line %d", lineNumber));
            }
            catch (IllegalArgumentException e) {
                System.err.println(
                        String.format("issue on line %d: %s", lineNumber,
                                e.getMessage()));
            }
            lineNumber++;
        }
    }

    public boolean processLine(
            String line, ImageStore imageStore)
    {
        String[] properties = line.split("\\s");
        if (properties.length > 0) {
            switch (properties[PROPERTY_KEY]) {
                case BGND_KEY:
                    return this.parseBackground(properties, imageStore);
                case DUDE_KEY:
                    return this.parseDude(properties, imageStore);
                case OBSTACLE_KEY:
                    return this.parseObstacle(properties, imageStore);
                case GUY_KEY:
                    return this.parseGuy(properties, imageStore);
//                case HOUSE_KEY:
//                    return this.parseHouse(properties, imageStore);
                case TREE_KEY:
                    return this.parseTree(properties, imageStore);
                case SAPLING_KEY:
                    return this.parseSapling(properties, imageStore);
            }
        }

        return false;
    }



}
